package service;

import domain.Car;
import domain.Client;
import domain.Rental;
import repository.CarRepository;
import repository.ClientRepository;
import repository.RentalRepository;

import java.util.ArrayList;
import java.util.List;

public class Service implements Subject{
    private CarRepository carRepository;
    private ClientRepository clientRepository;
    private RentalRepository rentalRepository;
    private List<Observer> observers = new ArrayList<>();


    public Service(CarRepository carRepository, ClientRepository clientRepository, RentalRepository rentalRepository) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.rentalRepository = rentalRepository;
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

    public ArrayList<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public ArrayList<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public ArrayList<Rental> getAllRental() {
        return rentalRepository.getAllRentals();
    }

    public List<Car> getAllAvailableCars() {
        List<Car> allCars = carRepository.getAllCars();
        return allCars.stream()
                .filter(p -> p.getStatus().equals("available"))
                .toList();
    }
    public List<Car> getCarsbyCategory(String category) {
        List<Car> allCars = this.getAllAvailableCars();
        return allCars.stream()
                .filter(p -> p.getCategory().equals(category))
                .toList();
    }

    public void addRental(int clientID, int carId, String startDate, String endDate) {
        Car car = this.carRepository.findByID(carId);

        try {
            if(car.getStatus().equals("available")) {
                //2024-12-12 2024-12-15
                int totalCost = 100;
                rentalRepository.addRental(clientID, carId, startDate, endDate, totalCost);
                notifyObservers();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String ShowRentalInfo(Rental rental) {
        Client client = this.clientRepository.findByID(rental.getClientID());
        Car car = this.carRepository.findByID(rental.getCarID());
        String clientName = client.getName();
        String carModel = car.getModel();
        String endDate = rental.getEndDate();
        return clientName + " " + carModel + " " + endDate;
    }

    public List<String> ShowAllRentalInfo() {
        List<Rental> allRentals = rentalRepository.getAllRentals();
        return allRentals.stream()
                .map(this::ShowRentalInfo)
                .toList();
    }

    public void returnRental(int clientId, int carId) {
        rentalRepository.returnRental(clientId, carId);
    }
}
