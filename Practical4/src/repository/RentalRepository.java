package repository;

import domain.Client;
import domain.Rental;

import java.sql.*;
import java.util.ArrayList;

public class RentalRepository {
    private String url;
    private Connection connection;

    public RentalRepository(String url) {
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

    public ArrayList<Rental> getAllRentals() {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rentals");
            ResultSet rs = ps.executeQuery();
            ArrayList<Rental> couriers = new java.util.ArrayList<>();
            while (rs.next()) {
                couriers.add(new Rental(
                        rs.getInt("clientID"),
                        rs.getInt("carID"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getInt("totalCost")
                ));
            }
            return couriers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addRental(int clientID, int carID, String StartDate, String EndDate, int totalCost) {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("insert into Rentals(clientID, carId, startDate, endDate, totalCost) " +
                                                                    "values (?, ?, ?, ?, ?)");
            ps.setInt(1, clientID);
            ps.setInt(2, carID);
            ps.setString(3, StartDate);
            ps.setString(4, EndDate);
            ps.setInt(5, totalCost);
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("update cars set status='Rented' where carID=?");
            ps2.setInt(1, carID);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void returnRental(int clientID, int carID) {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("delete from Rentals where clientID=? and carID=?");
            ps.setInt(1, clientID);
            ps.setInt(2, carID);
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("update cars set status='available' where carID=?");
            ps2.setInt(1, carID);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }


}
