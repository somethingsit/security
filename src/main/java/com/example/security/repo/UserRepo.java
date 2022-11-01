package com.example.security.repo;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findAllByUsernameAndStatus(String username, Enum status);
}
