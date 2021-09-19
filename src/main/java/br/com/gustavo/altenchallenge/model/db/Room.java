package br.com.gustavo.altenchallenge.model.db;

import br.com.gustavo.altenchallenge.enums.RoomStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String roomNumber;

    @NotNull
    private String userId;

    @NotNull
    private String description;

    @NotNull
    private RoomStatus status;

    @NotNull
    private UUID token;

    @OneToMany(targetEntity = Reservation.class, mappedBy = Reservation_.ROOM, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Reservation> reservations;
}
