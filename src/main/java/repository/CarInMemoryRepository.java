package repository;

import model.Car;

import java.util.List;
import java.util.stream.Collectors;

public class CarInMemoryRepository extends AbstractRepo<Car, Integer> implements CarRepository{


    @Override
    public List<Car> showAll() {
        return getAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<Car> filterByManufacturer(String manufacturer) {
        return getAll().stream().filter(x->x.getManufacturer().equals(manufacturer)).collect(Collectors.toList());
    }

    @Override
    public List<Car> filterByPrice(int price) {
        return getAll().stream().filter(x->x.getPrice() <= price).collect(Collectors.toList());
    }
}
