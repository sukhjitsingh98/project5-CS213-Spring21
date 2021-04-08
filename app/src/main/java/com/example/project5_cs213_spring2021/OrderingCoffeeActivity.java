package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OrderingCoffeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_coffee);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.coffeeSize_spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Short", "Tall", "Grande", "Venti"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
}