package br.com.marcia.error.service;

import br.com.marcia.error.entity.EventEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEventService extends IService<EventEntity> {

    Optional<EventEntity> findById(Long id);

    List<EventEntity> findAll(Example<EventEntity> eventEntityExample, Pageable pageable);
}
