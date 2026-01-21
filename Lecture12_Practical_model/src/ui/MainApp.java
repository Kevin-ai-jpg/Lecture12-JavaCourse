package ui;

import domain.Courier;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import repository.CourierRepository;
import repository.PackageRepository;
import service.Service;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    private Service hotelService;

    @Override
    public void start(Stage primaryStage) {
        // Initialize database connection and service
        String databaseURL = "jdbc:sqlite:identifier.sqlite";
        CourierRepository clientRepository = new CourierRepository(databaseURL);
        PackageRepository bookingsRepository = new PackageRepository(databaseURL);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        hotelService = new Service(clientRepository, bookingsRepository);
        List<Courier> allClients = hotelService.getAllCouriers();
        int index = 0;
        for(Courier client : allClients) {
            index = index + 5;
            openClientWindow(client, hotelService, 20 * index);
        }
        openStaffWindow();
    }

    private void openClientWindow(Courier user, Service service, double xOffset) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("CourierWindow.fxml"));

            loader.setControllerFactory(param -> new CourierWindowController(user, service));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Company.fxml"));

            loader.setControllerFactory(param -> new CompanyController(hotelService));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Staff Window - Hotel Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading staff window: " + e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        // Clean up resources before closing
        if (hotelService != null) {
            hotelService.saveAndClose();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
