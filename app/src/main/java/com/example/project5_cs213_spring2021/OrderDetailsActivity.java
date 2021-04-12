package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    Order currentOrder = new Order(0, null);
    ArrayAdapter adapter;
    ListView menuItemsList;

    int selectedItemIndex = Constants.DEFAULT_SELECTION_INDEX;

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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemIndex = position;
            }
        });
    }

    public void onMenuItemRemoved(View view){
        if(currentOrder.getItems().size() == 0){
            //print error message
            return;
        }
        else if(selectedItemIndex == Constants.DEFAULT_SELECTION_INDEX){
            //error message
            return;
        }
        currentOrder.remove( currentOrder.getItems().get(selectedItemIndex));
        adapter.clear();
        adapter.addAll(getItemStringArray());
        adapter.notifyDataSetChanged();
        setPriceFields();
    }

    //Method which returns arraylist of menuItem string data
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
        TextView totalLabel = findViewById(R.id.currentOrderTotalTextView);

        double salesTax = calculateSubTotal() * Constants.NJ_SALES_USE_TAX_RATE;

        subtotalLabel.setText("$" + String.format("%.2f", calculateSubTotal()));
        taxLabel.setText("$" + String.format("%.2f", salesTax));
        totalLabel.setText("$" + String.format("%.2f", (calculateSubTotal() + salesTax)));
    }
}