package service;

import model.Car;
import model.Reservation;
import model.ReservationStatus;
import repository.CarRepository;
import repository.Repo;
import repository.RepositoryException;
import repository.ReservationRepository;

import java.lang.module.ResolutionException;
import java.util.List;

public class RentalService {

    private CarRepository carRepository;
    private ReservationRepository reservationRepository;


    public CarRepository getCarRepository() {
        return carRepository;
    }

    public ReservationRepository getReservationRepository(){
        return reservationRepository;
    }

    public RentalService(CarRepository carRepository, ReservationRepository reservationRepository){
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }


    //CARS
    public int addCar(String manufacturer, String model,  double engine, int price, String color){
        try{
            Car car = new Car(manufacturer, model, engine, price, color);
            Car newCar = carRepository.add(car);
            return newCar.getID();
        }catch(RepositoryException ex){
            throw new ServiceException("Error adding car." + ex);
        }
    }

    public void deleteCar(int carId){
        try{
            carRepository.delete(carId);
        }catch (RepositoryException ex){
            throw new ServiceException("Error deleting car. " + ex);
        }
    }

    public void updateCar(int carId, Car new_car){
        try{
            //System.out.println("merge");
            carRepository.update(carId, new_car);
        }catch (RepositoryException ex){
            throw new ServiceException("Error updating car. " + ex);
        }
    }

    public List<Car> listAllCars(){
        return carRepository.showAll();
    }

    public List<Car> filterCarsByPrice(int price){
        try{
            return carRepository.filterByPrice(price);
        }catch(RepositoryException ex){
            throw new ServiceException("Error filtering by price." + ex);
        }
    }

    public List<Car> filterCarsByManufacturer(String manufacturer){
        try{
            return carRepository.filterByManufacturer(manufacturer);
        }catch(RepositoryException ex){
            throw new ServiceException("Error filtering by manufacturer. " + ex);
        }
    }


    //RESERVATIONS
    public int addReservation(Car car, String clientName, String date, int nrDays){
        try{
            Reservation reservation = new Reservation(car, clientName, date, nrDays);
            Reservation newReservation = reservationRepository.add(reservation);
            return newReservation.getID();
        }catch(RepositoryException er){
            throw new RuntimeException("Error adding reservation." + er);
        }
    }

    public void deleteReservation(int reservationID){
        try{
            reservationRepository.delete(reservationID);
        }catch(RepositoryException ex){
            throw new ServiceException("Error deleting reservation. " + ex);
        }
    }

    public void updateReservation(int reservationId, Reservation newReservation){
        try{
            reservationRepository.update(reservationId, newReservation);
        } catch (RepositoryException ex){
            throw new ServiceException("Error updating reservation. " + ex);
        }
    }

    public List<Reservation> listAllReservations(){ return reservationRepository.showAll();}

    public List<Reservation> filterReservationByStatus(ReservationStatus status){
        try {
            return reservationRepository.filterByStatus(status);
        }catch (RepositoryException ex){
            throw new ServiceException("Error filtering by status." + ex);
        }
    }

    public List<Reservation> filterReservationByDate(String date){
        try{
            return reservationRepository.filterByDate(date);
        }catch(RepositoryException ex){
            throw new ServiceException("Error filtering by date." + ex);
        }
    }



}

