package br.com.gustavo.altenchallenge.model.filter;

import br.com.gustavo.altenchallenge.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ReservationFilter {

    private Long id;
    private ReservationStatus status;
    private LocalDate dateEnd;
    private List<Long> roomIds;
}
