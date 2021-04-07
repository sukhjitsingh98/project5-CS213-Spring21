package com.example.project5_cs213_spring2021;

/**
 The MenuItem class defines the abstract MainMenu type.
 Contains constructors to generate MenuItem objects using the parameter data.
 The class allows for menu item data members to be set and  retrieved

 @author German Munguia, Sukhjit Singh
 */

public class MenuItem {
    private double itemPrice;
    private int itemQuantity;
    private String itemString;

    /**
     Constructor used to generate a MenuItem object with a given item quantity
     @param itemQuantity the quantity of the menu item to be ordered
     */
    public MenuItem (int itemQuantity){
        this.itemQuantity = itemQuantity;
    }

    /**
     Setter method which sets the price of an instance of the MenuItem class.
     @param itemPrice of the MenuItem Object
     */
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     Getter method which returns the subtotal price of an instance of the MenuItem class.
     @return itemPrice containing the subtotal price
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     Setter method which sets the quantity of an instance of the MenuItem class.
     @param itemQuantity of the MenuItem Object
     */
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     Getter method which returns the quantity of an instance of the MenuItem class.
     @return itemQuantity containing the quantity
     */
    public int getItemQuantity() {
        return itemQuantity;
    }

    /**
     Setter method which sets the String representing the data members of an instance of the MenuItem child class.
     @param itemString representing the data members of the child class
     */
    public void setItemString(String itemString){
        this.itemString = itemString;
    }

    /**
     Getter method which returns the String representation of an instance of the MenuItem child class.
     @return itemString containing the data member String representation
     */
    public String getItemString() {
        return itemString;
    }
}

