package com.forkify_backend.service;

import com.forkify_backend.persistence.entity.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getDefaultRole();
}
