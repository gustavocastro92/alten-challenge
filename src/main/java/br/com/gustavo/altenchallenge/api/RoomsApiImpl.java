package br.com.gustavo.altenchallenge.api;

import br.com.gustavo.altenchallenge.bo.RoomBO;
import br.com.gustavo.altenchallenge.model.ReservationRequest;
import br.com.gustavo.altenchallenge.model.ReservationResponse;
import br.com.gustavo.altenchallenge.model.RoomResponse;
import br.com.gustavo.altenchallenge.model.RoomUpdateRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomsApiImpl implements RoomsApi {

    private final RoomBO roomBO;

    @Override
    public ResponseEntity<List<RoomResponse>> getRooms(@Parameter(in = ParameterIn.QUERY, schema=@Schema()) @Valid @RequestParam(value = "id", required = false) Long id,
                                                       @Parameter(in = ParameterIn.QUERY, schema=@Schema()) @Valid @RequestParam(value = "roomNumber", required = false) String roomNumber,
                                                       @Parameter(in = ParameterIn.QUERY, schema=@Schema()) @Valid @RequestParam(value = "userId", required = false) String userId) {
        List<RoomResponse> rooms = roomBO.getRoomResponse(id, roomNumber, userId);

        if (!CollectionUtils.isEmpty(rooms)) {
            return ResponseEntity.ok(rooms);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateRoom(@Parameter(in = ParameterIn.PATH, description = "Room id", required=true, schema=@Schema()) @PathVariable("id") Long id,
                                           @Parameter(in = ParameterIn.DEFAULT, required=true, schema=@Schema()) @Valid @RequestBody RoomUpdateRequest body) {
        roomBO.updateRoom(id, body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> addReservation(@Parameter(in = ParameterIn.PATH, description = "Room id", required=true, schema=@Schema()) @PathVariable("id") Long roomId,
                                               @Parameter(in = ParameterIn.DEFAULT, required=true, schema=@Schema()) @Valid @RequestBody ReservationRequest reservationRequest) {
        roomBO.addReservation(roomId, reservationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ReservationResponse>> getReservation(Long roomId) {

        List<ReservationResponse> reservationResponses = roomBO.getRoomReservations(roomId);

        if (!CollectionUtils.isEmpty(reservationResponses)) {
            return ResponseEntity.ok(reservationResponses);
        }
        return ResponseEntity.noContent().build();
    }
}
