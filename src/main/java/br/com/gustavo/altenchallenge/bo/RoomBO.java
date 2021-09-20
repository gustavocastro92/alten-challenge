package br.com.gustavo.altenchallenge.bo;

import br.com.gustavo.altenchallenge.model.ReservationRequest;
import br.com.gustavo.altenchallenge.model.ReservationResponse;
import br.com.gustavo.altenchallenge.model.RoomResponse;
import br.com.gustavo.altenchallenge.model.RoomUpdateRequest;
import br.com.gustavo.altenchallenge.model.db.Room;

import java.util.List;

public interface RoomBO {
    List<RoomResponse> getRoomResponse(Long id, String roomNumber, String userId);

    void updateRoom(Long id, RoomUpdateRequest body);

    void addReservation(Long roomId, ReservationRequest reservationRequest);

    List<ReservationResponse> getRoomReservations(Long roomId);

    Room getById(Long id);
}
