package com.madaproject.Medikit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.madaproject.Medikit.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
