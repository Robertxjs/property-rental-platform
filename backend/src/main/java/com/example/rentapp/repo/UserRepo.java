package com.example.rentapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;  
import com.example.rentapp.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

}
