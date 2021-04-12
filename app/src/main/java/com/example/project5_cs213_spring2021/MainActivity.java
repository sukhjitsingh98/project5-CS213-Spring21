package com.example.project5_cs213_spring2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int orderNumber = Constants.FIRST_ORDER;
    Order currentOrder = new Order(orderNumber);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleOrderCoffee(View view) {
        Intent intent = new Intent(this, OrderingCoffeeActivity.class);
        startActivityForResult(intent, Constants.FIRST_REQUEST_CODE);
    }

    public void handleOrderDonut(View view) {
        Intent intent = new Intent(this, OrderingDonutsActivity.class);
        startActivity(intent);
    }

    public void handleCurrentOrder(View view) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        System.out.println(currentOrder.getItems().get(0).getItemString());
        intent.putExtra("currentOrder", new Order(currentOrder.getOrderNumber(), currentOrder.getItems()));
        startActivityForResult(intent, Constants.THIRD_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent ){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == Constants.FIRST_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Coffee coffee = (Coffee) intent.getExtras().getParcelable("key");
            currentOrder.add(coffee);
            //For debugging purposes. DELETE WHEN DONE
            TextView textView = (TextView) findViewById(R.id.outputTemp);
            textView.setText(coffee.getItemString());
        }
    }
}