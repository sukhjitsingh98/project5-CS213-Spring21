package com.example.project5_cs213_spring2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderingDonutsActivity extends AppCompatActivity {

    ArrayList<Donut> donuts = new ArrayList<>();
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
                flavor = selectableFlavors.getItemAtPosition(position).toString();
                Intent intent = new Intent(OrderingDonutsActivity.this, AddDonutActivity.class);
                //Send the name of the flavor to display.
                intent.putExtra(String.valueOf(R.string.flavor), flavor);
                startActivityForResult(intent, Constants.DONUT_REQUEST_CODE);

            }
        });

    }

    //reload the listview with selected items and await clicks.
    private void loadSelected() {
        //look at the current donuts and display them
        double priceSum = 0;
        ListView selected = (ListView) findViewById(R.id.selected);
        String[] items = new String[donuts.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = donuts.get(i).getItemString();
            priceSum += donuts.get(i).itemPrice();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );
        selected.setAdapter(adapter);

        //set the price based on the number of donuts since they all cost the same
        TextView totalPrice = (TextView) findViewById(R.id.price_of_donuts);
        totalPrice.setText( getResources().getString(R.string.dollar_sign) + String.format("%.2f", priceSum) );

        //now set up a onclick.
        selected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //display popup and remove the donut clicked if confirmed.
                AlertDialog.Builder dialog = new AlertDialog.Builder(OrderingDonutsActivity.this);
                dialog.setMessage(R.string.remove_dialong)
                        .setPositiveButton(R.string.remove_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Deletion has been confirmed, delete the donut from the list.
                                removeDonutFromList(position);
                            }
                        })
                        .setNegativeButton(R.string.cancel_button, null);

                AlertDialog alert = dialog.create();
                alert.show();
            }
        });

    }


    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);

        //Check for matching codes.
        if(request == Constants.DONUT_REQUEST_CODE) {
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

    //remove from the list at the given index
    private void removeDonutFromList(int index) {
        donuts.remove(index);
        //make toast which states the donut removal
        Toast.makeText(OrderingDonutsActivity.this, R.string.remove_donut, Toast.LENGTH_SHORT).show();
        //Reload the listView after the removal
        loadSelected();
    }

    //will return the info of donut selection
    public void onSubmitDonutOrder(View view) {

        Intent sendDonutIntent = new Intent();
        sendDonutIntent.putParcelableArrayListExtra("donuts", donuts);
        setResult(Activity.RESULT_OK, sendDonutIntent);
        finish();
    }
}