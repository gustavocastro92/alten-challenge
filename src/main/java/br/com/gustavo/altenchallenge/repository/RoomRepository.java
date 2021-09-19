package br.com.gustavo.altenchallenge.repository;

import br.com.gustavo.altenchallenge.model.ReservationStatus;
import br.com.gustavo.altenchallenge.model.db.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
}
