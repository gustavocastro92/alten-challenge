package br.com.gustavo.altenchallenge.model.db;

import br.com.gustavo.altenchallenge.enums.ReservationStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDate dateStart;

    @NotNull
    private LocalDate dateEnd;

    @NotNull
    private LocalDate created;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="id_room")
    @NotNull
    private Room room;

}
