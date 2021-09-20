package br.com.gustavo.altenchallenge.dao.impl;

import br.com.gustavo.altenchallenge.dao.RoomDao;
import br.com.gustavo.altenchallenge.exception.NotFoundException;
import br.com.gustavo.altenchallenge.model.db.Room;
import br.com.gustavo.altenchallenge.model.db.Room_;
import br.com.gustavo.altenchallenge.model.filter.RoomFilter;
import br.com.gustavo.altenchallenge.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class RoomDaoImpl implements RoomDao {

    private final RoomRepository roomRepository;

    @Override
    public List<Room> findAllByRoomFilter(RoomFilter roomFilter) {
        Specification<Room> filter = buildFiter(roomFilter);
        Sort sort = Sort.by(Room_.ID);
        return roomRepository.findAll(filter, sort);
    }

    private Specification<Room> buildFiter(RoomFilter roomFilter) {
        return (root, criteriaQuery, criteriaBuilder) ->
                where(equalsId(roomFilter.getId()))
                .and(equalsRoomNumber(roomFilter.getRoomNumber()))
                .and(equalsUserId(roomFilter.getUserId()))
                .toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    private Specification<Room> equalsUserId(String userId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(StringUtils.isNotBlank(userId)) {
                return criteriaBuilder.equal(root.get(Room_.userId), userId);
            }
            return null;
        };
    }

    private Specification<Room> equalsRoomNumber(String roomNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(StringUtils.isNotBlank(roomNumber)) {
                return criteriaBuilder.equal(root.get(Room_.roomNumber), roomNumber);
            }
            return null;
        };
    }

    private Specification<Room> equalsId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (id != null) {
                return criteriaBuilder.equal(root.get(Room_.id), id);
            }
            return null;
        };
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
    }

    @Override
    public void save(Room updatedRoom) {
        roomRepository.save(updatedRoom);
    }
}
