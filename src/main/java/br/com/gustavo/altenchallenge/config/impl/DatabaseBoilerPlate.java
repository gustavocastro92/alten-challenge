package br.com.gustavo.altenchallenge.config.impl;

import br.com.gustavo.altenchallenge.enums.ReservationStatus;
import br.com.gustavo.altenchallenge.enums.RoomStatus;
import br.com.gustavo.altenchallenge.model.db.Reservation;
import br.com.gustavo.altenchallenge.model.db.Room;
import br.com.gustavo.altenchallenge.repository.ReservationRepository;
import br.com.gustavo.altenchallenge.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseBoilerPlate {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @PostConstruct
    private void initDb() {

        Room room = createRoom();
        createReservation(room);

    }

    private void createReservation(Room saved) {
        Reservation reservation = new Reservation();
        reservation.setCreated(LocalDate.now());
        reservation.setDateEnd(LocalDate.now().plusDays(3));
        reservation.setDateStart(LocalDate.now());
        reservation.setRoom(saved);
        reservation.setStatus(ReservationStatus.ACTIVE);

        reservationRepository.save(reservation);
    }

    private Room createRoom() {
        Room room = new Room();
        room.setId(123L);
        room.setDescription("The only room in the hotel");
        room.setUserId("10120113391");
        room.setRoomNumber("221B");
        room.setStatus(RoomStatus.ACTIVE);
        room.setToken(UUID.randomUUID());

        return roomRepository.save(room);
    }
}
