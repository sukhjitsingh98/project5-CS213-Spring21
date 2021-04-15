package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreOrdersActivity extends AppCompatActivity {

    StoreOrders storeOrders = new StoreOrders();
    Order selectedOrder = new Order(0);
    Spinner orderNumbers;
    ListView orderItems;
    TextView finalOrderTotal;

    /**
     Called when the activity is starting and is where most initialization happens.
     @param savedInstanceState bundle which contains the data most recently supplied when the activity previously shutdown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        Intent intent = getIntent();
        storeOrders = intent.getParcelableExtra("storeOrders");
        updateSpinner(); //set the spinner

        //now set and onclick which will display the order that is selected.
        orderNumbers.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //the order number chosen in the spinner
                        int orderSelected = Integer.parseInt(orderNumbers.getSelectedItem().toString());
                        int orderSelectedIndex = orderNumbers.getSelectedItemPosition(); //Get the position since the order number will not always be the position.

                        //set the current order in order to obtain its items
                        selectedOrder = storeOrders.getOrder(orderSelectedIndex);

                        //add the order items to the listview
                        orderItems = findViewById(R.id.storeOrdersMenuItemListView);

                        ArrayAdapter adapter2 = new ArrayAdapter<String>(
                                StoreOrdersActivity.this,
                                android.R.layout.simple_list_item_1,
                                getItemStringArray());
                        orderItems.setAdapter(adapter2);

                        //now update the total price of that order.
                        finalOrderTotal = findViewById(R.id.finalTotalTextView);
                        //price should contain the tax
                        double tax = selectedOrder.getTotal() * Constants.NJ_SALES_USE_TAX_RATE;
                        finalOrderTotal.setText( getResources().getString(R.string.dollar_sign) + String.format("%.2f", (selectedOrder.getTotal() + (selectedOrder.getTotal() * Constants.NJ_SALES_USE_TAX_RATE))  ));

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

    }

    //returns array of items from the selected order.
    private ArrayList getItemStringArray(){
        ArrayList<String> itemStringList = new ArrayList<>();
        for (int i = 0; i<selectedOrder.getItems().size(); i++){
            itemStringList.add(selectedOrder.getItems().get(i).getItemString());
        }
        return itemStringList;
    }

    //remove the select order.
    public void handleOrderRemoval(View view) {
        if(storeOrders.getOrders().size() == 0){
            Toast.makeText(StoreOrdersActivity.this, R.string.noOrderDialogue, Toast.LENGTH_SHORT).show();
            return;
        }

        int orderSelectedIndex = orderNumbers.getSelectedItemPosition();
        storeOrders.remove( storeOrders.getOrder(orderSelectedIndex));

        //now update the spinner and listView
        updateSpinner();
        Toast.makeText(StoreOrdersActivity.this, R.string.orderDeletedDialogue, Toast.LENGTH_SHORT).show();

    }

    private void updateSpinner() {

        //if there are no orders, clear the listview in case of removal.
        if(storeOrders.getNumOrders() == 0) {
            orderItems = findViewById(R.id.storeOrdersMenuItemListView);
            orderItems.setAdapter(null);

            finalOrderTotal = findViewById(R.id.finalTotalTextView);
            finalOrderTotal.setText("");
        }

        ArrayList<Integer> orderNumbersList = new ArrayList<>();
        for(int i = 0; i < storeOrders.getNumOrders(); i++) {
            orderNumbersList.add( storeOrders.getOrder(i).getOrderNumber() );
        }
        orderNumbers = findViewById(R.id.orderNumberSpinner);

        //populate the spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbersList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumbers.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent sendOrderIntent = new Intent();
        sendOrderIntent.putExtra("storeOrders", storeOrders);
        setResult(Constants.BACK_PRESS_RESULT_CODE, sendOrderIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        Intent sendOrderIntent = new Intent();
        sendOrderIntent.putExtra("storeOrders", storeOrders);
        setResult(Constants.BACK_PRESS_RESULT_CODE, sendOrderIntent);
        finish();
        return true;
    }

}