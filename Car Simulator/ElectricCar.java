//Name: Kritarth Shah
//Student ID: 500907217

public class ElectricCar extends Car {

    //Declaring instance variables
    private int rechareTime; //Variable holds int type that contains the recharge time of the electric car
    private String batteryType; //Variable holds String type that contains the type of battery the electric car has

    //Constructor method of class that inherits variables from the Car class and also other instance variables
    public ElectricCar(String mfr, String color, Model model, PowerSource power, int numWheels, double
            safetyRating,int maxRange, boolean AWD, double price, int rechargeTime) {
        super(mfr, color, model, power, numWheels, safetyRating, maxRange, AWD, price); //super that passes parameters for the super Car constructor
        this.rechareTime = rechargeTime;
        this.batteryType = "Lithium";
    }

    //Display method that add on the the display from the super class which is Car and appends the new instance variables
    public String display() {
        return super.display() + " EL, BAT: " + batteryType + " RCH: " + rechareTime;
    }
}