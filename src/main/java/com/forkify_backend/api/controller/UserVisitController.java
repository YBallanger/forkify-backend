package com.forkify_backend.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.forkify_backend.api.dto.UserVisitDto;
import com.forkify_backend.persistence.entity.UserVisit;
import com.forkify_backend.service.UserVisitService;

import lombok.AllArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/user/visits")
public class UserVisitController {

    private final UserVisitService userVisitService;

    @PostMapping("")
    public ResponseEntity<Void> createUserVisit(@RequestBody UserVisitDto userVisitDto) {
        UserVisit userVisit = userVisitService.createUserVisit(userVisitDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userVisit.getUserVisitId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

}
