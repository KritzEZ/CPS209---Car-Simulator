//Name: Kritarth Shah
//Student ID: 500907217

public class Car extends Vehicle implements Comparable <Car>{
    private Model model; //Variable holds Model type that contains the type of car
    private int maxRange; //Variable holds int type that contains the maximum range of the car
    private double safetyRating, //Variable holds double type that contains the safety rating of the car
            price; //Variable holds double type thst contains the price of the car
    private boolean AWD; //Variable holds boolean type the contains if the car has AWD or not

    //Method sets types of car models to enums
    public enum Model {
        SEDAN, SUV, SPORTS, MINIVAN;
    }

    //Constructor method of class that inherits variables from the Vehicle class and also other instance variables
    public Car (String mfr, String color, Model model, PowerSource power, int numWheels, double
            safetyRating,int maxRange, boolean AWD, double price) {
        super(mfr, color, numWheels, power); //super that passes parameters for the super Vehicle constructor
        this.model = model;
        this.maxRange = maxRange;
        this.safetyRating = safetyRating;
        this.price = price;
        this.AWD = AWD;
    }

    //Display method that add on the the display from the super class which is Vehicle and appends the new instance variables
    public String display() {
        return super.display() + " " + model + " " + price + "$ SF: " + safetyRating + " RNG: " + maxRange;
    }

    //Compares one car object with another car objects in terms of the equals function inherited from the super class,
    //and the model and also if wheather they are both AWD's or not
    public boolean equals(Car other) {
        if (super.equals(other) && this.model.equals(other.model) && this.AWD==other.AWD)
            return true;
        else
            return false;
    }

    //Getter method for price
    public double getPrice() {
        return price;
    }

    //Getter method for safetyRating
    public double getSafetyRating() {
        return safetyRating;
    }

    //Getter method for MaxRange
    public int getMaxRange() {
        return maxRange;
    }

    //Method that checks if car object is AWD or not and returns answer
    public boolean isAWD() {
        return AWD;
    }

    //Method to return the powersourse of the car object
    public PowerSource getPower() {
        return super.getPower();
    }

    //CompareTo method
    public int compareTo(Car other) {
        return (Double.compare(this.getPrice(), other.getPrice()));
    }

    //Getter method, that will call the getVIN from Vehicle class that returns the VIN number of the car
    public int getVIN() {
        return super.getVIN();
    }
}