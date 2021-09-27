package ctrl;

import com.sun.javafx.geom.AreaOp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Car;
import model.Reservation;
import model.ReservationStatus;
import repository.CarRepository;
import repository.RepositoryException;
import service.RentalService;
import service.ServiceException;

import java.time.LocalDate;
import java.util.List;


public class Controller {

    private RentalService rentalService;

    //Cars
    @FXML
    private TextField manufacturer, model, engine, price, color;

    @FXML
    private Text car_id;

    //View all cars
    @FXML
    private TableView<Car> viewCars;

    @FXML
    private TextField selectedPrice;

    @FXML ChoiceBox<String> manufacturerChoice;

    //Reservations
    @FXML
    private TextField wanted_car_id, client_name,nr_days, status;

    @FXML
    private DatePicker start_date;

    @FXML
    private Text reservation_id;

    //View all reservations
    @FXML
    private TableView<Reservation> viewReservations;

    @FXML
    private ChoiceBox<ReservationStatus> statusChoice;

    @FXML
    private DatePicker datePicker;

    public Controller(){}

    public void setRentalService(RentalService rentalService){this.rentalService = rentalService;}

    //Adding a car
    @FXML
    public void addCarToList(ActionEvent actionEvent){
        String carManufacturer = manufacturer.getText();
        String carModel = model.getText();
        String carEngine = engine.getText();
        String carPrice = price.getText();
        String carColor = color.getText();
        if(checkString(carManufacturer) && checkString(carModel) && checkString(carEngine) && checkString(carPrice) && checkString(carColor)) {
            try {
                int id = rentalService.addCar(carManufacturer, carModel,Double.parseDouble(carEngine), Integer.parseInt(carPrice), carColor);
                car_id.setText("Car id:" + id);
                //showNotification("Car successfully added!", Alert.AlertType.INFORMATION);
                List<Car> carList = rentalService.listAllCars();
                viewCars.getItems().clear();
                viewCars.getItems().addAll(carList);
                clearCarFields();
            } catch (ServiceException ex) {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
        else
            showNotification("You have to fill in all the fields!", Alert.AlertType.ERROR);
    }

    //Delete a car
    @FXML
    public void deleteCar(ActionEvent actionEvent) {
        viewCars.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Car selectedCar = viewCars.getSelectionModel().getSelectedItem();
        if(selectedCar.getID() < 0){
            showNotification("You must select a car for deletion.", Alert.AlertType.ERROR);
            return;
        }else{
            try {
                 rentalService.deleteCar(selectedCar.getID());
                List<Car> carList = rentalService.listAllCars();
                viewCars.getItems().clear();
                viewCars.getItems().addAll(carList);
            }catch (ServiceException ex){
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    //Update a car
    @FXML
    public void updateCar(ActionEvent actionEvent) {
        viewCars.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Car selectedCar = viewCars.getSelectionModel().getSelectedItem();
        int selectedCarID = selectedCar.getID();
        String carManufacturer = manufacturer.getText();
        String carModel = model.getText();
        String carEngine = engine.getText();
        String carPrice = price.getText();
        String carColor = color.getText();
        Car newCar = new Car(selectedCarID, carManufacturer, carModel, Double.parseDouble(carEngine), Integer.parseInt(carPrice), carColor);
        if(selectedCar == null){
            showNotification("Something went wrong!", Alert.AlertType.ERROR);
            return;
        }
        else {
            try {
                rentalService.updateCar(selectedCarID, newCar);
                showNotification("Car updated succeffully!", Alert.AlertType.INFORMATION);
                List<Car> carList = rentalService.listAllCars();
                viewCars.getItems().clear();
                viewCars.getItems().addAll(carList);
            } catch (ServiceException ex) {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    public void clearCarFields(){
        manufacturer.setText("");
        model.setText("");
        engine.setText("");
        price.setText("");
        color.setText("");
    }

    private boolean checkString(String s){
        return s==null || s.isEmpty()? false:true;
    }

    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Listing all cars
    public void resetCarList(ActionEvent actionEvent) {
        List<Car> carList = rentalService.listAllCars();
        viewCars.getItems().clear();
        viewCars.getItems().addAll(carList);
    }

    public void filterCarsByPrice(ActionEvent actionEvent) {
        int price = Integer.parseInt(selectedPrice.getText());
        try{
            List<Car> carList = rentalService.filterCarsByPrice(price);
            viewCars.getItems().clear();
            viewCars.getItems().addAll(carList);
        }catch(ServiceException ex){
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void filterCarsByManufacturer(ActionEvent actionEvent) {
        String choice = manufacturerChoice.getSelectionModel().getSelectedItem();
        try{
            List<Car> carList = rentalService.filterCarsByManufacturer(choice);
            viewCars.getItems().clear();
            viewCars.getItems().addAll(carList);
        }catch (ServiceException ex){
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }

    }

    //RESERVATIONS

    public void addReservationToList(ActionEvent actionEvent) {
        String resWantedCar = wanted_car_id.getText();
        String resClient = client_name.getText();
        String resStartDate = start_date.getValue().toString();
        String resDays = nr_days.getText();
        //String resStatus = status.getText();
        Car wantedCar = rentalService.getCarRepository().findByID(Integer.parseInt(resWantedCar));
        if(checkString(resWantedCar) && checkString(resClient) && checkString(resStartDate) && checkString(resDays) ) {
            try {
                int id = rentalService.addReservation(wantedCar, resClient, resStartDate, Integer.parseInt(resDays));
                reservation_id.setText("Reservation id:" + id);
                //showNotification("Car successfully added!" + id, Alert.AlertType.INFORMATION);
                List<Reservation> reservationList = rentalService.listAllReservations();
                viewReservations.getItems().clear();
                viewReservations.getItems().addAll(reservationList);
                clearReservationsFields();
            } catch (ServiceException ex) {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
        else
            showNotification("You have to fill in all the fields!", Alert.AlertType.ERROR);
    }

    public void clearReservationsFields(){
        wanted_car_id.setText("");
        client_name.setText("");
        nr_days.setText("");
    }

    public void deleteReservation(ActionEvent actionEvent) {
        viewReservations.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Reservation selectedReservation = viewReservations.getSelectionModel().getSelectedItem();
        if(selectedReservation.getID() < 0){
            showNotification("You must select a reservation for deletion.", Alert.AlertType.ERROR);
            return;
        }else{
            try {
                rentalService.deleteReservation(selectedReservation.getID());
                List<Reservation> reservationList = rentalService.listAllReservations();
                viewReservations.getItems().clear();
                viewReservations.getItems().addAll(reservationList);
            }catch (ServiceException ex){
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    public void updateReservation(ActionEvent actionEvent) {
        viewReservations.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Reservation selectedReservation = viewReservations.getSelectionModel().getSelectedItem();
        int reservationId = selectedReservation.getID();

        String resWantedCar = wanted_car_id.getText();
        String resClient = client_name.getText();
        String resStartDate = start_date.getValue().toString();
        String resDays = nr_days.getText();
        Car resCar = rentalService.getCarRepository().findByID(Integer.parseInt(resWantedCar));
        Reservation newReservation = new Reservation(reservationId, resCar, resClient, resStartDate, Integer.parseInt(resDays));
        if(reservationId < 0){
            showNotification("Something went wrong!", Alert.AlertType.ERROR);
            return;
        }
        else {
            try {
                rentalService.updateReservation(reservationId, newReservation);
                //showNotification("Reservation updated succeffully!", Alert.AlertType.INFORMATION);
                List<Reservation> reservationList = rentalService.listAllReservations();
                viewReservations.getItems().clear();
                viewReservations.getItems().addAll(reservationList);
                clearReservationsFields();
            } catch (ServiceException ex) {
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    //Listing all reservations
    public void resetReservationsList(ActionEvent actionEvent) {
        List<Reservation> reservationList = rentalService.listAllReservations();
        viewReservations.getItems().clear();
        viewReservations.getItems().addAll(reservationList);

    }


    public void completeReservation(ActionEvent actionEvent) {
        viewReservations.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Reservation selectedReservation = viewReservations.getSelectionModel().getSelectedItem();
        int reservationId = selectedReservation.getID();

        selectedReservation.setStatus(ReservationStatus.Completed);
        try{
            rentalService.updateReservation(reservationId, selectedReservation);
            List<Reservation> reservationList = rentalService.listAllReservations();
            viewReservations.getItems().clear();
            viewReservations.getItems().addAll(reservationList);
        }catch (ServiceException ex){
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cancelReservation(ActionEvent actionEvent) {
        viewReservations.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Reservation selectedReservation = viewReservations.getSelectionModel().getSelectedItem();
        int reservationId = selectedReservation.getID();

        selectedReservation.setStatus(ReservationStatus.Cancelled);
        try{
            rentalService.updateReservation(reservationId, selectedReservation);
            List<Reservation> reservationList = rentalService.listAllReservations();
            viewReservations.getItems().clear();
            viewReservations.getItems().addAll(reservationList);
        }catch (ServiceException ex){
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void filterByStatus(ActionEvent actionEvent) {
        try {
            ReservationStatus choice = statusChoice.getSelectionModel().getSelectedItem();
            List<Reservation> reservationList = rentalService.filterReservationByStatus(choice);
            viewReservations.getItems().clear();
            viewReservations.getItems().addAll(reservationList);
        }catch (ServiceException ex){
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void filterByDate(ActionEvent actionEvent) {
        try{
            String pickedDate = datePicker.getValue().toString();
            List<Reservation> reservationList = rentalService.filterReservationByDate(pickedDate);
            viewReservations.getItems().clear();
            viewReservations.getItems().addAll(reservationList);
        }catch(ServiceException ex){
            showNotification(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }



}
