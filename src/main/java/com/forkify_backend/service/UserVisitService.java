package com.forkify_backend.service;

import com.forkify_backend.api.dto.UserVisitDto;
import com.forkify_backend.persistence.entity.UserVisit;

public interface UserVisitService {

    /**
     * crée une visite d'un utilisateur
     * 
     * @param userVisitDto la visite à sauvergader en BDD
     * @return la visite créée
     */
    public UserVisit createUserVisit(UserVisitDto userVisitDto);

}
