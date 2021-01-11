package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    @Query("SELECT u FROM users u WHERE UPPER(u.username) = UPPER(:username)")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM users u WHERE UPPER(u.id) = UPPER(:id)")
    Optional<User> findById(@Param("id") long id);

    Collection<User> findByUsernameIgnoreCaseContaining(String username);

    @Query("SELECT u FROM users u WHERE UPPER(u.username) LIKE UPPER(:username)")
    List<User> findAll(String username);
}
