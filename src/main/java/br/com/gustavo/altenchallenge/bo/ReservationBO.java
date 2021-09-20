package br.com.gustavo.altenchallenge.bo;

import br.com.gustavo.altenchallenge.model.ReservationRequest;
import br.com.gustavo.altenchallenge.model.ReservationResponse;
import br.com.gustavo.altenchallenge.model.ReservationUpdateRequest;
import br.com.gustavo.altenchallenge.model.db.Reservation;
import br.com.gustavo.altenchallenge.model.db.Room;

import java.time.LocalDate;
import java.util.List;

public interface ReservationBO {
    void addReservation(Room room, ReservationRequest reservationRequest);

    void checkToken(Room room, ReservationRequest reservationRequest);

    void checkIfPeriodIsAllowed(ReservationRequest reservationRequest, LocalDate now);

    void checkReservationPeriodIsAlowed(ReservationRequest reservationRequest);

    void checkIfRoomIsReserved(Room room, LocalDate now);

    Reservation createReservation(Room room, ReservationRequest reservationRequest, LocalDate now);

    List<ReservationResponse> convertReservationsToReservationResponses(List<Reservation> reservations);

    void updateReservation(Long id, ReservationUpdateRequest body);
}
