package repository;

import domain.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageRepository {
    private String url;
    private Connection connection;

    public PackageRepository(String url) {
        this.url = url;
        try {
            connection = java.sql.DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAndClose() {
        try {
            if (connection != null && !connection.isClosed()) {
                // REMOVE THIS LINE: connection.commit();

                // JUST DO THIS:
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPackage(String recipient, String address, int locationX, int locationY) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Package (Recipient, Address, LocationX, LocationY, Status) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, recipient);
            ps.setString(2, address);
            ps.setInt(3, locationX);
            ps.setInt(4, locationY);
            ps.setBoolean(5, false);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //i dont want to delete the package, just update that its been delivered
    public void deletePackage(String recipient, String address) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Package SET Status = ? WHERE Recipient = ? AND Address = ?");
            ps.setBoolean(1, true);
            ps.setString(2, recipient);
            ps.setString(3, address);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Package> getAllPackages() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Package");
            ResultSet rs = ps.executeQuery();
            ArrayList<Package> packages = new java.util.ArrayList<>();
            while (rs.next()) {
                packages.add(new Package(
                        rs.getString("Recipient"),
                        rs.getString("Address"),
                        rs.getInt("LocationX"),
                        rs.getInt("LocationY"),
                        rs.getBoolean("Status")
                        ));
            }
            return packages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
