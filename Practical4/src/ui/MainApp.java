package ui;

import domain.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import repository.CarRepository;
import repository.ClientRepository;
import repository.RentalRepository;
import service.Service;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    private Service hotelService;

    @Override
    public void start(Stage primaryStage) {
        // Initialize database connection and service
        String databaseURL = "jdbc:sqlite:identifier.sqlite";
        ClientRepository clientRepository = new ClientRepository(databaseURL);
        CarRepository bookingsRepository = new CarRepository(databaseURL);
        RentalRepository rentalRepository = new RentalRepository(databaseURL);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        hotelService = new Service(bookingsRepository, clientRepository, rentalRepository);
        List<Client> allClients = hotelService.getAllClients();
        int index = 0;
        for(Client client : allClients) {
            index = index + 5;
            openClientWindow(client, hotelService, 20 * index);
        }
        openStaffWindow();
    }

    private void openClientWindow(Client user, Service service, double xOffset) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("ClientWindow.fxml"));

            loader.setControllerFactory(param -> new ClientWindowController(user, service));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();

            stage.setTitle("User " + user.getName() + ": " + user.getName());
            stage.setScene(scene);
            stage.setY(0);
            stage.setX(xOffset);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void openStaffWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager.fxml"));

            loader.setControllerFactory(param -> new ManagerController(hotelService));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Staff Window - Hotel Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading staff window: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
