
package com.jesus24dev.carnetizacion.repository;

import com.jesus24dev.carnetizacion.models.Images;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, UUID> {
    
}
