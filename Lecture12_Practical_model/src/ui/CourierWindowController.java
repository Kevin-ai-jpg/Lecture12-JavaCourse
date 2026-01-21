package ui;

import domain.Courier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Observer;
import service.Service;

import domain.Package;

import java.util.ArrayList;
import java.util.List;

public class CourierWindowController implements Observer {


    private Service service;

    @FXML
    private Button deliverButton;
    @FXML
    private Button optimiseButton;
    @FXML
    private ListView<Package> packagesListView;
    @FXML
    private ListView<Package> optimisedPackagesListView;
    @FXML
    private TextField zoneField;

    private ObservableList<Package> packageObservableList;

    private Courier courier;

    @FXML
    private ComboBox<String> streetsComboBox;

    @FXML
    private ListView<Package> streetPackagesListView;

    public CourierWindowController(Courier courier, Service service) {
        this.courier = courier;
        this.service = service;
        this.service.addObserver(this);
    }

    @FXML
    public ArrayList<String> showStreets() {
        try {
            ArrayList<Package> allPackages = new ArrayList<>(service.getAllPackages());
            ArrayList<String> streets = new ArrayList<>();
            for (Package p : allPackages) {
                if (!streets.contains(p.getAddress())) {
                    streets.add(p.getAddress());
                }
            }
            ObservableList<String> streetsObservableList = FXCollections.observableArrayList(streets);
            streetsComboBox.setItems(streetsObservableList);
            return streets;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }


    @FXML
    public void initialize() {
        try {
            //load packages in the packagesListView
            ArrayList<Package> allPackages = new ArrayList<>(service.getAllPackages());
            packageObservableList = FXCollections.observableArrayList(allPackages);
            packagesListView.setItems(packageObservableList);

            //load courier zone in the zoneField
            zoneField.setText(service.getCourierZone(courier));

            //event to button
            optimiseButton.setOnAction(e -> onOptimiseButtonClick());
            deliverButton.setOnAction(e -> onDeliverButtonClick());

            update();


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onStreetSelected() {
        try {
            String selectedStreet = streetsComboBox.getSelectionModel().getSelectedItem();
            if (selectedStreet != null) {
                List<Package> streetPackages = service.ShowPackageForStreet(selectedStreet);
                ObservableList<Package> streetPackageObservableList = FXCollections.observableArrayList(streetPackages);
                streetPackagesListView.setItems(streetPackageObservableList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onDeliverButtonClick() {
        try {
            Package selectedPackage = packagesListView.getSelectionModel().getSelectedItem();
            if (selectedPackage != null) {
                service.deletePackageIfDelivered(selectedPackage);
                update();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onOptimiseButtonClick() {
        try {
            List<Package> optimisedPackages = service.optimiseDelivery(courier);
            ObservableList<Package> optimisedPackageObservableList = FXCollections.observableArrayList(optimisedPackages);
            optimisedPackagesListView.setItems(optimisedPackageObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            // Refresh packages
            ArrayList<Package> allPackages = new ArrayList<>(service.getAllPackages());
            packageObservableList.setAll(allPackages);
            packagesListView.setItems(packageObservableList);
            //refresh streets
            showStreets();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
