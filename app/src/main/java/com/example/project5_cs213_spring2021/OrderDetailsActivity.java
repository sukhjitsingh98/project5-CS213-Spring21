package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    Order currentOrder = new Order(0, null);
    ArrayAdapter adapter;
    ListView menuItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent intent = getIntent();
        currentOrder = intent.getParcelableExtra("currentOrder");

        menuItemsList = (ListView) findViewById(R.id.menuItemListView);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getItemStringArray());

        menuItemsList.setAdapter(adapter);

        menuItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                menuItemsList.setSelected(true);
            }
        });
    }

    public void onMenuItemRemoved(View view){


    }

    private ArrayList getItemStringArray(){
        ArrayList<String> itemStringList = new ArrayList<>();
        for (int i = 0; i<currentOrder.getItems().size(); i++){
            itemStringList.add(currentOrder.getItems().get(i).getItemString());
        }
        return itemStringList;
    }
}