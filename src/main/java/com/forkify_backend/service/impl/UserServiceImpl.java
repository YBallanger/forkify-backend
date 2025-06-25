package com.forkify_backend.service.impl;

import com.forkify_backend.api.dto.*;
import com.forkify_backend.persistence.entity.Role;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.entity.UserVisit;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.service.AuthenticationService;
import com.forkify_backend.service.RoleService;
import com.forkify_backend.service.UserService;
import com.forkify_backend.service.mapper.UserMapper;
import com.forkify_backend.service.mapper.UserVisitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final UserVisitMapper userVisitMapper;

    @Override
    public User createUser(UserSignupDto userSignupDto) {
        Set<Role> roles = roleService.getDefaultRole();
        String firebaseUid = authenticationService.FirebaseSignUp(userSignupDto.getEmail(), userSignupDto.getPassword());
        User user = new User(userSignupDto, firebaseUid, roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserDtos(users);
    }

    @Override
    public Set<UserVisitDto> getUserVisits(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        return userVisitMapper.userVisitsToUserVisitDtos(user.getUserVisits());
    }

    @Override
    public UserStatisticsDto getUserStatistics(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        Double amountSpent = 0.0;
        Integer numberOfVisits = 0;
        Integer numberOfNewRestaurants = 0;

        Set<Long> visitedRestaurants = new HashSet<>();

        for (UserVisit userVisit : user.getUserVisits()) {
            amountSpent += userVisit.getAmountSpent();
            numberOfVisits++;

            Long restaurantId = userVisit.getRestaurant().getRestaurantId(); // Assumes userVisit has a Restaurant
            if (!visitedRestaurants.contains(restaurantId)) {
                visitedRestaurants.add(restaurantId);
                numberOfNewRestaurants++;
            }
        }

        return UserStatisticsDto.builder()
                .amountSpent(amountSpent)
                .numberOfVisits(numberOfVisits)
                .numberOfNewRestaurants(numberOfNewRestaurants)
                .build();
    }

    @Override
    public UserTopRestaurantsDto getUserTopRestaurants(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        Map<String, RestaurantStatisticsBuilder> restaurantStatsMap = createRestaurantStatsMap(user);
        List<UserRestaurantStatisticsDto> allRestaurantStats = createAllRestaurantStats(restaurantStatsMap);
        List<UserRestaurantStatisticsDto> mostVisited = getMostVisitedRestaurants(allRestaurantStats);
        List<UserRestaurantStatisticsDto> highestSpending = getHighestSpendingRestaurants(allRestaurantStats);
        List<UserRestaurantStatisticsDto> bestRated = getBestRatedRestaurants(allRestaurantStats);

        return UserTopRestaurantsDto.builder()
                .mostVisitedRestaurants(mostVisited)
                .highestSpendingRestaurants(highestSpending)
                .bestRatedRestaurants(bestRated)
                .build();
    }

    /**
     * Construit une map contenant les statistiques de chaque restaurant visité par l'utilisateur.
     *
     * @param user L'utilisateur dont on veut analyser les visites.
     * @return Une map où la clé est le nom du restaurant et la valeur est un builder de statistiques.
     */
    private Map<String, RestaurantStatisticsBuilder> createRestaurantStatsMap(User user) {
        Map<String, RestaurantStatisticsBuilder> restaurantStatsMap = new HashMap<>();
        for (UserVisit visit : user.getUserVisits()) {
            String restaurantName = visit.getRestaurant().getName();
            RestaurantStatisticsBuilder statsBuilder = restaurantStatsMap.getOrDefault(
                    restaurantName,
                    new RestaurantStatisticsBuilder(restaurantName));
            statsBuilder.addVisit(visit.getAmountSpent(), visit.getRating());
            restaurantStatsMap.put(restaurantName, statsBuilder);
        }
        return restaurantStatsMap;
    }

    /**
     * Transforme la map des statistiques des restaurants en une liste de RestaurantStatisticsDto.
     *
     * @param restaurantStatsMap La map des statistiques des restaurants.
     * @return Une liste de statistiques pour chaque restaurant.
     */
    private List<UserRestaurantStatisticsDto> createAllRestaurantStats(Map<String, RestaurantStatisticsBuilder> restaurantStatsMap) {
        return restaurantStatsMap.values().stream()
                .map(RestaurantStatisticsBuilder::build)
                .toList();
    }

    /**
     * Récupère les trois restaurants les plus visités à partir de la liste des statistiques.
     *
     * @param allRestaurantStats La liste de toutes les statistiques des restaurants.
     * @return Une liste des trois restaurants les plus visités.
     */
    private List<UserRestaurantStatisticsDto> getMostVisitedRestaurants(List<UserRestaurantStatisticsDto> allRestaurantStats) {
        return allRestaurantStats.stream()
                .sorted(Comparator.comparing(UserRestaurantStatisticsDto::getVisitCount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les trois restaurants où l'utilisateur a le plus dépensé.
     *
     * @param allRestaurantStats La liste de toutes les statistiques des restaurants.
     * @return Une liste des trois restaurants avec le plus de dépenses.
     */
    private List<UserRestaurantStatisticsDto> getHighestSpendingRestaurants(List<UserRestaurantStatisticsDto> allRestaurantStats) {
        return allRestaurantStats.stream()
                .sorted(Comparator.comparing(UserRestaurantStatisticsDto::getTotalAmountSpent).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les trois restaurants les mieux notés par l'utilisateur.
     *
     * @param allRestaurantStats La liste de toutes les statistiques des restaurants.
     * @return Une liste des trois restaurants les mieux notés.
     */
    private List<UserRestaurantStatisticsDto> getBestRatedRestaurants(List<UserRestaurantStatisticsDto> allRestaurantStats) {
        return allRestaurantStats.stream()
                .filter(stats -> stats.getAverageRating() != null)
                .sorted(Comparator.comparing(UserRestaurantStatisticsDto::getAverageRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Sauvegarde l'utilisateur en base de données.
     *
     * @param user L'utilisateur à sauvegarder.
     */
    private void saveUser(User user) {
        userRepository.save(user);
    }

    private static class RestaurantStatisticsBuilder {
        private final String restaurantName;
        private double totalAmount = 0.0;
        private double totalRating = 0.0;
        private int visitCount = 0;
        private int ratingCount = 0;

        public RestaurantStatisticsBuilder(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public void addVisit(Double amount, Double rating) {
            if (amount != null) {
                totalAmount += amount;
            }

            if (rating != null) {
                totalRating += rating;
                ratingCount++;
            }

            visitCount++;
        }

        public UserRestaurantStatisticsDto build() {
            Double averageRating = ratingCount > 0 ? Math.round((totalRating / ratingCount) * 100.0) / 100.0 : null;
            return UserRestaurantStatisticsDto.builder()
                    .restaurantName(restaurantName)
                    .totalAmountSpent(totalAmount)
                    .averageRating(averageRating)
                    .visitCount(visitCount)
                    .build();
        }
    }
}
