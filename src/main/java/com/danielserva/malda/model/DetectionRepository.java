package com.danielserva.malda.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionRepository extends JpaRepository<Detection,Long> {
    List<Detection> findByType(DetectionType dt);
    Optional<Detection> findOneByUuid(UUID uuid);
    
}
