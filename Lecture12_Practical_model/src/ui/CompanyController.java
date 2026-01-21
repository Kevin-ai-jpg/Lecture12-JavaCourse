package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Observer;
import service.Service;

import domain.Package;

import java.util.ArrayList;

public class CompanyController implements Observer {


    @FXML
    private ListView<Package> companyPackagesListView;
    @FXML
    private TextField recipientName;
    @FXML
    private TextField address;
    @FXML
    private TextField locX;
    @FXML
    private TextField locY;
    @FXML
    private Button addPackageButton;

    public Service service;

    private ObservableList<Package> packageObservableList;

    public CompanyController(Service service) {
        this.service = service;
        this.service.addObserver(this);
    }

    @FXML
    public void initialize() {
        try {
            ArrayList<domain.Package> allPackages = new ArrayList<>(service.getAllPackages());
            packageObservableList = FXCollections.observableArrayList(allPackages);
            companyPackagesListView.setItems(packageObservableList);

            //button event
            addPackageButton.setOnAction(event -> addPackage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    @FXML
//    public void addPackage() {
//        try {
//            String name = recipientName.getText();
//            String addr = address.getText();
//            int x = Integer.parseInt(locX.getText());
//            int y = Integer.parseInt(locY.getText());
//
//            // Save to repository - this will notify all observers
//            service.addPackage(name, addr, x, y);
//            //save the new package in the db
//
//            // Clear input fields after adding
//            recipientName.clear();
//            address.clear();
//            locX.clear();
//            locY.clear();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Override
    public void update() {
        System.out.println("CompanyController.update() called"); // Debug line
        try {
            ArrayList<domain.Package> allPackages = new ArrayList<>(service.getAllPackages());
            System.out.println("Packages count: " + allPackages.size()); // Debug line
            packageObservableList.setAll(allPackages);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addPackage() {
        try {
            String name = recipientName.getText();
            String addr = address.getText();
            int x = Integer.parseInt(locX.getText());
            int y = Integer.parseInt(locY.getText());

            System.out.println("Adding package: " + name); // Debug line
            service.addPackage(name, addr, x, y);

            recipientName.clear();
            address.clear();
            locX.clear();
            locY.clear();
        } catch (Exception e) {
            e.printStackTrace(); // Print full stack trace
        }
    }


}
