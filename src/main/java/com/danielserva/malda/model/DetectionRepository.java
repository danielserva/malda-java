package com.danielserva.malda.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionRepository extends JpaRepository<Detection,Long> {
    List<Detection> findByType(DetectionType dt);
    List<Detection> findByUuid(UUID uuid);
    List<Detection> findAllByTypeOrderByTimeDesc(DetectionType dt);

    // List<Detection> findAllByExample(Example<Detection> example, Sort sort);
    
}
