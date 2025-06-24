package com.forkify_backend.api.controller;

import com.forkify_backend.api.dto.UserDto;
import com.forkify_backend.api.dto.UserStatisticsDto;
import com.forkify_backend.api.dto.UserTopRestaurantsDto;
import com.forkify_backend.api.dto.UserVisitDto;
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
     * @param userDto les informations de l'utilisateur à créer
     * @return une réponse HTTP 201 Created avec l'URI de la ressource créée
     */
    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);

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
        Set<UserVisitDto> userVisitDtos = userService.getConnectedUserVisit(userDetails.getId());
        return ResponseEntity.ok(userVisitDtos);
    }

    /**
     * Récupère les statistiques d'un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur
     * @return les statistiques de l'utilisateur
     */
    @GetMapping("/{id}/statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(@PathVariable String id) {
        UserStatisticsDto userStatisticsDto = userService.getUserStatistics(id);
        return ResponseEntity.ok(userStatisticsDto);
    }

    /**
     * Récupère les statistiques des restaurants préférés de l'utilisateur connecté.
     *
     * @param userDetails les détails de l'utilisateur authentifié
     * @return les trois listes des restaurants préférés de l'utilisateur
     */
    @GetMapping("/top-restaurants")
    public ResponseEntity<UserTopRestaurantsDto> getUserTopRestaurants(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserTopRestaurantsDto topRestaurants = userService.getUserTopRestaurants(userDetails.getId());
        return ResponseEntity.ok(topRestaurants);
    }
}
