package com.kenbem.primavera.models.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, Long> {

    @Query("select u from #{#entityName} as u where u.email = ?1")
    Optional<T> findByEmail(String email);

    @Query("select u from #{#entityName} as u where u.username = ?1")
    Optional<T> findByUsername(String username);

}
