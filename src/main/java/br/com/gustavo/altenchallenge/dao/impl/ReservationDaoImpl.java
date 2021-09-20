package br.com.gustavo.altenchallenge.dao.impl;

import br.com.gustavo.altenchallenge.dao.ReservationDao;
import br.com.gustavo.altenchallenge.exception.NotFoundException;
import br.com.gustavo.altenchallenge.model.db.Reservation;
import br.com.gustavo.altenchallenge.model.db.Reservation_;
import br.com.gustavo.altenchallenge.model.filter.ReservationFilter;
import br.com.gustavo.altenchallenge.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class ReservationDaoImpl implements ReservationDao {

    private final ReservationRepository repository;

    @Override
    public List<Reservation> findAllByFilter(ReservationFilter reservationFilter) {

        Specification<Reservation> filter = buildFilter(reservationFilter);
        Sort sort = Sort.by(Reservation_.ID);

        return repository.findAll(filter, sort);
    }

    private Specification<Reservation> buildFilter(ReservationFilter reservationFilter) {
        return (root, criteriaQuery, criteriaBuilder) ->
                where(equalsId(reservationFilter.getId()))
                        .and(equalsStatus(reservationFilter.getStatus().name()))
                        .and(dateEndAfter(reservationFilter.getDateEnd()))
                        .and(roomIdIn(reservationFilter.getRoomIds()))
                .toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    private Specification<Reservation> dateEndAfter(LocalDate dateEnd) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (dateEnd == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Reservation_.dateEnd), dateEnd);
        };
    }

    private Specification<Reservation> roomIdIn(List<Long> roomIds) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(roomIds)) return null;
            return root.get(Reservation_.room).in(roomIds);
        };
    }


    private Specification<Reservation> equalsStatus(String status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (StringUtils.isNotBlank(status)) {
                return criteriaBuilder.equal(root.get(Reservation_.status), status);
            }
            return null;
        };
    }

    private Specification<Reservation> equalsId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (id != null) {
                return criteriaBuilder.equal(root.get(Reservation_.id), id);
            }
            return null;
        };
    }

    @Override
    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found"));
    }

}
