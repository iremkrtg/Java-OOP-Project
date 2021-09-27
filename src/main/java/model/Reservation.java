package  model;

import java.io.Serializable;

public class Reservation implements Identifiable<Integer> , Serializable {

    private int res_id;
    private Car wanted_car;
    private String client_name;
    private String start_date;
    private int nr_days;
    private ReservationStatus status; // active/inactive
    // + status (active/cancelled)

    public Car getWanted_car(){ return this.wanted_car; }
    public void setWanted_car(Car wanted_car_id){ this.wanted_car = wanted_car; }

    public String getClient_name(){ return this.client_name; }
    public void setClient_name(String client_name){ this.client_name = client_name; }

    public String getStart_date(){ return this.start_date; }
    public void setStart_date(String start_date){ this.start_date = start_date; }

    public int getNr_days(){ return this.nr_days; }
    public void setNr_days(int nr_days){ this.nr_days = nr_days; }

    public ReservationStatus getStatus(){ return this.status; }
    public void setStatus(ReservationStatus status){ this.status = status; }

    public Reservation(){
        this.res_id = 0;
        this.wanted_car = new Car();
        this.client_name = "Joe";
        this.start_date = "01.01.2000";
        this.nr_days = 0;
        this.status = ReservationStatus.Unknown;
    }

    public Reservation(Car wanted_car, String client_name, String start_date, int nr_days){
        this.wanted_car = wanted_car;
        this.client_name = client_name;
        this.start_date = start_date;
        this.nr_days = nr_days;
        this.status = ReservationStatus.Active;
    }

    public Reservation(int res_id, Car wanted_car, String client_name, String start_date, int nr_days){
        this.res_id = res_id;
        this.wanted_car = wanted_car;
        this.client_name = client_name;
        this.start_date = start_date;
        this.nr_days = nr_days;
        this.status = ReservationStatus.Active;
    }

    public String toString(){
        String str = "Reservation " + res_id + ": Car " + wanted_car + " from " + start_date + " for " + nr_days + " days. Client's name: " + client_name +". Status: " + status;
        return str;
    }

    @Override
    public Integer getID() {
        return res_id;
    }

    @Override
    public void setID(Integer res_id) { this.res_id = res_id; }
}
