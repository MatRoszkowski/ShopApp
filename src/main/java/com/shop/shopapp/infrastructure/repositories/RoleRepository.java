package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.Product;
import com.shop.shopapp.infrastructure.entities.Role;
import com.shop.shopapp.infrastructure.entities.RoleName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    @Query("SELECT DISTINCT role FROM roles role WHERE role.roleName = :rolename")
    Optional<Role> findByRoleName(@Param("rolename") RoleName roleName);
}
