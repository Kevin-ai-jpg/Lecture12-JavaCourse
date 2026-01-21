package ui;

import domain.Car;
import domain.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Observer;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class ClientWindowController implements Observer {
    private Service service;
    private Client client;

    public ClientWindowController(Client courier, Service service) {
        this.client = courier;
        this.service = service;
        this.service.addObserver(this);
    }

    @FXML
    private ListView<Car> carListView;
    @FXML
    private ListView<Car> carListViewCategory;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private Button rentButton;
    @FXML
    private ComboBox<String> categories;

    private ObservableList<Car> carObservableList;
    private ObservableList<Car> carCategoryObservableList;

    @FXML
    public void initialize() {
        try {
            ArrayList<Car> allCars = new ArrayList<>(service.getAllAvailableCars());
            carObservableList = FXCollections.observableArrayList(allCars);
            carListView.setItems(carObservableList);

//            rentButton.setOnAction(e->addRent());

            update();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public ArrayList<String> showCategories() {
        try {
            ArrayList<Car> allPackages = new ArrayList<>(service.getAllCars());
            ArrayList<String> streets = new ArrayList<>();
            for (Car p : allPackages) {
                if (!streets.contains(p.getCategory())) {
                    streets.add(p.getCategory());
                }
            }
            ObservableList<String> streetsObservableList = FXCollections.observableArrayList(streets);
            categories.setItems(streetsObservableList);
            return streets;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @FXML
    public void onCategorySelected() {
        try {
            String selectedStreet = categories.getSelectionModel().getSelectedItem();
            if (selectedStreet != null) {
                List<Car> streetPackages = service.getCarsbyCategory(selectedStreet);
                ObservableList<Car> streetPackageObservableList = FXCollections.observableArrayList(streetPackages);
                carListViewCategory.setItems(streetPackageObservableList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addRent() {
        try {
            Car selectedcar = carListView.getSelectionModel().getSelectedItem();
            String stDate = startDate.getText();
            String eDate = endDate.getText();

            service.addRental(client.getClientID(), selectedcar.getCarID(), stDate, eDate);
            startDate.clear();
            endDate.clear();

            update();
        } catch (Exception e) {
            e.printStackTrace(); // Print full stack trace
        }
    }

    @Override
    public void update() {
        try {
            ArrayList<Car> allCars = new ArrayList<>(service.getAllAvailableCars());
            carObservableList = FXCollections.observableArrayList(allCars);
            carListView.setItems(carObservableList);

            showCategories();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
