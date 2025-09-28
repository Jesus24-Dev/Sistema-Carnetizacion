
package com.jesus24dev.carnetizacion.repository;

import com.jesus24dev.carnetizacion.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
