package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StoreOrdersActivity extends AppCompatActivity {

    StoreOrders storeOrders = new StoreOrders();
    Order selectedOrder = new Order(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        Intent intent = getIntent();
        storeOrders = intent.getParcelableExtra("storeOrders");
    }
}