package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 The OrderDetailsActivity class defines the methods associated with the activity_order_details.xml GUI file.
 The public methods define the actions performed when buttons are clicked in the GUI application.
 The private methods are helper methods to aid in the functionality of the button methods.
 An Order Object is passed into this class and the methods interact with this object to add, remove, or
 manipulate the order data given by the user in the GUI application.

 @author German Munguia, Sukhjit Singh
 */

public class OrderDetailsActivity extends AppCompatActivity {
    Order currentOrder = new Order(0, null);
    ArrayAdapter adapter;
    ListView menuItemsList;

    int selectedItemIndex = Constants.DEFAULT_SELECTION_INDEX;

    /**
     Called when the activity is starting and is where most initialization happens.
     @param savedInstanceState bundle which contains the data most recently supplied when the activity previously shutdown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent intent = getIntent();
        currentOrder = intent.getParcelableExtra("currentOrder");

        menuItemsList = (ListView) findViewById(R.id.menuItemListView);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getItemStringArray());

        menuItemsList.setAdapter(adapter);

        setPriceFields();

        menuItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             Callback method which is invoked when an item in this AdapterView has been clicked
             @param parent The AdapterView where the click happened
             @param view within the AdapterView that was clicked
             @param position of the view in the adapter
             @param id of the item that was clicked
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemIndex = position;
            }
        });
    }

    /**
     Checks if there are any menu items selected by the user in the listview of the GUI application, if so the
     selection is deleted from the currentOrder arraylist.
     Once the item is deleted, the listview is updated with the remaining menu item data, if no items remains, the
     listview remains empty.
     @param view associated with the listener for the Intent object
     */
    public void onMenuItemRemoved(View view){
        if(currentOrder.getItems().size() == 0){
            Toast.makeText(OrderDetailsActivity.this, R.string.noItemDialogue, Toast.LENGTH_SHORT).show();
            return;
        }
        else if(selectedItemIndex == Constants.DEFAULT_SELECTION_INDEX){
            Toast.makeText(OrderDetailsActivity.this, R.string.noSelectionDialogue, Toast.LENGTH_SHORT).show();
            return;
        }
        currentOrder.remove( currentOrder.getItems().get(selectedItemIndex));
        adapter.clear();
        adapter.addAll(getItemStringArray());
        adapter.notifyDataSetChanged();
        setPriceFields();
    }

    /**
     Parcels the the Order Object, sends it to MainActivity, and closes this GUI page.
     If no items are in the Order, an error message is displayed
     @param view associated with the listener for the Intent object
     */
    public void onSubmitOrder(View view){
        if(currentOrder.getItems().size() == 0){
            Toast.makeText(OrderDetailsActivity.this, R.string.noItemToOrderDialogue, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendOrderIntent = new Intent();
        sendOrderIntent.putExtra("sendOrder", currentOrder);
        setResult(Constants.SUBMIT_ORDER_RESULT_CODE, sendOrderIntent);
        finish();
    }

    /**
     Override the device back button to prevent the loss of data when the button is pressed.
     The modified Order object is saved by sending it to the MainActivity.
     */
    @Override
    public void onBackPressed(){
        Intent sendOrderIntent = new Intent();
        sendOrderIntent.putExtra("sendOrder", currentOrder);
        setResult(Constants.BACK_PRESS_RESULT_CODE, sendOrderIntent);
        finish();
    }

    /**
     Override the UP button to prevent the MainActivity from restarting when the button is pressed.
     The modified Order object is saved by sending it to the MainActivity.
     */
    @Override
    public boolean onSupportNavigateUp(){
        Intent sendOrderIntent = new Intent();
        sendOrderIntent.putExtra("sendOrder", currentOrder);
        setResult(Constants.BACK_PRESS_RESULT_CODE, sendOrderIntent);
        finish();
        return true;
    }

    /**
     Helper method which generates an arrayList containing the string representations of the menu items.
     @return itemStringList containing the string representations of the menu items.
     */
    private ArrayList getItemStringArray(){
        ArrayList<String> itemStringList = new ArrayList<>();
        for (int i = 0; i<currentOrder.getItems().size(); i++){
            itemStringList.add(currentOrder.getItems().get(i).getItemString());
        }
        return itemStringList;
    }

    /**
     Helper method which calculates the subtotal price of the order by summing the individual menu item prices.
     @return sum of the individual item prices of the order.
     */
    private double calculateSubTotal(){
        double sum = 0;
        if(!currentOrder.getItems().isEmpty()){
            for (MenuItem menuItem : currentOrder.getItems()){
                sum += menuItem.getItemPrice();
            }
        }
        return sum;
    }

    /**
     Helper method which updates the price labels with the calculated values for the total order subtotal, sales tax,
     and total price with tax.
     */
    private void setPriceFields(){
        TextView subtotalLabel = findViewById(R.id.currentOrderSubtotalTextView);
        TextView taxLabel = findViewById(R.id.currentOrderTaxTextView);
        TextView totalLabel = findViewById(R.id.finalTotalTextView);

        double salesTax = calculateSubTotal() * Constants.NJ_SALES_USE_TAX_RATE;

        subtotalLabel.setText("$" + String.format("%.2f", calculateSubTotal()));
        taxLabel.setText("$" + String.format("%.2f", salesTax));
        totalLabel.setText("$" + String.format("%.2f", (calculateSubTotal() + salesTax)));
    }
}