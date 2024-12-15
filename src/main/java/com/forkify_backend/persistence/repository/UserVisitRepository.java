package com.forkify_backend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forkify_backend.persistence.entity.UserVisit;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {

}
