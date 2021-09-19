package br.com.gustavo.altenchallenge.api;

import br.com.gustavo.altenchallenge.bo.ReservationBO;
import br.com.gustavo.altenchallenge.model.ReservationUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationsApiImpl implements ReservationsApi {

    private final ReservationBO reservationBO;

    @Override
    public ResponseEntity<Void> updateReservation(Long id, ReservationUpdateRequest body) {
        reservationBO.updateReservation(id, body);
        return ResponseEntity.ok().build();
    }
}
