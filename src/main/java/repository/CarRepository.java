package repository;

import model.Car;

import java.util.List;

public interface CarRepository extends Repo<Car, Integer>{
    List<Car> showAll();
    List<Car> filterByManufacturer(String manufacturer);
    List<Car> filterByPrice(int price);
}
