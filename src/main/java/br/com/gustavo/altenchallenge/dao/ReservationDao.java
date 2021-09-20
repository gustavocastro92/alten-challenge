package br.com.gustavo.altenchallenge.dao;

import br.com.gustavo.altenchallenge.model.db.Reservation;
import br.com.gustavo.altenchallenge.model.filter.ReservationFilter;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAllByFilter(ReservationFilter reservationFilter);

    Reservation save(Reservation reservation);

    Reservation findById(Long id);
}
