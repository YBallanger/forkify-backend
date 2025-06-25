package com.forkify_backend.service.impl;

import com.forkify_backend.persistence.entity.Role;
import com.forkify_backend.persistence.repository.RoleRepository;
import com.forkify_backend.security.RoleConstants;
import com.forkify_backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getDefaultRole() {
        return Set.of(roleRepository.findByName(RoleConstants.USER)
                .orElseThrow(() -> new IllegalArgumentException("Role 'USER' not found in the database."))
        );
    }
}
