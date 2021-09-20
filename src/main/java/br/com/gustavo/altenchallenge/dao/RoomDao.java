package br.com.gustavo.altenchallenge.dao;

import br.com.gustavo.altenchallenge.model.db.Room;
import br.com.gustavo.altenchallenge.model.filter.RoomFilter;

import java.util.List;

public interface RoomDao {
    List<Room> findAllByRoomFilter(RoomFilter roomFilter);

    Room findById(Long id);

    void save(Room updatedRoom);
}
