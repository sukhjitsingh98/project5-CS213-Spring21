package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 The MainActivity class defines the methods associated with the activity_main.xml GUI file.
 The public methods define the actions performed when buttons are clicked in the GUI application.
 currentOrder and storeOrders arraylists are created and the methods interact with this arraylist to add, remove, or
 manipulate the Order and StoreOrders data given by the user in the GUI application.

 @author German Munguia, Sukhjit Singh
 */

public class MainActivity extends AppCompatActivity {

    int orderNumber = Constants.FIRST_ORDER;

    //Order object which will hold the current order being made by the user
    Order currentOrder = new Order(orderNumber);

    //StoreOrders object which will hold all orders made by user
    StoreOrders storeOrders = new StoreOrders();

    /**
     Called when the activity is starting and is where most initialization happens.
     @param savedInstanceState bundle which contains the data most recently supplied when the activity previously shutdown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     Loads the activity_ordering_coffee.xml file and generates a GUI scene by running the associated code.
     A listener is applied to the Intent object to obtain the Coffee Class Object from the child
     GUI and update the currentOrders arraylist in this Class.
     @param view associated with the listener for the Intent object
     */
    public void handleOrderCoffee(View view) {
        Intent intent = new Intent(this, OrderingCoffeeActivity.class);
        startActivityForResult(intent, Constants.FIRST_REQUEST_CODE);
    }

    /**
     Loads the activity_ordering_donuts.xml file and generates a GUI scene by running the associated code.
     A listener is applied to the Intent object to obtain the Donut arraylist from the child activity
     GUI and update the currentOrders arraylist in this Class.
     @param view associated with the listener for the Intent object
     */
    public void handleOrderDonut(View view) {
        Intent intent = new Intent(this, OrderingDonutsActivity.class);
        startActivityForResult(intent, Constants.SECOND_REQUEST_CODE);
    }

    /**
     Loads the activity_order_details.xml file and generates a GUI scene by running the associated code.
     A listener is applied to the Intent object to obtain the Order arraylist from the child
     GUI and update the currentOrders arraylist in this Class.
     A new Order object is generated after the current order is placed by the user.
     @param view associated with the listener for the Intent object
     */
    public void handleCurrentOrder(View view) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("currentOrder", new Order(currentOrder.getOrderNumber(), currentOrder.getItems()));
        startActivityForResult(intent, Constants.THIRD_REQUEST_CODE);
    }

    /**
     Loads the activity_store_orders.xml file and generates a GUI scene by running the associated code.
     A listener is applied to the Intent object to obtain the storeOrders arraylist from the child
     GUI and update the storeOrders arraylist in this Class.
     @param view associated with the listener for the Intent object
     */
    public void handleStoreOrders(View view){
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        intent.putExtra("storeOrders", new StoreOrders(storeOrders.getOrders()));
        startActivityForResult(intent, Constants.FOURTH_REQUEST_CODE);
    }

    /**
     When the child activity exits, the requestCode used for the activity is given, the resultCode is returned,
     and additional data is returned as well. This data is used to determine which action is to be taken to store the data.
     @param requestCode integer originally supplied to startActivityForResult() which identifies which activity the result came from
     @param resultCode integer returned by the child activity through its setResult() method
     @param intent which can return result data to the caller
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent ){
        super.onActivityResult(requestCode, resultCode, intent);

        //Receive Coffee Details When Submit Button is Pressed
        if(requestCode == Constants.FIRST_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Coffee coffee = (Coffee) intent.getExtras().getParcelable("coffeeKey");
            currentOrder.add(coffee);
            Toast.makeText(MainActivity.this, R.string.coffeeSuccessDialogue, Toast.LENGTH_SHORT).show();
        }
        //Receive Donut Details When Submit Button is Pressed
        else if(requestCode == Constants.SECOND_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<Donut> donuts = intent.getExtras().getParcelableArrayList("donuts");

            for (Donut donut : donuts){
                currentOrder.add(donut);
            }

            Toast.makeText(MainActivity.this, R.string.donutSuccessDialogue, Toast.LENGTH_SHORT).show();
        }
        //Receive Order Details When Back Button is Pressed
        else if(requestCode == Constants.THIRD_REQUEST_CODE && resultCode == Constants.BACK_PRESS_RESULT_CODE) {
            currentOrder = (Order) intent.getExtras().getParcelable("sendOrder");
            Toast.makeText(MainActivity.this, R.string.changesSavedDialogue, Toast.LENGTH_SHORT).show();
        }
        //Receive Order Details When Submit Order Button is Pressed
        else if(requestCode == Constants.THIRD_REQUEST_CODE && resultCode == Constants.SUBMIT_ORDER_RESULT_CODE) {
            storeOrders.add((Order) intent.getExtras().getParcelable("sendOrder"));
            orderNumber++;
            currentOrder = new Order(orderNumber);
            Toast.makeText(MainActivity.this, R.string.orderSuccessDialogue, Toast.LENGTH_SHORT).show();
        }

        //Receive store orders when back button is pressed.
        else if(requestCode == Constants.FOURTH_REQUEST_CODE && resultCode == Constants.BACK_PRESS_RESULT_CODE) {
            storeOrders = (StoreOrders) intent.getExtras().getParcelable("storeOrders");
            Toast.makeText(MainActivity.this, R.string.changesSavedDialogue, Toast.LENGTH_SHORT).show();
        }

    }

}