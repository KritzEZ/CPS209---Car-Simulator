//Name: Kritarth Shah
//Student ID: 500907217

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CarDealershipSimulator {
    //Method to read and input from file
    static ArrayList<Car> carReader() throws IOException {
        ArrayList<Car> newCars = new ArrayList<Car>(); //Declares a new Car array called newCars that will story the array of cars that are being read from the tex file
        File inputFile = new File("cars.txt"); //Gets File type called "cars.txt"
        Scanner in = new Scanner(inputFile);

        //While loop goes on till the user input has no more line
        while (in.hasNext()){
            Scanner singleline = new Scanner (in.nextLine()); //Makes each line of the textfile into a individual scanner
            //According to the textfile format, each next value in scanner is assigned to the particular type and purpose
                String mfr = singleline.next();
                String color = singleline.next();
                String enum_model = singleline.next();
                String enum_enginetype = singleline.next();
                double safetyrating = Double.parseDouble(singleline.next());
                int maxrange = Integer.parseInt(singleline.next());
                boolean AWD = singleline.next().equals("AWD");
                double price = Double.parseDouble(singleline.next());
                if (singleline.hasNextInt())
                    newCars.add(new ElectricCar(mfr, color, Car.Model.valueOf(enum_model), Vehicle.PowerSource.valueOf(enum_enginetype), 4, safetyrating, maxrange, AWD, price, Integer.parseInt(singleline.next())));
                else
                    newCars.add(new Car(mfr, color, Car.Model.valueOf(enum_model), Vehicle.PowerSource.valueOf(enum_enginetype), 4, safetyrating, maxrange, AWD, price));
        }
        //Returns the new car array
        return newCars;
    }

    //Main class uses the carReader function and looks at the commands form the user with Exceptions and try/catches
    public static void main(String[] args) {
        CarDealership carDealership = new CarDealership(); //Creates new CarDealership object to use all the methods
        ArrayList<Car> newCars = new ArrayList<Car>(); //New Car Arraylist to contain the car inventory
        int lasttransactionid = 0, addcount = 0; //Int variable that holds the latest "BUY" transaction id
        boolean previouscarbought = false; //Boolean variable that shows if there is a previous car bought or not
        AccountingSystem accountingSystem = new AccountingSystem();

        //Try/Catch if there is an error in receiving the file or reading the file, if error occurs, it will output appropriate statement
        try {
            newCars = carReader();
        }catch (FileNotFoundException e) {
            System.out.println("Input File is Not Found");
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        Scanner linecommand = new Scanner(System.in);

        //While loop to go through every line on the command till it has no next line
        while (linecommand.hasNextLine()) {
            Scanner linecom = new Scanner(linecommand.nextLine()); //Scanner variable hold the commands in the line
            while (linecom.hasNext()) { //While loop goes through every part of the line ( it will break and move to the next line if the command is seen,
                // if more info on the command is needed it reads it and breaks. So no more than one command per line
                String command = linecom.next();
                //If and else if statements for all the commands and their appropriate actions
                if (command.equals("L")) {
                    carDealership.displayInventory();
                    break;
                }

                else if (command.equals("BUY")) {
                    //Tries command and if exception occurs will catch the two possibilities in the catch part
                    try {
                        int VINnum = linecom.nextInt();
                        System.out.println(carDealership.buyCar(VINnum));
                        lasttransactionid = carDealership.IDoflastBought(VINnum);
                        previouscarbought = true;
                        break;
                    } catch (NoSuchElementException e) {
                        System.out.println("Index of car bought is missing");
                        break;
                    }catch (NullPointerException e) {
                        System.out.println("VIN number is not in Inventory");
                        break;
                    }
                }

                else if (command.equals("RET")) {
                    //Tries to return car, but if there is no car to return it will throw an exception, and below it, it ill be caught and will print message and break.
                    try {
                        if (!previouscarbought) {
                            IllegalArgumentException nullReturn = new IllegalArgumentException();
                            throw nullReturn;
                        }
                        carDealership.returnCar(lasttransactionid);
                        previouscarbought = false;
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("No Car To Return");
                    }
                }

                else if (command.equals("ADD")) {
                    if (addcount==0) {
                        carDealership.addCars(newCars);
                        addcount++;
                    }
                    else
                        System.out.println("Cannot Add Car Inventory Again");
                    break;
                }

                else if (command.equals("SPR")) {
                    carDealership.sortByPrice();
                    break;
                }

                else if (command.equals("SSR")) {
                    carDealership.sortBySafetyRating();
                    break;
                }

                else if (command.equals("SMR")){
                    carDealership.sortByMaxRange();
                    break;
                }

                else if (command.equals("FPR")){
                    //Try and Catch to see if the min is greater than max or if the prices are negatives, throws a new exception according to the case and Catch will print message according
                    try {
                        double min = linecom.nextDouble(), max = linecom.nextDouble();
                        if (min>max)
                            throw new IllegalArgumentException("The Minimum value is larger than the Maximum value");
                        if (max<0 || min<0 || (max<0 && min<0))
                            throw new IllegalArgumentException("The Max and/or Min prices are a negative number --> Not valid");

                        carDealership.filterByPrice(min, max);
                        break;
                    }catch(IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        break;
                    }catch (NoSuchElementException e) {
                        System.out.println("Second Value is Missing");
                    }
                }

                else if (command.equals("FEL")){
                    carDealership.filterByElectric();
                    break;
                }

                else if (command.equals("FAW")){
                    carDealership.filterByAWD();
                    break;
                }

                else if (command.equals("FCL")){
                    carDealership.FilterClear();
                    break;
                }

                else if (command.equals("Q"))
                    return;

                //___________________________________________________________________
                //New Commands
                else if (command.equals("SALES")){
                    boolean continuetype = false;

                    if (!linecom.hasNext()){
                        carDealership.displayTransactions();
                        continuetype = true;
                    }

                    if (!continuetype) {

                        if (linecom.hasNextInt()) {
                            try { //Try and Catch for the SALES m command, throws exception if the int is greater than 12 (number of months)
                                int month_num = linecom.nextInt();
                                if (month_num >=12 || month_num<0)
                                    throw new IllegalArgumentException("The Number Entered For Month is Invalid Month Num is between 0-11");
                                carDealership.salesByMonth(month_num);
                            }catch(IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        }
                        else {
                            String salestype = linecom.next();
                            if (salestype.equals("TEAM")) {
                                accountingSystem.displaySalesTeam();
                            }

                            else if (salestype.equals("TOPSP")) {
                                accountingSystem.topsalesPerson();
                            }

                            else if (salestype.equals("STATS")) {
                                carDealership.displayStats();
                            }

                            //If there is something said after SALES that is not apart of the commands, it will says invalid
                            else
                                System.out.println("Sales Command is Invalid");
                        }
                    }
                    break;
                }
                //____________________________________________________________________

                //If none of the commands were asked, print invalid command and breaks to loop again
                else{
                    System.out.println("Command is Invalid");
                    break;
                }
            }
        }
    }
}