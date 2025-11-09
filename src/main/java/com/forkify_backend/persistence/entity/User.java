package com.forkify_backend.persistence.entity;

import com.forkify_backend.api.dto.UserSignupDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "roles", nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<UserVisit> userVisits;

    public User(UserSignupDto userSignupDto, Set<Role> roles) {
        this.userId = userSignupDto.getId();
        this.email = userSignupDto.getEmail();
        this.username = userSignupDto.getUsername();
        this.roles = roles;
    }
}
