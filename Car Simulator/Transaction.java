//Name: Kritarth Shah
//Student ID: 500907217

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaction {

    SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy"); //Sets the format of the date and the required items for the date
    //Declaring all the instances variables
    private int id;
    private Calendar date;
    private Car car;
    private String salesPerson, type;
    private double price;

    //Constructor method to take parameters in and declare all the instance variables
    public Transaction(int id, Calendar date, Car car, String salesPerson, String type,double price) {
        this.id = id;
        this.date = date;
        this.car = car;
        this.salesPerson = salesPerson;
        this.type = type;
        this.price = price;
    }

    //Getter method that will return the transaction id
    public int getId() {
        return id;
    }

    //Getter method that will return the Car object of the transaction
    public Car getCar() {
        return car;
    }

    //Getter method that will return the type of transaction it is  ("BUY" or "RETURN")
    public String getType() {
        return type;
    }

    //Getter method that will get the month of the transaction (Used in getting a return date with a car is returned)
    public int getMonth () {
        return date.get(Calendar.MONTH);
    }

    //Display method that gets all the information needed and returns string will all the transaction information
    public String display() {
        return "ID: " + id + " " +  sdf.format(date.getTime()) + " " + type + " Sales Person: " + salesPerson + " Car: " + car.display() ;
    }
}
