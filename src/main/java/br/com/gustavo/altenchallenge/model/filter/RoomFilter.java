package br.com.gustavo.altenchallenge.model.filter;

import br.com.gustavo.altenchallenge.enums.RoomStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class RoomFilter {

    private Long id;
    private String roomNumber;
    private String userId;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
}
