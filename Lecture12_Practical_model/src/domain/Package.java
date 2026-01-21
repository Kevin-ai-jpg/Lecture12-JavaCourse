package domain;

public class Package {
    private String recipient;
    private String Address;
    private int LocationX;
    private int LocationY;
    private boolean Status;
    public Package(String recipient, String address, int locationX, int locationY, boolean status) {
        this.recipient = recipient;
        Address = address;
        LocationX = locationX;
        LocationY = locationY;
        Status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getLocationX() {
        return LocationX;
    }

    public void setLocationX(int locationX) {
        LocationX = locationX;
    }

    public int getLocationY() {
        return LocationY;
    }

    public void setLocationY(int locationY) {
        LocationY = locationY;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Package{" +
                "recipient='" + recipient + '\'' +
                ", Address='" + Address + '\'' +
                ", LocationX=" + LocationX +
                ", LocationY=" + LocationY +
                ", Status=" + Status +
                '}';
    }
}
