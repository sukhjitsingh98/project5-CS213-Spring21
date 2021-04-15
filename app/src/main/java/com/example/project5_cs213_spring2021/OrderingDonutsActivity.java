package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 The OrderingDonutsActivity class defines the methods associated with the activity_ordering_donuts.xml GUI file.
 The public methods define the actions performed when buttons and spinner items are clicked in the GUI application.
 The private methods are helper methods to aid in the functionality of the button and spinner methods.
 A donuts arraylist is created and the methods interact with this arraylist to add, remove, or
 manipulate the Donut data given by the user in the GUI application.

 @author German Munguia, Sukhjit Singh
 */

public class OrderingDonutsActivity extends AppCompatActivity {

    ArrayList<Donut> donuts = new ArrayList<>();
    String flavor = null;

    /**
     Called when the activity is starting and is where most initialization happens.
     @param savedInstanceState bundle which contains the data most recently supplied when the activity previously shutdown
     */
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

            /**
             Callback method which is invoked when an item in this AdapterView has been clicked
             @param parent The AdapterView where the click happened
             @param view within the AdapterView that was clicked
             @param position of the view in the adapter
             @param id of the item that was clicked
             */
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

    /**
     Helper method which reloads the listview with selected items and awaits clicks by the user
     */
    private void loadSelected() {
        //look at the current donuts and display them
        double priceSum = 0;
        ListView selected = (ListView) findViewById(R.id.selected);
        String[] items = new String[donuts.size()];

        //get the donut string representation and increment the subtotal price with the donut price
        for (int i = 0; i < items.length; i++) {
            items[i] = donuts.get(i).getItemString();
            priceSum += donuts.get(i).itemPrice();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        selected.setAdapter(adapter);

        //set the price based on the number of donuts since they all cost the same
        TextView totalPrice = (TextView) findViewById(R.id.price_of_donuts);
        totalPrice.setText( getResources().getString(R.string.dollar_sign) + String.format("%.2f", priceSum) );

        //now set up a onclick.
        selected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             Callback method which is invoked when an item in this AdapterView has been clicked
             @param parent The AdapterView where the click happened
             @param view within the AdapterView that was clicked
             @param position of the view in the adapter
             @param id of the item that was clicked
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //display popup and remove the donut clicked if confirmed.
                AlertDialog.Builder dialog = new AlertDialog.Builder(OrderingDonutsActivity.this);
                dialog.setMessage(R.string.remove_dialong)
                        .setPositiveButton(R.string.remove_button, new DialogInterface.OnClickListener() {
                            /**
                             Callback method which is invoked when a button in the dialogue is clicked
                             @param dialog that received the click
                             @param which the button that was clicked
                             */
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

    /**
     When the child activity exits, the request used for the activity is given, the result is returned,
     and additional data is returned as well. This data is used to determine which action is to be taken to store the data.
     @param request integer originally supplied to startActivityForResult() which identifies which activity the result came from
     @param result integer returned by the child activity through its setResult() method
     @param data which can return result data to the caller
     */
    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);

        //Check for matching codes.
        if(request == Constants.DONUT_REQUEST_CODE) {
            if(result == Activity.RESULT_OK) {
                //Get the resulting data from intent
                int donutCount = Integer.parseInt( data.getStringExtra(Intent.EXTRA_TEXT));
                //add the donut count to the list
                addDonutToList(donutCount);
            }
        }
    }

    /**
     Helper method which adds a donut of the selected flavor with the given count into the donuts arrayList
     @param donutCount integer representing the quantity of donuts to be added
     */
    private void addDonutToList(int donutCount) {
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

    /**
     Helper method which removes a Donut from the donuts arrayList which is at the index given
     @param index of Donut to be removed from the arrayList
     */
    private void removeDonutFromList(int index) {
        donuts.remove(index);
        //make toast which states the donut removal
        Toast.makeText(OrderingDonutsActivity.this, R.string.remove_donut, Toast.LENGTH_SHORT).show();
        //Reload the listView after the removal
        loadSelected();
    }

    /**
     Parcels the the Donut arrayList, sends it to MainActivity, and closes this GUI page.
     Displays an error message if no donut was selected for ordering
     @param view associated with the listener for the Intent object
     */
    public void onSubmitDonutOrder(View view) {
        if(donuts.size() == 0){
            Toast.makeText(OrderingDonutsActivity.this, R.string.noDonutsToOrderDialogue, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendDonutIntent = new Intent();
        sendDonutIntent.putParcelableArrayListExtra("donuts", donuts);
        setResult(Activity.RESULT_OK, sendDonutIntent);
        finish();
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