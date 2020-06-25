package br.com.marcia.error.repository;

import br.com.marcia.error.entity.EventEntity;

import br.com.marcia.error.enumeration.LevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByDescriptionAndLevelAndLogAndOrigin(String description,
                                                                   LevelEnum level,
                                                                   String Log,
                                                                   String origin);
}
