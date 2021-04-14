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

public class MainActivity extends AppCompatActivity {

    int orderNumber = Constants.FIRST_ORDER;
    Order currentOrder = new Order(orderNumber);
    StoreOrders storeOrders = new StoreOrders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleOrderCoffee(View view) {
        Intent intent = new Intent(this, OrderingCoffeeActivity.class);
        startActivityForResult(intent, Constants.FIRST_REQUEST_CODE);
    }

    public void handleOrderDonut(View view) {
        Intent intent = new Intent(this, OrderingDonutsActivity.class);
        //intent.putExtra("OrderingDonutsActivity", orderingDonutsActivity);
        startActivityForResult(intent, Constants.SECOND_REQUEST_CODE);
    }

    public void handleCurrentOrder(View view) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        System.out.println(currentOrder.getItems().get(0).getItemString());
        intent.putExtra("currentOrder", new Order(currentOrder.getOrderNumber(), currentOrder.getItems()));
        startActivityForResult(intent, Constants.THIRD_REQUEST_CODE);
    }

    public void handleStoreOrders(View view){
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        intent.putExtra("storeOrders", new StoreOrders(storeOrders.getOrders()));
        startActivityForResult(intent, Constants.FOURTH_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent ){
        super.onActivityResult(requestCode, resultCode, intent);

        //Receive Coffee Details When Submit Button is Pressed
        if(requestCode == Constants.FIRST_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Coffee coffee = (Coffee) intent.getExtras().getParcelable("key");
            currentOrder.add(coffee);
            //For debugging purposes. DELETE WHEN DONE
            TextView textView = (TextView) findViewById(R.id.outputTemp);
            textView.setText(coffee.getItemString());
        }
        else if(requestCode == Constants.SECOND_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<Donut> donuts = intent.getExtras().getParcelableArrayList("donuts");

            for (Donut donut : donuts){
                currentOrder.add(donut);
            }

        }

        //Receive Order Details When Back Button is Pressed
        else if(requestCode == Constants.THIRD_REQUEST_CODE && resultCode == Constants.BACK_PRESS_RESULT_CODE) {
            currentOrder = (Order) intent.getExtras().getParcelable("sendOrder");
        }
        //Receive Order Details When Submit Order Button is Pressed
        else if(requestCode == Constants.THIRD_REQUEST_CODE && resultCode == Constants.SUBMIT_ORDER_RESULT_CODE) {
            storeOrders.add((Order) intent.getExtras().getParcelable("sendOrder"));
            orderNumber++;
            currentOrder = new Order(orderNumber);
        }

        //Receive store orders when back button is pressed.
        else if(requestCode == Constants.FOURTH_REQUEST_CODE && resultCode == Constants.BACK_PRESS_RESULT_CODE) {
            storeOrders = (StoreOrders) intent.getExtras().getParcelable("storeOrders");
        }

    }

}