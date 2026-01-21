package domain;

public class Courier {
    private String name;
    private String streets;
    private int radius;
    private int CenterX;
    private int CenterY;

    public Courier(String name, String streets, int radius, int centerX, int centerY) {
        this.name = name;
        this.streets = streets;
        this.radius = radius;
        CenterX = centerX;
        CenterY = centerY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreets() {
        return streets;
    }

    public void setStreets(String streets) {
        this.streets = streets;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCenterX() {
        return CenterX;
    }

    public void setCenterX(int centerX) {
        CenterX = centerX;
    }

    public int getCenterY() {
        return CenterY;
    }

    public void setCenterY(int centerY) {
        CenterY = centerY;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", streets='" + streets + '\'' +
                ", radius=" + radius +
                ", CenterX=" + CenterX +
                ", CenterY=" + CenterY +
                '}';
    }
}
