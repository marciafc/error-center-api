package br.com.marcia.error.api.v1.controller;

import br.com.marcia.error.api.v1.dto.EventDTO;
import br.com.marcia.error.api.v1.dto.EventSaveDTO;
import br.com.marcia.error.api.v1.dto.EventListDTO;
import br.com.marcia.error.entity.EventEntity;
import br.com.marcia.error.entity.UserEntity;
import br.com.marcia.error.enumeration.LevelEnum;
import br.com.marcia.error.service.IEventService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Api(tags = {"event"})
@RestController
@RequestMapping("/v1/event")
public class EventController {

    @Autowired
    private IEventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Add a new error log event")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created")
    })
    public ResponseEntity<EventDTO> create(@RequestHeader(name = "Authorization") String authorization,
                                           @Valid @RequestBody EventSaveDTO eventSaveDTO) {
        EventEntity eventEntity = modelMapper.map(eventSaveDTO, EventEntity.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(eventService.save(eventEntity), EventDTO.class));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find a error log event by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Error log event not found")
    })
    public ResponseEntity<EventDTO> findById(@RequestHeader(name = "Authorization") String authorization,
                                             @PathVariable Long id) {

        Optional<EventEntity> eventEntity = eventService.findById(id);

        if(eventEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(modelMapper.map(eventEntity.get(), EventDTO.class));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    @ApiOperation(value = "The search can be performed by the fields below. " +
            "The log field will not be displayed on return. To obtain it, use the search by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public ResponseEntity<List<EventListDTO>> findBy(@RequestHeader(name = "Authorization") String authorization,
                                                     @RequestParam(value = "level", required = false) LevelEnum level,
                                                     @RequestParam(value = "description", required = false) String description,
                                                     @RequestParam(value = "log", required = false) String log,
                                                     @RequestParam(value = "origin", required = false) String origin,
                                                     @RequestParam(value = "quantity", required = false) Integer quantity,
                                                     Pageable pageable) {

        List<EventEntity> eventEntityList = eventService
                .findAll(prepareEventMatcher(level, description, log, origin, quantity), pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(converterEventEntityListToDTOList(eventEntityList));
    }

    private List<EventListDTO> converterEventEntityListToDTOList(List<EventEntity> eventEntityList) {
        return eventEntityList
                .stream()
                .map(event -> modelMapper
                        .map(event, EventListDTO.class))
                .collect(Collectors.toList());
    }

    private Example<EventEntity> prepareEventMatcher(LevelEnum level,
                                                     String description,
                                                     String log,
                                                     String origin,
                                                     Integer quantity) {
        EventEntity eventEntity = EventEntity
                .builder()
                .level(level)
                .description(description)
                .log(log)
                .origin(origin)
                .quantity(quantity)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("level", exact())
                .withMatcher("description", contains().ignoreCase())
                .withMatcher("log", contains().ignoreCase())
                .withMatcher("origin", exact())
                .withMatcher("quantity", exact());

        return Example.of(eventEntity, matcher);
    }

}
