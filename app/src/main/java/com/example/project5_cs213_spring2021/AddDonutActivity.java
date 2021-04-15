package com.example.project5_cs213_spring2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 The AddDonutActivity class defines the methods associated with the activity_odd_donut.xml GUI file.
 The public methods define the actions performed when buttons and spinners are clicked in the GUI application.
 The private methods are helper methods to aid in the functionality of the button and spinner methods.
 A Donut flavor string is passed into this class and the methods return the quantity of the Donut to be ordered

 @author German Munguia, Sukhjit Singh
 */

public class AddDonutActivity extends AppCompatActivity {

    Spinner countSpinner;
    TextView flavorName;
    TextView price;

    /**
     Called when the activity is starting and is where most initialization happens.
     @param savedInstanceState bundle which contains the data most recently supplied when the activity previously shutdown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donut);

        flavorName = (TextView) findViewById(R.id.flavorName);
        String flavor = getIntent().getStringExtra(String.valueOf(R.string.flavor));
        flavorName.setText(flavor);

        //Set up an onclick listener which will change the price depending on the count selection.
        countSpinner = (Spinner) findViewById(R.id.countSpinner);

        countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             Callback method which is invoked when an item in this view has been selected
             @param parent The AdapterView where the selection happened
             @param view within the AdapterView that was selected
             @param position of the view in the adapter
             @param id of the item that was selected
             */
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int count = Integer.parseInt(countSpinner.getSelectedItem().toString());
                //get the textview to modify
                price = (TextView) findViewById(R.id.donutPrice);
                price.setText(getResources().getString(R.string.dollar_sign) + String.format("%.2f", count*Constants.DONUT_PRICE));
            }
            /**
             Callback method which is invoked when the selection disappears from this view
             @param parent The AdapterView where the selection happened
             */
            public void onNothingSelected(AdapterView<?> parent) { //selected by default, this does nothing but is still needed.
            }
        });
    }

    /**
     Method which sends the donut quantity string to the parent activity when the add donut button is clicked
     @param view associated with the listener for the Intent object
     */
    public void handleAddDonut(View view) {
        //get the number that was chosen.
        countSpinner = (Spinner) findViewById(R.id.countSpinner);
        String donutCount = countSpinner.getSelectedItem().toString();

        //Now return the selected count
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, donutCount);
        setResult(RESULT_OK, intent);
        finish(); //close and return
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
