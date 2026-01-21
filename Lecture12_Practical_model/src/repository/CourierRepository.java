package repository;

import domain.Courier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourierRepository {
    private String url;
    private Connection connection;

    public CourierRepository(String url) {
        this.url = url;
        try {
            connection = java.sql.DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Courier> getAllCouriers() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Courier");
            ResultSet rs = ps.executeQuery();
            List<Courier> couriers = new java.util.ArrayList<>();
            while (rs.next()) {
                couriers.add(new Courier(
                        rs.getString("CourierName"),
                        rs.getString("AssignedStreets"),
                        rs.getInt("CircleRadius"),
                        rs.getInt("CircleCenterX"),
                        rs.getInt("CircleCenterY")
                        ));
            }
            return couriers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAndClose() {
        try {
            if (!connection.isClosed()) {
                connection.commit();
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addCourier(String name, String streets, int radius, int centerX, int centerY) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Courier (CourierName, AssignedStreets, CircleRadius, CircleCenterX, CircleCenterY) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, streets);
            ps.setInt(3, radius);
            ps.setInt(4, centerX);
            ps.setInt(5, centerY);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCourierZone(Courier courier) {
        return courier.getCenterX() + "," + courier.getCenterY() + "," + courier.getRadius();
    }
}
