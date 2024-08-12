package com.bikash.repository;

import com.bikash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String username);

}
