package com.example.project5_cs213_spring2021;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 The StoreOrders class defines the abstract StoreOrders type which contains the array consisting of Order class objects.
 Contains constructors to generate StoreOrders objects.
 The class allows for Order objects to be removed, added, return the order number of an individual Order object, and
 return the number of orders in the orders arraylist.
 This class also contains methods corresponding to the Parcelable interface class. These methods allow
 for the data members to be Parceled and transferred to a different activity to be reconstructed.

 @author German Munguia, Sukhjit Singh
 */
public class StoreOrders implements Customizable, Parcelable {

    private ArrayList<Order> orders = new ArrayList<>();

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
        out.writeList(orders);
    }

    /**
     Specialization of Creator class which allows the system to receive the ClassLoader the object is being created in.
     */
    public static final Parcelable.Creator<StoreOrders> CREATOR = new Parcelable.Creator<StoreOrders>(){
        /**
         Create a new instance of the Parcelable class, instantiating it from the given Parcel
         whose data had previously been written by the writeToParcel() method and using the given ClassLoader
         @param in the Parcel to the read the object's data from
         */
        public StoreOrders createFromParcel (Parcel in){
            return new StoreOrders(in);
        }

        /**
         Create a new array of the Parcelable class
         @param size of the array
         */
        public StoreOrders[] newArray(int size){
            return new StoreOrders[size];
        }
    };

    /**
     Default constructor used to generate a StoreOrders object
     */
    public StoreOrders(){
    }

    /**
     Constructor used to generate a StoreOrders object
     (Used when generating Parcelable objects)
     @param orders arrayList containing Order objects
     */
    public StoreOrders(ArrayList orders){
        this.orders = orders;
    }

    /**
     Constructor used to generate a Donut object using the Parcelable data
     @param in the Parcel to the read the Donut's data from
     */
    private StoreOrders(Parcel in){
        this.orders = in.readArrayList(Order.class.getClassLoader());
    }

    /**
     Add the given Object into the orders arraylist.
     First checks if the given Object is an instance of Order, if so it is casted and added to the orders arraylist.
     @param obj the Object to be added to the orders arraylist
     @return true if the Object was added, false otherwise
     */
    public boolean add(Object obj){
        if(obj instanceof Order) {
            Order order = (Order) obj;
            orders.add(order);
            return true;
        }
        return false;
    }

    /**
     Remove the given Object from the orders arraylist.
     First checks if the given Object is an instance of Order, if so it is casted and used to find the item to be
     removed from the orders arraylist. If the arraylist contains the Object, it is removed.
     @param obj the Object to be removed from the orders arraylist
     @return true if the Object was removed, false otherwise
     */
    public boolean remove(Object obj){
        if(obj instanceof Order) {
            //Make sure it exists.
            Order order = (Order) obj;
            if(orders.contains(order)) {
                orders.remove(order);
                return true;
            }
        }
        return false;
    }

    /**
     Getter method which returns an individual Order from the orders arraylist.
     @return orders.get(i) an individual order from the orders arraylist
     @param i the order number that is asked for.
     */
    public Order getOrder(int i){
        return orders.get(i);
    }

    /**
     Getter method which returns the orders arraylist containing Order objects.
     @return orders arraylist
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     Getter method which returns the number of Order objects in the orders arraylist.
     @return orders.size() the number of items in the orders arraylist
     */
    public int getNumOrders(){
        return orders.size();
    }

}