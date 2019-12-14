//Name: Kritarth Shah
//Student ID: 500907217

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class CarDealership {

    Calendar tempdate = null;

    //Declaring instance variables
    private boolean Electricfilter = false, AWDfilter = false, Pricefilter = false; //Three Variables that hold boolean type and contain true/false on whether that filter has been activated
    private double maxPrice = 0, minPrice = 0; // Two Variables that hold type double containing the minimum price and the maximum price for the Price Filter function
    private ArrayList<Car> cars; // Variable holds ArrayList type Car that contains the list of cars in the inventory
    static SalesTeam salesTeam = new SalesTeam();
    static AccountingSystem accountingSystem = new AccountingSystem();

    //Constructor method that creates a new empty ArrayList;
    public CarDealership() {
        cars = new ArrayList<>();
    }

    //Method that takes in an ArrayList type car created in the simulator and adds all the cars in that list into
    //the list in this class, which contains ALL the cars in the inventory
    public void addCars(ArrayList<Car> newCars) {
        cars.addAll(newCars);
    }

    //Method takes in a VIN number and if that VIN is in the list, it will remove the car from array.
    //The method will return the display function when the new transaction is added.
    //Method also calls a random sales person, and a random date of buyDate(), also hold the latest date in a tempdate variable
    //Throws NullPointerException when the VIN number is invalid
    public String buyCar(int VIN) {
        Car temp = null;
        String result = "";
        for (Car car: cars) {
            if (car.getVIN()==VIN) {
                temp = car;
                break;
            }
        }
        if (temp == null){
            throw new NullPointerException();
        }
        else {
            cars.remove(temp);
            String salesPerson = salesTeam.getSalesPerson();
            Calendar datebought = accountingSystem.buyDate();
            tempdate = datebought;
            result = accountingSystem.add(datebought, temp, salesPerson, "BUY", temp.getPrice());
        }
        return result;
    }

    //Method that takes in a transaction id, and calls the getTransaction method from AccountingSystem class which returns the Transaction object associated with the id
    //Also called returndate using the tempdate, adds the car back into list and prints the new transaction
    public void returnCar(int transaction) {
        Transaction newrettrans = accountingSystem.getTransaction(transaction);
        Calendar retundate = accountingSystem.returnDate(tempdate);
        cars.add(newrettrans.getCar());
        System.out.println(accountingSystem.add(retundate, newrettrans.getCar(), salesTeam.getSalesPerson(), "RETURN", newrettrans.getCar().getPrice()));

    }

    //Method used to display the list of cars according to the filters set in th e simulator class
    //Runs with an enhanced loop, and if conditions are met to the select boolean filter selection, the will be printed out.
    public void displayInventory() {
        if (cars.size()==0)
            System.out.println("No Cars Have Been Added To Inventory");
        else {
            for (Car car : cars) {
                boolean valid = false;
                if (!Electricfilter && !AWDfilter && !Pricefilter) {
                    valid = true;
                } else if (!Electricfilter && !AWDfilter && Pricefilter) {
                    if (car.getPrice() >= minPrice && car.getPrice() <= maxPrice)
                        valid = true;

                } else if (!Electricfilter && AWDfilter && !Pricefilter) {
                    if (car.isAWD())
                        valid = true;
                } else if (!Electricfilter && AWDfilter && Pricefilter) {
                    if (car.getPrice() >= minPrice && car.getPrice() <= maxPrice && car.isAWD())
                        valid = true;
                } else if (Electricfilter && !AWDfilter && !Pricefilter) {
                    if (car.getPower() == Vehicle.PowerSource.ELECTRIC_MOTOR)
                        valid = true;
                } else if (Electricfilter && !AWDfilter && Pricefilter) {
                    if (car.getPrice() >= minPrice && car.getPrice() <= maxPrice && car.getPower() == Vehicle.PowerSource.ELECTRIC_MOTOR)
                        valid = true;
                } else if (Electricfilter && AWDfilter && !Pricefilter) {
                    if (car.getPower() == Vehicle.PowerSource.ELECTRIC_MOTOR && car.isAWD())
                        valid = true;
                } else {
                    if (car.getPower() == Vehicle.PowerSource.ELECTRIC_MOTOR && car.isAWD() && car.getPrice() >= minPrice && car.getPrice() <= maxPrice)
                        valid = true;
                }
                if (valid)
                    System.out.println(car.display());
            }
        }
    }

    //Method changes the boolean of the Electric filter to true if the Filter by Electric Cars is called in the simulation
    public void filterByElectric() {
        Electricfilter = true;
    }

    //Method changes the boolean of the AWD filter to true if the Filter by AWD Cars is called in the simulation
    public void filterByAWD() {
        AWDfilter = true;
    }

    //Method changes the boolean of the Price Filter to true is the Filter by Price is called in tbe simulation
    //The method also takes in two double parameters that are used to set the range of the prices of the vehicles to show
    //Sets the minPrice and maxPrice instance variables to the values given from the parameter
    public void filterByPrice(double minPrice, double maxPrice) {
        Pricefilter = true;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    //Method used to clear all the filters but making all the filter booleans to false and clearing the min and max price double variables to 0
    public void FilterClear() {
        Electricfilter = false;
        AWDfilter = false;
        Pricefilter = false;
        maxPrice = 0;
        minPrice = 0;
    }

    //Method uses the Collection.sort function to sort the car in price in incrementing order by default
    public void sortByPrice() {
        Collections.sort(cars);
    }

    //Method uses the Collection.sort function along with the paired comparator class created for it to sort the car in SafetyRatings in decrementing order
    public void sortBySafetyRating() {
        Collections.sort(cars, new sortBySafetyRating());
    }

    //Method uses the Collection.sort function along with the paired comparator class created for it to sort the car in Maximum Range in decrementing order
    public void sortByMaxRange() {
        Collections.sort(cars, new sortByMaxRange());
    }

    //_______________________________________________________________________________________________________

    //Method used for "SALES" command
    public void displayTransactions(){
        accountingSystem.allTransactions();
    }

    //Method used for "SALES STATS" command
    public void displayStats() {
        accountingSystem.salesStats();
    }

    //Method that get the Transaction id given the VIN number and calld the method that does that from the AccountingSystem
    public int IDoflastBought (int VIN) {
        return accountingSystem.getTransactionIDwithVIN(VIN);
    }

    //Method that takes in a month number and calls method that is used for the "SALES m" command
    public void salesByMonth(int month){
        accountingSystem.monthlyTransactions(month);
    }
}

//Class that implements Comparator with Car objects and compares two cars based on the safety rating
//Returns 1 if Car2's ratings are higher
//Returns -1 if Car1's ratings are higher
//Returns 0 if both ratings are the same
class sortBySafetyRating implements Comparator <Car> {
    public int compare(Car car1, Car car2) {
        return Double.compare(car2.getSafetyRating(),car1.getSafetyRating());
    }
}

//Class that implements Comparator with Car objects and compares two cars based on the max range
class sortByMaxRange implements Comparator <Car> {
    public int compare(Car car1, Car car2) {
        return car2.getMaxRange() - car1.getMaxRange();
    }
}
