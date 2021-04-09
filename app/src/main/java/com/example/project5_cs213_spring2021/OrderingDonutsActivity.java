package com.example.project5_cs213_spring2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderingDonutsActivity extends AppCompatActivity {

    ArrayList<Donut> donuts = new ArrayList<>();
    int code = 100; //magic number to replace later.
    String flavor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_donuts);

        //Create the flavor listview
        ListView selectableFlavors = (ListView) findViewById(R.id.selectableFlavors);
        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.donutFlavors) //get the array from values
        );
        selectableFlavors.setAdapter(adapter);
        selectableFlavors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //once selected, call the activity.
                //Toast.makeText(OrderingDonutsActivity.this, selectableFlavors.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                flavor = selectableFlavors.getItemAtPosition(position).toString();
                Intent intent = new Intent(OrderingDonutsActivity.this, AddDonutActivity.class);
                //Send the name of the flavor to display.
                intent.putExtra(String.valueOf(R.string.flavor), flavor);
                startActivityForResult(intent, code);

            }
        });


    }

    //reload the listview with selected items and await clicks.
    private void loadSelected() {
        //look at the current donuts and display them
        ListView selected = (ListView) findViewById(R.id.selected);
        String[] items = new String[donuts.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = donuts.get(i).getItemString();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );
        selected.setAdapter(adapter);

        //now set up a onclick.
        selected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //display popup and remove the donut clicked if confirmed.
                Toast.makeText(OrderingDonutsActivity.this, "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);

        //Check for matching codes.
        if(request == code) {
            if(result == Activity.RESULT_OK) {
                //Get the resulting data from intent
                int donutCount = Integer.parseInt( data.getStringExtra(Intent.EXTRA_TEXT));
                //add it
                addDonutToList(donutCount);

            }
        }
    }

    //will add the select donut of chosen flavor and count to the list.
    private void addDonutToList( int donutCount) {

        //create the new donut
        Donut donut = new Donut(donutCount);
        donut.add(flavor);
        //add it to the list.
        donuts.add(donut);

        //Print out Toast confirmation
        String confirmation =  donut.getItemString();
        Toast.makeText(OrderingDonutsActivity.this, confirmation, Toast.LENGTH_SHORT).show();

        //Update the the selected listview
        loadSelected();
    }



}