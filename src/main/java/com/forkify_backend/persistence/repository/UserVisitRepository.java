package com.forkify_backend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forkify_backend.persistence.entity.UserVisit;

public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {

}
