import ctrl.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Car;
import repository.CarFileRepository;
import repository.CarRepository;
import repository.ReservationFileRepository;
import repository.ReservationRepository;
import service.RentalService;
import service.ServiceException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("example.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setRentalService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Car Rental Application");
            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("Error while starting app." + e);
            alert.showAndWait();
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    static RentalService getService() throws ServiceException{
        try{
            String carFileName = null;
            Properties properties = new Properties();
            properties.load(new FileInputStream("rental.properties"));
           carFileName = properties.getProperty("CarsFile");
            if(carFileName == null){
                carFileName = "Cars.txt";
                System.err.println("Cars file not found. Using default " + carFileName);
            }


            String reservationsFileName = properties.getProperty("ReservationsFile");
            if(reservationsFileName == null){
                reservationsFileName = "Reservations.txt";
                System.err.println("Reservations file not found. Using default " + reservationsFileName);
            }

            CarRepository carRepository = new CarFileRepository(carFileName);
            ReservationRepository reservationRepository = new ReservationFileRepository(reservationsFileName, carRepository);
            return new RentalService(carRepository, reservationRepository);



        }catch(IOException ex){
            throw new ServiceException("Error starting app." + ex);
        }
    }
}
