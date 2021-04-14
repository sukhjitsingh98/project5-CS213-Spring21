package com.example.project5_cs213_spring2021;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 The Order class defines the abstract Order type which contains the array consisting of MenuItem class objects.
 Contains constructors to generate Order objects.
 The class allows for MenuItem objects to be removed, added, the price of the items to be updated, return the
 arraylist of items, and can return the order number of an individual Order object.
 This class also contains methods corresponding to the Parcelable interface class. These methods allow
 for the data members to be Parceled and transferred to a different activity to be reconstructed.

 @author German Munguia, Sukhjit Singh
 */

public class Order implements Customizable, Parcelable{
    private int orderNumber;
    private ArrayList<MenuItem> items = new ArrayList<>();
    private double totalPrice;

    /**
     Describes the kinds of special objects contained in this Parcelable instance's marshaled representation
     @return 0 default return value
     */
    public int describeContents(){
        return 0;
    }

    /**
     Method to flatten the object into a Parcel
     @param out the Parcel in which the object should be written
     @param flags indicates how the object should be written
     */
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(orderNumber);
        out.writeList(items);
        updateTotal();
    }

    /**
     Specialization of Creator class which allows the system to receive the ClassLoader the object is being created in.
     */
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>(){
        /**
         Create a new instance of the Parcelable class, instantiating it from the given Parcel
         whose data had previously been written by the writeToParcel() method and using the given ClassLoader
         @param in the Parcel to the read the object's data from
         */
        public Order createFromParcel (Parcel in){
            return new Order(in);
        }

        /**
         Create a new array of the Parcelable class
         @param size of the array
         */
        public Order[] newArray(int size){
            return new Order[size];
        }
    };

    /**
     Constructor used to generate a Order object with a given order number
     @param orderNumber unique identifier for an instance of the Order class.
     */
    public Order(int orderNumber){
        this.orderNumber = orderNumber;
    }

    /**
     Constructor used to generate a Order object with a given order number and menu items arrayList
     @param orderNumber unique identifier for an instance of the Order class.
     @param items arrayList containing Donut and/or Coffee menu items
     */
    public Order(int orderNumber, ArrayList items){
        this.orderNumber = orderNumber;
        this.items = items;
        updateTotal();
    }

    /**
     Constructor used to generate an Order object using the Parcelable data
     @param in the Parcel to the read the Order's data from
     */
    private Order(Parcel in){
        this.orderNumber = in.readInt();
        this.items = in.readArrayList( MenuItem.class.getClassLoader());
        updateTotal();
    }

    /**
     Add the given Object into the items arraylist.
     First checks if the given Object is an instance of MenuItem, if so it is casted and added to the items arraylist.
     Calls the updateTotal method to update the total price of the entire order.
     @param obj the Object to be added to the items arraylist
     @return true if the Object was added, false otherwise
     */
    public boolean add(Object obj){
        //Only add if its a menuItem, donut and coffee.
        if(obj instanceof MenuItem) {
            MenuItem item = (MenuItem)obj;
            items.add(item);
            //Since something was added, update the price
            updateTotal();
            return true;
        }
        return false;
    }

    /**
     Remove the given Object from the items arraylist.
     First checks if the given Object is an instance of MenuItem, if so it is casted and used to find the item to be
     removed from the items arraylist. If the arraylist contains the Object, it is removed.
     Calls the updateTotal method to update the total price of the entire order.
     @param obj the Object to be removed from the items arraylist
     @return true if the Object was removed, false otherwise
     */
    public boolean remove(Object obj){
        //ignore if its not a MenuItem
        if(obj instanceof MenuItem) {
            //Make sure the item exists.
            MenuItem item = (MenuItem) obj;
            if(items.contains(item)) {
                items.remove(item);
                //Since something was removed, update the price
                updateTotal();
                return true;
            }
        }
        return false;
    }

    /**
     Helper method which traverses the arraylist and calculates the subtotal price of the items.
     */
    private void updateTotal() {
        //reset the prize
        totalPrice = 0;
        //If there are no items, leave the price at 0.
        if(items == null){
            return;
        }
        else if(items.size() == 0) {
            return;
        }

        //traverse the list
        for(int i = 0; i < items.size(); i++) {
            totalPrice += items.get(i).getItemPrice() * items.get(i).getItemQuantity();
        }

    }

    /**
     Getter method which returns the total price of the items in the items arraylist.
     @return totalPrice of the items in the items arraylist
     */
    public double getTotal() {
        return totalPrice;
    }

    /**
     Getter method which returns the items arraylist.
     @return items arraylist containing MenuItem objects
     */
    public ArrayList<MenuItem> getItems() {
        return items;
    }

    /**
     Getter method which returns the order number of an instance Order.
     @return orderNumber unique identifier for an instance of Order.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

}


