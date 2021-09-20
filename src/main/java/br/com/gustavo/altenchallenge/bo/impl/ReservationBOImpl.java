package br.com.gustavo.altenchallenge.bo.impl;

import br.com.gustavo.altenchallenge.bo.ReservationBO;
import br.com.gustavo.altenchallenge.dao.ReservationDao;
import br.com.gustavo.altenchallenge.dao.RoomDao;
import br.com.gustavo.altenchallenge.enums.ReservationStatus;
import br.com.gustavo.altenchallenge.exception.NotFoundException;
import br.com.gustavo.altenchallenge.exception.PeriodExceedsException;
import br.com.gustavo.altenchallenge.exception.RoomAlreadyReservedException;
import br.com.gustavo.altenchallenge.mapper.ReservationMapper;
import br.com.gustavo.altenchallenge.model.ReservationRequest;
import br.com.gustavo.altenchallenge.model.ReservationResponse;
import br.com.gustavo.altenchallenge.model.ReservationUpdateRequest;
import br.com.gustavo.altenchallenge.model.db.Reservation;
import br.com.gustavo.altenchallenge.model.db.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationBOImpl implements ReservationBO {

    @Value("${api.config.reservation.maxium.duration:3}")
    private long maximumDaysAllowed;

    @Value("${api.config.reservation.maxium.days-in-advance:30}")
    private long maximumDaysInAdvance;

    private final ReservationDao dao;
    private final Clock clock;
    private final ReservationMapper mapper;
    private final RoomDao roomDao;

    @Override
    public void addReservation(Room room, ReservationRequest reservationRequest) {
        LocalDate now = LocalDate.now(clock);

        checkIfRoomIsReserved(room, now);
        checkReservationPeriodIsAlowed(reservationRequest);
        checkIfPeriodIsAllowed(reservationRequest, now);
        checkToken(room, reservationRequest);

        Reservation reservation = createReservation(room, reservationRequest, now);

        updateRoomToken(room);
        dao.save(reservation);
    }

    @Override
    public void checkToken(Room room, ReservationRequest reservationRequest) {
        if (!reservationRequest.getToken().equals(room.getToken())) {
            throw new RoomAlreadyReservedException("Ops! Someone else completed the reservation while you were making yours");
        }
    }

    @Override
    public void checkIfPeriodIsAllowed(ReservationRequest reservationRequest, LocalDate now) {
        long daysInAdvance = ChronoUnit.DAYS.between(now, reservationRequest.getDateStart());
        if (daysInAdvance > maximumDaysInAdvance) {
            throw new PeriodExceedsException(String.format("It is not possible to book with more than % days in advance", maximumDaysInAdvance));
        }
    }

    @Override
    public void checkReservationPeriodIsAlowed(ReservationRequest reservationRequest) {
        long duration = ChronoUnit.DAYS.between(reservationRequest.getDateEnd(), reservationRequest.getDateStart());

        if (duration > maximumDaysAllowed) {
            throw new PeriodExceedsException(String.format("The reservation duration cannot exceed %s days", maximumDaysAllowed));
        }
    }

    @Override
    public void checkIfRoomIsReserved(Room room, LocalDate now) {
        List<Reservation> reservations = room.getReservations()
                .stream()
                .filter(getActiveReservation(now))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(reservations)) {
            throw new RoomAlreadyReservedException("The room is already reserved");
        }
    }

    private Predicate<Reservation> getActiveReservation(LocalDate date) {
        return r -> r.getDateEnd().isAfter(date) && ReservationStatus.ACTIVE.equals(r.getStatus());
    }

    @Override
    public Reservation createReservation(Room room, ReservationRequest reservationRequest, LocalDate now) {
        Reservation reservation = new Reservation();
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setRoom(room);
        reservation.setDateStart(reservationRequest.getDateStart());
        reservation.setDateEnd(reservationRequest.getDateEnd());
        reservation.setCreated(now);
        return reservation;
    }

    @Override
    public List<ReservationResponse> convertReservationsToReservationResponses(List<Reservation> reservations) {
        return mapper.reservationsToReservationResponses(reservations);
    }

    @Override
    public void updateReservation(Long id, ReservationUpdateRequest body) {

        Reservation reservation;

        try {
            reservation = dao.findById(id);
        } catch (NotFoundException e) {
            Room room = roomDao.findById(body.getRoomId());
            reservation = new Reservation();
            reservation.setRoom(room);
            reservation.setCreated(LocalDate.now(clock));
        }

        Reservation updated = mapper.reservationUpdateRequestToReservation(body);
        updated.setId(reservation.getId());
        updated.setRoom(reservation.getRoom());

        dao.save(updated);
    }

    private void updateRoomToken(Room room){
        room.setToken(UUID.randomUUID());
        roomDao.save(room);
    }


}
