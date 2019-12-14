//Name: Kritarth Shah
//Student ID: 500907217

import java.time.Month;
import java.util.*;

public class AccountingSystem {

    //Random object calls new Random object;
    Random num = new Random();
    static Random month = new Random();
    static Random day = new Random();

    Transaction temptrans; //Transaction variable that will hold the latest transaction in use
    SalesTeam salesTeam = new SalesTeam(); //Sales team variable that will be used to perform functions related to the team

    static Map<Integer, Transaction> transactionLibrary; //Map that has integer as key and Transaction object as value (will hold transactions with the key associated with the transaction number)

    //Constructor method --> makes Map into a HashMap
    public AccountingSystem() {
        transactionLibrary = new HashMap<>();
    }

    //Method that when call, will take all the sufficient details to make a new transaction and will create a random transaction id
    public String add(Calendar date, Car car, String salesPerson, String type, double salePrice) {
        int transaction_id = num.nextInt(99) + 1;
        boolean norepeat = false;

        //Checks to see if the random number generator did not repeat the id again, if it is, will generate random number again
        while (!norepeat){
            int counter = 0;
            for (Transaction transaction:transactionLibrary.values()){
                if (transaction.getId() == transaction_id){
                    transaction_id = num.nextInt(99) + 1;
                }
                else
                    counter++;
            }
            if (counter == transactionLibrary.size())
                norepeat = true;
        }

        temptrans = new Transaction(transaction_id, date, car, salesPerson, type, salePrice);
        transactionLibrary.put(transaction_id, temptrans);
        return temptrans.display();
    }

    //Method that will return a Transaction object that is the transaction id that is given in the parameters
    public Transaction getTransaction(int id) {
        Transaction temp = null; ;
        for (Integer idnum: transactionLibrary.keySet()){
            if (idnum == id) {
                temp = transactionLibrary.get(idnum);
                break;
            }
        }
        return temp;
    }

    //____________________________________________________________________________________________
    //Method that will generate a random date in the 2019 calendar as the day a car was bought
    public Calendar buyDate() {

        Calendar calendar;
        calendar = new GregorianCalendar();

        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, month.nextInt(12));
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxdays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daynum = day.nextInt(maxdays) + 1;

        calendar.set(Calendar.DAY_OF_MONTH, daynum);

        return calendar;
    }

    //Method that will take a date in the parameters (which is a car bought date) and will generate a date in the same
    // month but the day is more than the bought date
    public Calendar returnDate(Calendar boughtDate) {

        Calendar calendar;
        calendar = new GregorianCalendar();

        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, boughtDate.get(Calendar.MONTH));

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxdays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daynum = day.nextInt(maxdays - boughtDate.get(Calendar.DAY_OF_MONTH)+1) + boughtDate.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, daynum);

        return calendar;
    }
    //_________________________________________________________________________________________________

    //Method that will print all the transactions in the Transaction HashMap for the "SALES" command
    public void allTransactions() {
        for (Transaction transaction: transactionLibrary.values())
            System.out.println(transaction.display());
    }

    //Method that calls a method is Sales Team class to print all members of the team for the "SALES TEAM" command
    public void displaySalesTeam() {
        System.out.println("Sales Team: " + salesTeam.displayAll());
    }

    //Method that calls a method in Sales Team class to print the top sales person for the "SALES TOPS" command
    public void topsalesPerson() {
        System.out.println(salesTeam.getTopSalesPerson());
    }

    //Method that will go through all the Transaction values in the HashMap and print the transaction that happened in
    //the month given as an int in the parameters for the "SALES m" command
    public void monthlyTransactions(int month){
        int salescount = 0;
        for (Transaction transaction : transactionLibrary.values()){
            if (transaction.getMonth() == month) {
                System.out.println(transaction.display());
                salescount++;
            }
        }
        if (salescount==0)
            System.out.println("No Sales Have Been Made That Month");
    }

    //Method that combines and prints the Sales Stats by calling all different method for the "SALES STATS" command
    public void salesStats() {
        System.out.println("Total Sales: " + updatedtotalprice() + " Total Sold: " + getTotalsold() + " Avg Sales: " + (int) updatedtotalprice()/12
                + " Total Returned: " + getTotalreturned() + bestMonth());
    }

    //Method will go through all the transactions in the HashMap and depending on the type of transaction will either add the price of the car to the total price or will subtract (accounting for  returned cars)
    public double updatedtotalprice() {
        double totalsales = 0;
        for (Transaction transaction : transactionLibrary.values()) {
            if (transaction.getType().equals("BUY"))
                totalsales += transaction.getCar().getPrice();
            else
                totalsales += transaction.getCar().getPrice()*-1;
        }
        return totalsales;
    }

    //Method goes through all the transactions in the HashMap month by month and compares with the greatest number of cars sold in a month
    //At the end it will get the corresponding month to the integer and will return the best month
    public String bestMonth() {
        int max = 0;
        String result = " Best Month: ";
        for (int i = 0; i<12; i++) {
            int monthlysold = 0;
            for (Transaction transaction : transactionLibrary.values()) {
                if (transaction.getMonth() == i && transaction.getType().equals("BUY"))
                    monthlysold++;
            }
            if (monthlysold > max)
                max = monthlysold;
        }
        if (max == 0)
            result = " No Sales Have Been Made, No Month With Highest Sales";
        else {
            for (int j = 0; j < 12; j++) {
                ArrayList<Transaction> transhold = new ArrayList<>();
                for (Transaction transaction : transactionLibrary.values()) {
                    if (transaction.getMonth() == j && transaction.getType().equals("BUY"))
                        transhold.add(transaction);
                }
                if (transhold.size() == max)
                    result += Month.of(j + 1).name() + " cars sold - " + max + " ";
            }
        }
        return result;
    }

    //Method goes through all the tranaction values in the HashMap and count the number of "Return" type cars and return total returned cars
    public int getTotalreturned() {
        int totalreturned = 0;
        for (Transaction transaction: transactionLibrary.values()) {
            if (transaction.getType().equals("RETURN"))
                totalreturned++;
        }
        return totalreturned;
    }

    //Method goes through all transactions and counts the number of "BUY" transactions and returns the number
    public  int getTotalsold() {
        int sold = 0;
        for (Transaction transaction : transactionLibrary.values()) {
            if (transaction.getType().equals("BUY"))
                sold++;
        }
        return sold;
    }

    //Method that will return the transactions id of transaction with the corresponding VIN number provided in the parameters
    public int getTransactionIDwithVIN(int VIN) {
        int id = 0;
        for (Transaction transaction : transactionLibrary.values()){
            if (transaction.getType().equals("BUY") && transaction.getCar().getVIN()==VIN)
                id = transaction.getId();
        }
        return id;
    }
}
