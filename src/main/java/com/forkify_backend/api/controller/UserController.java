package com.forkify_backend.api.controller;

import com.forkify_backend.api.dto.*;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.security.CustomUserDetails;
import com.forkify_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userSignupDto les informations de l'utilisateur à créer
     * @return une réponse HTTP 201 Created avec l'URI de la ressource créée
     */
    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody UserSignupDto userSignupDto) {
        User user = userService.createUser(userSignupDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return une liste d'utilisateurs
     */
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    /**
     * Récupère les visites de l'utilisateur actuellement connecté.
     *
     * @param userDetails les détails de l'utilisateur authentifié
     * @return un ensemble de visites de l'utilisateur
     */
    @GetMapping("/visits")
    public ResponseEntity<Set<UserVisitDto>> getConnectedUserVisits(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Set<UserVisitDto> userVisitDtos = userService.getUserVisits(userDetails.getId());
        return ResponseEntity.ok(userVisitDtos);
    }

    /**
     * Retourne les statistiques de l'utilisateur connecté.
     *
     * @param userDetails Les informations de l'utilisateur connecté.
     * @return Les statistiques de l'utilisateur connecté.
     */
    @GetMapping("/statistics")
    public ResponseEntity<UserStatisticsDto> getConnectedUserStatistics(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserStatisticsDto userStatisticsDto = userService.getUserStatistics(userDetails.getId());
        return ResponseEntity.ok(userStatisticsDto);
    }

    /**
     * Récupère les statistiques des restaurants préférés de l'utilisateur connecté.
     *
     * @param userDetails les détails de l'utilisateur authentifié
     * @return les trois listes des restaurants préférés de l'utilisateur
     */
    @GetMapping("/top-restaurants")
    public ResponseEntity<UserTopRestaurantsDto> getConnectedUserTopRestaurants(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserTopRestaurantsDto topRestaurants = userService.getUserTopRestaurants(userDetails.getId());
        return ResponseEntity.ok(topRestaurants);
    }
}
