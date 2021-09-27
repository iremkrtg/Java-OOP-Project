package repository;

import model.Car;
import model.Reservation;
import model.ReservationStatus;

import java.io.*;
import java.util.Collection;

public class ReservationFileRepository extends ReservationInMemoryRepository{
     private String filename;
     private CarRepository carRepository;
     private static int idGenerator = 0;

     public ReservationFileRepository(String filename, CarRepository carRepository){
         this.filename = filename;
         this.carRepository = carRepository;
         readFromFile();
     }

     private void readFromFile(){
         try(BufferedReader br = new BufferedReader(new FileReader(filename))){
             String line = br.readLine();
             try{
                 idGenerator = Integer.parseInt(line);
             }catch(NumberFormatException ex){
                 System.err.println("Invalid value for id. Starting from 0." + ex);
             }
             while((line = br.readLine()) != null){
                 String[] elements = line.split((";"));

                 if(elements.length != 6){
                     System.err.println("Invalid line " + line);
                     continue;
                 }
                 try{
                     int id = Integer.parseInt(elements[0]);
                     int wantedCarId = Integer.parseInt(elements[1]);
                     int nrDays = Integer.parseInt(elements[4]);
                     ReservationStatus status = ReservationStatus.valueOf(elements[5]);
                     Car wantedCar = carRepository.findByID(wantedCarId);
                     Reservation reservation = new Reservation(id, wantedCar, elements[2], elements[3], nrDays);
                     reservation.setStatus(status);
                     super.add(reservation);
                 }catch (NumberFormatException ex){
                     System.err.println("Invalid data." + ex);
                 }catch(RepositoryException ex){
                     System.err.println("Repository error." + ex);
                 }
             }

             }catch (IOException ex){
             throw new RepositoryException("Error reading. " + ex);
         }
     }

    @Override
    public Reservation add(Reservation object) {
         object.setID(getNextId());
         //System.out.println(object.toString());
         super.add(object);
         writeToFile();
         return object;
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        writeToFile();
    }

    @Override
    public void update(Integer integer, Reservation object) {
        super.update(integer, object);
        writeToFile();
    }


    private void writeToFile(){
         try(PrintWriter pw = new PrintWriter(filename)){
             pw.println(idGenerator);
             for(Reservation reservation:findAll()){
                 pw.println(reservation.getID() + ";" + reservation.getWanted_car().getID() + ";" + reservation.getClient_name() + ";" + reservation.getStart_date() + ";" + reservation.getNr_days() +  ";" + reservation.getStatus());
             }
         }catch(IOException ex){
             throw new RepositoryException("Error writing." + ex);
         }
    }

    private static int getNextId(){ return idGenerator++;}
}
