package repository;

import domain.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private String url;
    private Connection connection;

    public CarRepository(String url) {
        this.url = url;
    }
    private void openConnection() {
        try {
            if (connection == null || connection.isClosed())
                connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Car> getAllCars() {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM cars");
            ResultSet rs = ps.executeQuery();
            ArrayList<Car> couriers = new java.util.ArrayList<>();
            while (rs.next()) {
                couriers.add(new Car(
                        rs.getInt("carID"),
                        rs.getString("model"),
                        rs.getString("category"),
                        rs.getInt("pricePerDay"),
                        rs.getString("status")
                ));
            }
            return couriers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public Car findByID(int id) {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM cars where carID=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Car car = new Car(
                    rs.getInt("carID"),
                    rs.getString("model"),
                    rs.getString("category"),
                    rs.getInt("pricePerDay"),
                    rs.getString("status")
            );
            return car;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
