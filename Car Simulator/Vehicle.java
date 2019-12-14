//Name: Kritarth Shah
//Student ID: 500907217

import java.util.ArrayList;
import java.util.Random;

public class Vehicle {

    //Setting common vehicle instance variables
    public PowerSource power; //Variable holds PowerSource(enum) type and contains one of the enums from PowerSource
    private String mfr, //Variable holds String type that contains the manufacturer of the Vehicle
            color; //Variable holds String type that contains the color of the Vehicle
    private int VIN,
            numWheels; //Variable hols int type that contains the number of wheels in the vehicle
    Random VINnum = new Random(); //Random object calls new Random object;

    ArrayList<Integer> VINlibrary = new ArrayList<Integer>(); //Integer ArrayList will hold all the VIN number that have already been used

    //Method sets types of Powersources to enums type
    public enum PowerSource{
        GAS_ENGINE, DIESEL_ENGINE, ELECTRIC_MOTOR;
    }

    //Constructor for class Vehicle, has 4 parameters and sets the instance variables
    public Vehicle (String mfr, String color, int numWheels, PowerSource power) {
        this.mfr = mfr;
        this.color = color;
        this.power = power;
        this.numWheels = numWheels;
        this.VIN = VINnum.nextInt(400) + 100;
        VINlibrary.add(VIN);

        //Checks to see if the VIN umber is already been used or not, if it has, the random number will generate again
        boolean norepeat = false;
        while (!norepeat) {
            int counter = 0;
            for (int i = 0 ; i<VINlibrary.size(); i++) {
                if (VIN != VINlibrary.get(i))
                    counter++;
            }
            if (counter==VINlibrary.size()-1)
                norepeat = true;
            else
                this.VIN = VINnum.nextInt(400) + 100;
        }
    }

    //Checks if one car is equal to another car in terms of manufacture, power and number of wheels
    public boolean equals(Object other) {
        Object newvehicle = (Vehicle) other;
        return (this.mfr.equals(((Vehicle) newvehicle).getMfr()) && this.power==((Vehicle) newvehicle).power && this.numWheels==((Vehicle) newvehicle).numWheels);
    }

    //Method for display, returns a string with the manufacture and the color of the car for printing at the end
    public String display() {
        return "VIN: " + this.VIN + " " + this.mfr + " " + this.color;
    }

    //Getter method for the mfr (gets the string in mfr when called)
    public String getMfr() {
        return mfr;
    }

    //Getter method for the type of power (returns the type of power that the car is (returns a PowerSource value))
    public PowerSource getPower() {
        return power;
    }

    //Getter method that return the VIN number of the Vehicle object
    public int getVIN() {
        return VIN;
    }
}