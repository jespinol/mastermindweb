package org.jmel.mastermindweb.dto;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
    Iterable<Session> findByCreatedAtBefore(LocalDateTime cutoff);
}
