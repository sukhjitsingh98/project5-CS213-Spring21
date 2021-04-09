package com.example.project5_cs213_spring2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    }


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