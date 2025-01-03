package com.forkify_backend.service;

import java.util.List;

import com.forkify_backend.api.dto.UserDto;
import com.forkify_backend.api.dto.UserStatisticsDto;
import com.forkify_backend.persistence.entity.User;

public interface UserService {

    /**
     * crée un user
     * 
     * @param userDto le user à sauvergader en BDD
     * @return le user créé
     */
    public User createUser(UserDto userDto);

    public List<UserDto> getAllUsers();

    /**
     * récupère les statistics globales d'un user
     * 
     * @param userId l'id de l'utilisateur
     * @return le dto contenant les statistiques globales de l'utilisateur
     */
    public UserStatisticsDto getUserStatistics(Long userId);

}
