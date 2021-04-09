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
                startActivityForResult(intent, code);

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

        //print test
        for(int i = 0; i < donuts.size(); i++) {
            System.out.println(donuts.get(i).getFlavor() + " : " + donuts.get(i).getItemString());
        }


        //WILL REPLACE THIS IN strings.xml
        String confirmation = "Added " + donutCount + " " + flavor + " donut(s)";
        Toast.makeText(OrderingDonutsActivity.this, confirmation, Toast.LENGTH_SHORT).show();
    }

}