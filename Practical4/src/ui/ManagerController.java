package ui;

import domain.Rental;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import service.Observer;
import service.Service;

import java.util.ArrayList;

public class ManagerController implements Observer {
    private Service service;

    public ManagerController(Service service) {
        this.service = service;
        this.service.addObserver(this);
    }

    @FXML
    private ListView<String> rentalListView;
    @FXML
    private Button returnCar;
    @FXML
    private ListView<Rental> rentalListView2;

    private ObservableList<String> rentalObservableList;
    private ObservableList<Rental> rentalObservableList2;


    @FXML
    public void initialize() {
        try {
            ArrayList<String> allrentals = new ArrayList<>(service.ShowAllRentalInfo());
            rentalObservableList = FXCollections.observableArrayList(allrentals);
            rentalListView.setItems(rentalObservableList);

            ArrayList<Rental> allrentals2 = new ArrayList<>(service.getAllRental());
            rentalObservableList2 = FXCollections.observableArrayList(allrentals2);
            rentalListView2.setItems(rentalObservableList2);

            returnCar.setOnAction(e -> onReturnButton());

            update();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onReturnButton() {
        Rental rentedCar = rentalListView2.getSelectionModel().getSelectedItem();
        service.returnRental(rentedCar.getClientID(), rentedCar.getCarID());
    }

    @Override
    public void update() {
        try {
            ArrayList<String> allrentals = new ArrayList<>(service.ShowAllRentalInfo());
            rentalObservableList = FXCollections.observableArrayList(allrentals);
            rentalListView.setItems(rentalObservableList);

            ArrayList<Rental> allrentals2 = new ArrayList<>(service.getAllRental());
            rentalObservableList2 = FXCollections.observableArrayList(allrentals2);
            rentalListView2.setItems(rentalObservableList2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
