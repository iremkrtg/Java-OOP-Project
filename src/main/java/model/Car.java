package model;


import java.io.Serializable;

public class Car implements Identifiable<Integer>, Serializable, Comparable<Car> {
    private int car_id;
    private String manufacturer;
    private String model;
    private double engine;
    private int price;
    private String color;
    private CarStatus status;

    Car(){
        this.car_id = 0;
        this.manufacturer = "";
        this.model = "";
        this.engine = 0;
        this.price = 0;
        this.color = "";
        this.status = CarStatus.Unknown;
    }


    public Car(String manufacturer, String model, double engine, int price, String color){
        this.manufacturer = manufacturer;
        this.model = model;
        this.engine = engine;
        this.price = price;
        this.color = color;
        this.status = CarStatus.Available;
    }

    public Car(int car_id, String manufacturer, String model, double engine, int price, String color){
        this.car_id = car_id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.engine = engine;
        this.price = price;
        this.color = color;
        this.status = CarStatus.Available;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public String getModel(){
        return model;
    }

    public  double getEngine(){
        return engine;
    }

    public int getPrice(){
        return price;
    }

    public String getColor(){
        return color;
    }

    public CarStatus getStatus(){ return status; }

    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setEngine(double engine){
        this.engine = engine;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setStatus(CarStatus status){ this.status = status; }

    public String toString(){
        String str = "Car: " + car_id + " - " + manufacturer + ", " + model + ", " + engine + ", " + color + ", price per day - " + price + " euro.";
        return str;
    }

    public int compareTo(Car c){
        return c.price - price;
    }

    @Override
    public Integer getID() {
        return car_id;
    }

    @Override
    public void setID(Integer car_id) {
        this.car_id = car_id;
    }
}
