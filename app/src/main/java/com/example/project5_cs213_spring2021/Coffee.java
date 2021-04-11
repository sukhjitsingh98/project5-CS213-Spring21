package com.example.project5_cs213_spring2021;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import androidx.versionedparcelable.ParcelField;

/**
 The Coffee class defines the abstract Coffee type.
 Contains constructors to generate Coffee objects using the parameter data.
 The class allows for coffee addins to be removed, added, the price and data of individual Coffee instances to be
 updated, and can return the String representation of the Coffee data.

 @author German Munguia, Sukhjit Singh
 */

public class Coffee extends MenuItem implements Customizable, Parcelable {

    private String coffeeType;
    private ArrayList<String> addInsList = new ArrayList<String>();

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel out, int flags){
        out.writeInt(super.getItemQuantity());
        out.writeString(coffeeType);
        out.writeList(addInsList);
    }

    public static final Parcelable.Creator<Coffee> CREATOR = new Parcelable.Creator<Coffee>(){
        public Coffee createFromParcel (Parcel in){
            return new Coffee(in);
        }
        public Coffee[] newArray(int size){
            return new Coffee[size];
        }
    };

    /**
     Constructor used to generate a Coffee object with a given coffee size and quantity
     @param coffeeType the size of the coffee to be ordered
     @param coffeeQuantity the quantity of the coffee to be ordered
     */
    public Coffee(String coffeeType, int coffeeQuantity){
        super(coffeeQuantity);
        this.coffeeType = coffeeType;
        super.setItemPrice(itemPrice());
        super.setItemString(coffeeDataString());
    }

    public Coffee(int coffeeQuantity, String coffeeType, ArrayList addInsList ){
        super(coffeeQuantity);
        this.coffeeType = coffeeType;
        this.addInsList = addInsList;
        super.setItemPrice(itemPrice());
        super.setItemString(coffeeDataString());
    }

    private Coffee(Parcel in){
        super(in.readInt());
        this.coffeeType = in.readString();
        this.addInsList = in.readArrayList(null);
        super.setItemPrice(itemPrice());
        super.setItemString(coffeeDataString());
    }

    /**
     Add the given Object into the addInsList arraylist.
     First checks if the given Object is a String and is a valid addin item, if so it is casted and added to the
     arraylist. The subtotal price and String representation of the Coffee instance is updated.
     @param obj the Object to be added to the addInsList arraylist
     @return true if the Object was added, false otherwise
     */
    public boolean add(Object obj){
        if(obj instanceof String){
            //Cast object into string
            String addIn = (String) obj;

            //Check if the add in is valid
            if(isValidAddIn(addIn) && !addInsList.contains(addIn)){
                addInsList.add(addIn);
                super.setItemPrice((itemPrice()));
                super.setItemString(coffeeDataString());
                return true;
            }
        }
        return false;
    }

    /**
     Remove the given Object from the addInsList arraylist.
     First checks if the given Object is a String and is a valid addin item, if so it is casted and used to find the
     item to be removed from the addInsList arraylist. If the arraylist contains the Object, it is removed. The
     subtotal price and String representation of the Coffee instance is updated.
     @param obj the Object to be removed from the addInsList arraylist
     @return true if the Object was removed, false otherwise
     */
    public boolean remove(Object obj){
        if(obj instanceof String){
            //Cast object into string
            String addIn = (String) obj;

            //Check if the add in is valid
            if(isValidAddIn(addIn) && addInsList.contains(addIn)){
                addInsList.remove(addIn);
                super.setItemPrice((itemPrice()));
                super.setItemString(coffeeDataString());
                return true;
            }
        }
        return false;
    }

    /**
     This method calculates the subtotal price of a Coffee instance based on the coffee size, quantity, and number of
     addins.
     @return sum of the coffee size price and addins
     */
    public double itemPrice(){
        double sum = coffeeSizePrice() * super.getItemQuantity() + Constants.COFFEE_ADD_IN * addInsList.size();
        return sum;
    }

    /**
     Helper method which returns the price of a cup of coffee based on the stored coffee size String.
     @return price of a cup of coffee based on its size
     */
    private double coffeeSizePrice(){
        if(coffeeType.equals("Short")){
            return Constants.SHORT_BLACK_COFFEE;
        }
        else if(coffeeType.equals("Tall")){
            return Constants.TALL_BLACK_COFFEE;
        }
        else if(coffeeType.equals("Grande")){
            return Constants.GRANDE_BLACK_COFFEE;
        }
        else if(coffeeType.equals("Venti")){
            return Constants.VENTI_BLACK_COFFEE;
        }
        else return 0;
    }

    /**
     Helper method which returns a Boolean value representing if the given String represents a valid addin item.
     @param addIn String representation of a potential addin item
     @return true if the given String represents a valid addin item, false otherwise
     */
    private boolean isValidAddIn(String addIn){
        if(addIn.equals("cream") || addIn.equals("syrup") || addIn.equals("milk") || addIn.equals("caramel") || addIn.equals("whipped cream")){
            return true;
        }
        return false;
    }

    /**
     Getter method which returns the price of an instance of the Coffee class.
     @return Coffee instance price retrieved from the super class.
     */
    public double getCoffeePrice(){
        return super.getItemPrice();
    }

    /**
     Setter method which sets the size of an instance of the Coffee class.
     The subtotal price and String representation of the Coffee instance is updated.
     @param coffeeType String representation of a coffee cup size
     */
    public void setCoffeeType(String coffeeType){
        this.coffeeType = coffeeType;
        super.setItemPrice(itemPrice());
        super.setItemString(coffeeDataString());
    }

    /**
     Setter method which sets the quantity of an instance of the Coffee class.
     The subtotal price and String representation of the Coffee instance is updated.
     @param coffeeQuantity representing the quantity of coffee to be updated
     */
    public void setCoffeeQuantity(int coffeeQuantity){
        super.setItemQuantity(coffeeQuantity);
        super.setItemPrice(itemPrice());
        super.setItemString(coffeeDataString());
    }

    /**
     Helper method which creates a String representation of an instance of the Coffee class and holds data about the
     coffee size, quantity, and addins.
     @return coffeeData String representing the data members of an instance of the Coffee class.
     */
    private String coffeeDataString(){
        String coffeeData = coffeeType + " Coffee, " + "Quantity: " + super.getItemQuantity() + ", Addins: " + addInsToString();
        return coffeeData;
    }

    /**
     Helper method which creates a single String representation of the items in the addInsList arraylist
     @return String representing the addin items from the addInsList arraylist
     */
    private String addInsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : addInsList) {
            //Only add commas if there is more than one add in
            if(addInsList.size() > 1 && !s.equals(addInsList.get(0))){
                stringBuilder.append(", ");
            }
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public ArrayList<String> getAddInsList() {
        return addInsList;
    }
}

