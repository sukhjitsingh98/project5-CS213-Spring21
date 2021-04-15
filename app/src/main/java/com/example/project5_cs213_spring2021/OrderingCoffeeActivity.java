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

public class OrderingCoffeeActivity extends AppCompatActivity {

    String size = "Short";
    int quantity = 1;
    Coffee coffee = new Coffee(size, quantity);
    TextView subtotalTextView;

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
        coffeeSizeDropdown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
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
        coffeeCountDropdown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
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
        subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);
        subtotalTextView.setText("$" + String.format("%.2f", coffee.getItemPrice()));
    }

    public void onSubmitCoffeeOrder(View view){

        Intent sendCoffeeIntent = new Intent();
        sendCoffeeIntent.putExtra("coffeeKey", new Coffee(coffee.getItemQuantity(), coffee.getCoffeeType(), coffee.getAddInsList()));
        setResult(Activity.RESULT_OK, sendCoffeeIntent);
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}