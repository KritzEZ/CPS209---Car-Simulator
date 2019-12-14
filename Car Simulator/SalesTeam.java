//Name: Kritarth Shah
//Student ID: 500907217

import java.util.*;

public class SalesTeam {

    static Random personindex = new Random(); //Random object calls new Random object;
    LinkedList<String> salesteam = new LinkedList<>(); //LinkedList that will hold the names of all the sales people
    static Map<String, Integer> totaltransactions = new HashMap<>(); //Creates HashMap to hold number of cars sold by each sales person

    //Constructor that will add all the sales members to the list and will add all the people in a HAshMap using emptyHashMap method
    public SalesTeam() {
        salesteam.add("Tommy");
        salesteam.add("Sam");
        salesteam.add("David");
        salesteam.add("Selena");
        salesteam.add("Jessica");

        //Code adds all the names to the HashMap with 0 in value as cars sold
        for (String names : salesteam)
            totaltransactions.put(names, 0);

    }

    //Gets random sales person, calls the salescounter method to add 1 to the value and returns the name of the sales person
    public String getSalesPerson() {
        int personpos = personindex.nextInt(salesteam.size());
        String key = salesteam.get(personpos);
        salesCounter(key);
        return key;
    }

    //Methods that creates a ListIterator and returns a string with all members of the sales team
    public String displayAll() {
        ListIterator<String> newsalesteam = salesteam.listIterator();
        String result = "";
        int counter = 0;
        while (newsalesteam.hasNext()) {
            if (counter==salesteam.size()-1)
                result += newsalesteam.next();
            else
                result += newsalesteam.next() + ", ";
            counter++;
        }
        return result;
    }

    //Method increments value in the HashMap of each sales person's cars sold by 1
    public void salesCounter(String key) {
        totaltransactions.put(key, totaltransactions.get(key)+1);
    }

    //Method runs through all the names and uses the values in the HashMap to get the name of the person with the most sold
    public String getTopSalesPerson() {
        int max = 0;
        String result = "";
        for (int i = 0; i<salesteam.size(); i++ ){
            String name = salesteam.get(i);
            if (totaltransactions.get(name) > max){
                max = totaltransactions.get(name);
            }
        }
        if (max==0)
            result = "No Sales Have Been Made (All Sales People Have 0 Sales)";
        else{
            result = "Top SP: ";
            for (int i = 0; i<salesteam.size(); i++) {
                String name2 = salesteam.get(i);
                if (totaltransactions.get(name2) == max)
                    result += name2 + " --> " + max + "   ";
            }
        }
        return result;
    }
}
