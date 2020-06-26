package br.com.marcia.error.service;

import br.com.marcia.error.entity.EventEntity;
import br.com.marcia.error.repository.EventRepository;
import br.com.marcia.error.security.LoggedUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService implements IEventService {

    private EventRepository eventRepository;

    private LoggedUserService loggedUserService;

    @Override
    public EventEntity save(EventEntity eventEntity) {

        Optional<EventEntity> foundEvent = eventRepository.
                findByDescriptionAndLevelAndLogAndOrigin(eventEntity.getDescription(),
                        eventEntity.getLevel(),
                        eventEntity.getLog(),
                        eventEntity.getOrigin());

        if(foundEvent.isPresent()) {
            foundEvent.get().setQuantity(foundEvent.get().getQuantity() + 1);
            foundEvent.get().getUsers().add(loggedUserService.getLoggedUser());
            return eventRepository.save(foundEvent.get());
        }

        eventEntity.setQuantity(1);
        eventEntity.getUsers().add(loggedUserService.getLoggedUser());
        return eventRepository.save(eventEntity);

    }

    public Optional<EventEntity> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<EventEntity> findAll(Example<EventEntity> eventEntityExample, Pageable pageable) {
        return eventRepository.findAll(eventEntityExample, pageable).getContent();
    }

}
