package br.com.marcia.error.entity;

import br.com.marcia.error.enumeration.LevelEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "event")
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 255)
    private String log;

    @Column(nullable = false, length = 100)
    private String origin;

    @Column(nullable = false)
    @Positive
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "user_event",
            joinColumns= @JoinColumn(name = "idEvent"),
            inverseJoinColumns = @JoinColumn(name = "idUser"))
    private Set<UserEntity> users = new HashSet<>();
}
