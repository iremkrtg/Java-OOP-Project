package repository;

import com.sun.javafx.iio.ios.IosDescriptor;
import model.Car;
import model.CarStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CarFileRepository extends CarInMemoryRepository {

    private String filename;
    private static int idGenerator = 0;

    public CarFileRepository(String filename){
        this.filename = filename;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();
            try{
               idGenerator = Integer.parseInt(line);
            }catch(NumberFormatException ex){
                System.err.println("Invalid value for id. Starting from 0.");
            }
            while((line = br.readLine()) != null){
                String[] elements = line.split(";");
                if(elements.length != 6){
                    System.err.println("Invalid line " + line);
                    continue;
                }
                try{
                    int id = Integer.parseInt(elements[0]);
                    double engine = Double.parseDouble(elements[3]);
                    int price = Integer.parseInt(elements[4]);
                    Car car = new Car(id, elements[1], elements[2],engine, price, elements[5]);
                    super.add(car);
                }catch (NumberFormatException ex){
                    System.err.println("Error converting." + ex);
                }
            }
        }catch (IOException ex){
            throw new RepositoryException("Error reading." + ex);
        }

    }

    @Override
    public Car add(Car object) {
        object.setID(getNextId());
        //System.out.println("aici" + object);
        super.add(object);
        writeToFile();
        return object;
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        System.out.println("aici" + integer);
        writeToFile();
    }

    @Override
    public void update(Integer integer, Car object) {
        super.update(integer, object);
        writeToFile();
    }

    private void writeToFile(){
        try(PrintWriter pw = new PrintWriter(filename)){
            pw.println(idGenerator);
            for(Car cars:findAll()){
                pw.println(cars.getID()+ ";" + cars.getManufacturer() + ";" + cars.getModel() + ";" + cars.getEngine() + ";" + cars.getPrice() + ";" + cars.getColor() );
            }
        }catch (IOException ex){
            throw new RepositoryException("Error writing. " + ex);
        }
    }

    private static int getNextId(){return idGenerator++; }


}
