package br.com.gustavo.altenchallenge.mapper;

import br.com.gustavo.altenchallenge.model.RoomResponse;
import br.com.gustavo.altenchallenge.model.RoomUpdateRequest;
import br.com.gustavo.altenchallenge.model.db.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReservationMapper.class})
public interface RoomMapper {

    RoomResponse roomToRoomResponse(Room room);
    List<RoomResponse> roomsToRoomResponses(List<Room> rooms);
    Room roomUpdateRequestToRoom(RoomUpdateRequest roomUpdateRequest);

}
