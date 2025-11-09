package com.forkify_backend.service;

import com.forkify_backend.api.dto.*;
import com.forkify_backend.persistence.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * crée un user
     *
     * @param userSignupDto le user à sauvergader en BDD
     * @return le user créé
     */
    public User createUser(UserSignupDto userSignupDto);

    public List<UserDto> getAllUsers();

    public Set<UserVisitDto> getUserVisits(String userId);

    /**
     * récupère les statistics globales d'un user
     *
     * @param userId l'id de l'utilisateur
     * @return le dto contenant les statistiques globales de l'utilisateur
     */
    public UserStatisticsDto getUserStatistics(String userId);

    /**
     * récupère les statistiques des restaurants préférés d'un utilisateur
     *
     * @param userId l'id de l'utilisateur
     * @return le dto contenant les 3 listes de restaurants préférés
     */
    public UserTopRestaurantsDto getUserTopRestaurants(String userId);

}
