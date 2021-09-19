package br.com.gustavo.altenchallenge.bo;

import br.com.gustavo.altenchallenge.dao.RoomDao;
import br.com.gustavo.altenchallenge.exception.NotFoundException;
import br.com.gustavo.altenchallenge.mapper.RoomMapper;
import br.com.gustavo.altenchallenge.model.ReservationRequest;
import br.com.gustavo.altenchallenge.model.ReservationResponse;
import br.com.gustavo.altenchallenge.model.RoomResponse;
import br.com.gustavo.altenchallenge.model.RoomUpdateRequest;
import br.com.gustavo.altenchallenge.model.db.Room;
import br.com.gustavo.altenchallenge.model.filter.RoomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomBO {

    private final RoomDao roomDao;
    private final RoomMapper roomMapper;
    private final ReservationBO reservationBO;

    public List<RoomResponse> getRoomResponse(Long id, String roomNumber, String userId) {

        List<Room> rooms = getRooms(id, roomNumber, userId);
        return roomMapper.roomsToRoomResponses(rooms);
    }

    private List<Room> getRooms(Long id, String roomNumber, String userId) {
        RoomFilter filter = RoomFilter.builder()
                .id(id)
                .roomNumber(roomNumber)
                .userId(userId)
                .build();

        return roomDao.findAllByRoomFilter(filter);
    }

    public void updateRoom(Long id, RoomUpdateRequest body) {

        Room room;
        try {
            room = roomDao.findById(id);
        } catch (NotFoundException e) {
            room = new Room();
        }
        Room updatedRoom = roomMapper.roomUpdateRequestToRoom(body);
        updatedRoom.setReservations(room.getReservations());
        updatedRoom.setId(room.getId());
        roomDao.save(updatedRoom);

    }

    public void addReservation(Long roomId, ReservationRequest reservationRequest) {
        Room room = roomDao.findById(roomId);
        reservationBO.addReservation(room, reservationRequest);
    }

    public List<ReservationResponse> getRoomReservations(Long roomId) {
        Room room = roomDao.findById(roomId);
        return reservationBO.convertReservationsToReservationResponses(room.getReservations());
    }

    public Room getById(Long id) {
        return roomDao.findById(id);
    }
}
