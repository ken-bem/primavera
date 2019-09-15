package com.kenbem.primavera.models.user;

import com.kenbem.primavera.models.role.Role;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String email;

    private String password;
    @Transient //Used to verify that the password is correct
    private String matchingPassword;

    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
