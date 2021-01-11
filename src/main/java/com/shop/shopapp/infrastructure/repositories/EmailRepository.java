package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.Email;
import com.shop.shopapp.infrastructure.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email,Long> {

}
