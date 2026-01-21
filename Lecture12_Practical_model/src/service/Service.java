package service;

import domain.Courier;
import domain.Package;
import repository.CourierRepository;
import repository.PackageRepository;

import java.util.ArrayList;
import java.util.List;

public class Service implements Subject {
    private CourierRepository courierRepository;
    private PackageRepository packageRepository;
    private List<Observer> observers = new ArrayList<>();

    public Service(CourierRepository courierRepository, PackageRepository packageRepository) {
        this.courierRepository = courierRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.getAllCouriers();
    }

    public ArrayList<Package> getAllPackages() {
        return packageRepository.getAllPackages();
    }

    public List<Package> getUndeliveredPackagesForCourier(Courier courier) {
        List<Package> allPackages = packageRepository.getAllPackages();

        // 1. Split the single String into an array of streets
        // CHANGE THIS delimiter (";") to whatever you used in your database (could be "," or "|")
        String[] assignedStreets = courier.getStreets().split(",");

        return allPackages.stream()
                .filter(p -> !p.isStatus()) // Check if not delivered

                // 2. Check if the package address contains ANY of the split streets
                .filter(p -> java.util.Arrays.stream(assignedStreets)
                        .map(String::trim) // Remove extra spaces (e.g. " Broadway" -> "Broadway")
                        .anyMatch(street -> !street.isEmpty() && p.getAddress().contains(street)))

                // 3. Check Radius
                .filter(p -> {
                    int dx = p.getLocationX() - courier.getCenterX();
                    int dy = p.getLocationY() - courier.getCenterY();
                    return dx * dx + dy * dy <= courier.getRadius() * courier.getRadius();
                })
                .toList();
    }

    //method to optimise the delivery for undelivered packages
    public List<Package> optimiseDelivery(Courier courier) {
        List<Package> undeliveredPackages = getUndeliveredPackagesForCourier(courier);
        return undeliveredPackages.stream()
                .sorted((p1, p2) -> {
                    int dx1 = p1.getLocationX() - courier.getCenterX();
                    int dy1 = p1.getLocationY() - courier.getCenterY();
                    int dx2 = p2.getLocationX() - courier.getCenterX();
                    int dy2 = p2.getLocationY() - courier.getCenterY();
                    double dist1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
                    double dist2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);
                    return Double.compare(dist1, dist2);
                })
                .toList();
    }

    public void addPackage(String recipient, String address, int locationX, int locationY) {
        //notify observers
        packageRepository.addPackage(recipient, address, locationX, locationY);
        notifyObservers();
    }

    public List<Package> ShowPackageForStreet(String street) {
        List<Package> allPackages = packageRepository.getAllPackages();
        return allPackages.stream()
                .filter(p -> p.getAddress().contains(street))
                .toList();
    }

    public void deletePackageIfDelivered(Package pkg) {
        packageRepository.deletePackage(pkg.getRecipient(), pkg.getAddress());
        notifyObservers();
    }

    public String getCourierZone(Courier courier) {
        return courierRepository.getCourierZone(courier);
    }

    public void saveAndClose() {
        packageRepository.saveAndClose();
    }


}
