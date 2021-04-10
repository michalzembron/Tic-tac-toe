package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tictactoe.currency.Currency;

public class ShopActivity extends AppCompatActivity {

    Currency currency = new Currency();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        TextView textViewCurrency = findViewById(R.id.textViewCurrency);
        textViewCurrency.setText(currency.getCurrency().toString());
    }
}