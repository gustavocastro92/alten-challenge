package br.com.gustavo.altenchallenge.mapper;

import br.com.gustavo.altenchallenge.model.ReservationResponse;
import br.com.gustavo.altenchallenge.model.ReservationUpdateRequest;
import br.com.gustavo.altenchallenge.model.db.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mappings({
            @Mapping(target = "roomId", source = "room.id"),
    })
    List<ReservationResponse> reservationsToReservationResponses(List<Reservation> reservations);

    Reservation reservationUpdateRequestToReservation(ReservationUpdateRequest request);
}
