package com.example.project5_cs213_spring2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddDonutActivity extends AppCompatActivity {

    Spinner countSpinner;
    TextView flavorName;
    TextView price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donut);

        flavorName = (TextView) findViewById(R.id.flavorName);
        String flavor = getIntent().getStringExtra(String.valueOf(R.string.flavor));
        flavorName.setText(flavor);

        //Set up an onclick listener which will change the price depending on the count selection.
        countSpinner = (Spinner) findViewById(R.id.countSpinner);
        countSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int count = Integer.parseInt(countSpinner.getSelectedItem().toString());
                        //get the textview to modify
                        price = (TextView) findViewById(R.id.donutPrice);
                        price.setText(getResources().getString(R.string.dollar_sign) + String.format("%.2f", count*Constants.YEAST_DONUT_PRICE));
                    }
                    public void onNothingSelected(AdapterView<?> parent) { //selected by default, this does nothing but is still needed.
                    }
                });
    }

    //return the count to the previous activity.
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
}
