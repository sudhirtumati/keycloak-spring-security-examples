package com.sudhirt.practice.security.keycloak.repository;

import com.sudhirt.practice.security.keycloak.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
