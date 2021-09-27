package repository;

import model.Reservation;
import model.ReservationStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationInMemoryRepository extends AbstractRepo<Reservation, Integer> implements ReservationRepository{

    @Override
    public List<Reservation> showAll(){
        return getAll().stream().collect(Collectors.toList());
    }
    @Override
    public List<Reservation> filterByStatus(ReservationStatus status) {
        return getAll().stream().filter(x->x.getStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> filterByDate(String date) {
        return getAll().stream().filter(x->x.getStart_date().equals(date)).collect(Collectors.toList());
    }
}
