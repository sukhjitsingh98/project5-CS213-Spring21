package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

/**
 The OrderingCoffeeActivity class defines the methods associated with the activity_ordering_coffee.xml GUI file.
 The public methods define the actions performed when buttons, checkbox, and spinner items are clicked in the GUI
 application.
 The private methods are helper methods to aid in the functionality of the button, checkbox, and spinner methods.
 An Addins arraylist is created and the methods interact with this arraylist to add, remove, or
 manipulate the Coffee addin data given by the user in the GUI application.

 @author German Munguia, Sukhjit Singh
 */

public class OrderingCoffeeActivity extends AppCompatActivity {

    String size = "Short";
    int quantity = 1;
    Coffee coffee = new Coffee(size, quantity);
    TextView subtotalTextView;

    /**
     Called when the activity is starting and is where most initialization happens.
     @param savedInstanceState bundle which contains the data most recently supplied when the activity previously shutdown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_coffee);

        //Dropdown Menu for Coffee Type (Size)
        Spinner coffeeSizeDropdown = findViewById(R.id.coffeeSize_spinner);
        ArrayAdapter<String> coffeeSizeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.coffeeSizes));

        coffeeSizeDropdown.setAdapter(coffeeSizeAdapter);
        coffeeSizeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        size = coffeeSizeDropdown.getSelectedItem().toString();
                        coffee.setCoffeeType(size);
                        subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);
                        subtotalTextView.setText("$" + String.format("%.2f", coffee.getItemPrice()));
                    }
                    public void onNothingSelected(AdapterView<?> parent) { //selected by default, this does nothing but is still needed.
                    }
                });

        //Dropdown Menu for Coffee Quantity
        Spinner coffeeCountDropdown = findViewById(R.id.coffeeCount_spinner);
        ArrayAdapter<String> coffeeCountAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.coffeeCount));

        coffeeCountDropdown.setAdapter(coffeeCountAdapter);

        coffeeCountDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        quantity = Integer.parseInt(coffeeCountDropdown.getSelectedItem().toString());
                        coffee.setCoffeeQuantity(quantity);
                        subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);
                        subtotalTextView.setText("$" + String.format("%.2f", coffee.getItemPrice()));
                    }
                    public void onNothingSelected(AdapterView<?> parent) { //selected by default, this does nothing but is still needed.
                    }
                });

    }

    /**
     Checks if any of the checkboxes are selected and based on the selection it adds or removes the associated addin from the
     Coffee object and updates the subtotal price.
     @param view associated with the listener for the Intent object
     */
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()){
            case R.id.cream_checkbox:
                if(checked)
                    coffee.add("cream");
                else
                    coffee.remove("cream");
                break;
            case R.id.syrup_checkbox:
                if(checked)
                    coffee.add("syrup");
                else
                    coffee.remove("syrup");
                break;
            case R.id.milk_checkbox:
                if(checked)
                    coffee.add("milk");
                else
                    coffee.remove("milk");
                break;
            case R.id.caramel_checkbox:
                if(checked)
                    coffee.add("caramel");
                else
                    coffee.remove("caramel");
                break;
            case R.id.whippedcream_checkbox:
                if(checked)
                    coffee.add("whipped cream");
                else
                    coffee.remove("whipped cream");
                break;
        }

        //Display the updated price
        subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);
        subtotalTextView.setText("$" + String.format("%.2f", coffee.getItemPrice()));
    }

    public void onSubmitCoffeeOrder(View view){

        Intent sendCoffeeIntent = new Intent();
        sendCoffeeIntent.putExtra("coffeeKey", new Coffee(coffee.getItemQuantity(), coffee.getCoffeeType(), coffee.getAddInsList()));
        setResult(Activity.RESULT_OK, sendCoffeeIntent);
        onBackPressed();
    }

    /**
     Override the UP button to prevent the MainActivity from restarting when the button is pressed.
     */
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}