package com.example.project5_cs213_spring2021;

import android.os.Parcel;
import android.os.Parcelable;

/**
 The Donut class defines the abstract Donut type.
 Contains constructors to generate Donut objects using the parameter data.
 The class allows for donut flavors to be removed, added, the price and data of individual Donut instances to be
 updated, and can return the String representation of the Donut data.
 This class also contains methods corresponding to the Parcelable interface class. These methods allow
 for the data members to be Parceled and transferred to a different activity to be reconstructed.

 @author German Munguia, Sukhjit Singh
 */

public class Donut extends MenuItem implements Customizable, Parcelable {
    private String flavor;
    private int count;

    /**
     Describes the kinds of special objects contained in this Parcelable instance's marshaled representation
     @return 0 default return value
     */
    public int describeContents() {
        return 0;
    }

    /**
     Method to flatten the object into a Parcel
     @param out the Parcel in which the object should be written
     @param flags indicates how the object should be written
     */
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(count);
        out.writeString(flavor);
        out.writeInt(count);
    }

    /**
     Specialization of Creator class which allows the system to receive the ClassLoader the object is being created in.
     */
    public static final Parcelable.Creator<Donut> CREATOR
            = new Parcelable.Creator<Donut>() {
        /**
         Create a new instance of the Parcelable class, instantiating it from the given Parcel
         whose data had previously been written by the writeToParcel() method and using the given ClassLoader
         @param in the Parcel to the read the object's data from
         */
        public Donut createFromParcel(Parcel in) {
            return new Donut(in);
        }

        /**
         Create a new array of the Parcelable class
         @param size of the array
         */
        public Donut[] newArray(int size) {
            return new Donut[size];
        }
    };


    /**
     Constructor used to generate a Donut object with a given quantity
     @param donutQuantity the quantity of the donuts to be ordered
     */
    public Donut(int donutQuantity){
        super(donutQuantity);
        super.setItemPrice(itemPrice());
        super.setItemString(donutDataString());
        count = donutQuantity;
    }

    /**
     Constructor used to generate a Donut object using the Parcelable data
     @param in the Parcel to the read the Donut's data from
     */
    private Donut(Parcel in) {
        super(in.readInt());
        this.flavor = in.readString();
        this.count = in.readInt();
        super.setItemPrice(itemPrice());
        super.setItemString(donutDataString());
    }

    /**
     Add and assign the given Object to the flavor String.
     First checks if the given Object is a String and is a valid flavor, if so it is casted and assigned to the
     flavor String. The String representation of the Donut instance is updated.
     @param obj the Object to be added and assigned to the flavor String
     @return true if the Object was added, false otherwise
     */
    public boolean add(Object obj){
        if(obj instanceof String){
            //Cast object into string
            String flavor = (String) obj;

            //Check if the flavor is valid
            if(isValidFlavor(flavor)){
                this.flavor = flavor;
                super.setItemString(donutDataString());
                return true;
            }
        }
        return false;
    }

    /**
     Remove the given Object from the flavor String.
     First checks if the given Object is a String and is a valid flavor, if so the flavor String is reassigned to a
     empty String. The String representation of the Coffee instance is updated.
     @param obj the Object to be removed from the flavor String
     @return true if the Object was removed, false otherwise
     */
    public boolean remove(Object obj){
        if(obj instanceof String){
            //Cast object into string
            String flavor = (String) obj;

            //Check if the add in is valid
            if(isValidFlavor(flavor) && !flavor.equals("")){
                this.flavor = "";
                super.setItemString(donutDataString());
                return true;
            }
        }
        return false;
    }

    /**
     This method calculates the subtotal price of the Donut instance based on the donut quantity
     @return subtotal price of the donuts
     */
    public double itemPrice(){
            return Constants.DONUT_PRICE * super.getItemQuantity();
    }

    /**
     Helper method which returns a Boolean value representing if the given String represents a valid flavor.
     @param flavor String representation of a potential donut flavor
     @return true if the given String represents a valid donut flavor, false otherwise
     */
    private boolean isValidFlavor(String flavor) {

        if(flavor.equals("Vanilla") || flavor.equals("Strawberry") || flavor.equals("Chocolate")){
                return true;
        }

        return false;
    }

    /**
     Getter method which returns the String representing the flavor of the Donut instance.
     @return flavor representing the String value of the donut's flavor.
     */
    public String getFlavor(){
        return flavor;
    }

    /**
     Getter method which returns the price of an instance of the Donut class.
     @return Donut instance price retrieved from the super class.
     */
    public double getDonutPrice() {
        return super.getItemPrice();
    }

    /**
     Helper method which creates a String representation of an instance of the Donut class and holds data about the
     donut type, quantity, and flavor
     @return donutData String representing the data members of an instance of the Donut class.
     */
    private String donutDataString(){
        String donutData = flavor + " flavor donut" + ", Quantity: " + super.getItemQuantity();
        return donutData;
    }



}

