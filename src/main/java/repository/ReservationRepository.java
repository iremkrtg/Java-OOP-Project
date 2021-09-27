package repository;

import model.Reservation;
import model.ReservationStatus;

import java.util.List;

public interface ReservationRepository extends Repo<Reservation, Integer> {
    List<Reservation> showAll();
    public List<Reservation> filterByStatus(ReservationStatus status);
    public List<Reservation> filterByDate(String date);
}

