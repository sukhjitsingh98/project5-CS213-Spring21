package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OrderingCoffeeActivity extends AppCompatActivity {

    String size = "Short";
    int quantity = 0;
    Coffee coffee = new Coffee(size, quantity);

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

        coffeeSizeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                size = coffeeSizeDropdown.getItemAtPosition(position).toString();
                coffee.setCoffeeType(size);
            }
        });

        //Dropdown Menu for Coffee Quantity
        Spinner coffeeQuantityDropdown = findViewById(R.id.coffeeQuantity_spinner);
        ArrayAdapter<String> coffeeQuantityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.coffeeCount));
        coffeeQuantityDropdown.setAdapter(coffeeQuantityAdapter);

        coffeeQuantityDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                quantity = Integer.parseInt(coffeeQuantityDropdown.getItemAtPosition(position).toString());
                coffee.setCoffeeQuantity(quantity);
            }
        });
    }
}