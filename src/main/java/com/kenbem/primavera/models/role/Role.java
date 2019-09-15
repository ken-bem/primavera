package com.kenbem.primavera.models.role;

import com.kenbem.primavera.models.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

}
