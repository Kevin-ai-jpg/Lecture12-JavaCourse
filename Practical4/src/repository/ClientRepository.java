package repository;

import domain.Car;
import domain.Client;

import java.sql.*;
import java.util.ArrayList;

public class ClientRepository {
    private String url;
    private Connection connection;

    public ClientRepository(String url) {
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

    public ArrayList<Client> getAllClients() {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clients");
            ResultSet rs = ps.executeQuery();
            ArrayList<Client> couriers = new java.util.ArrayList<>();
            while (rs.next()) {
                couriers.add(new Client(
                        rs.getInt("clientID"),
                        rs.getString("clientName"),
                        rs.getString("phoneNumber")
                ));
            }
            return couriers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public Client findByID(int id) {
        openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM clients where clientID=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Client car = new Client(
                    rs.getInt("clientID"),
                    rs.getString("clientName"),
                    rs.getString("phoneNumber")
            );
            return car;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
