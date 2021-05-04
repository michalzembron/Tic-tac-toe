package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe.currency.Currency;
import com.example.tictactoe.skins.Skins;
import com.example.tictactoe.skins.SkinsChangerXActivity;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    Currency currency = new Currency();
    Skins skins = new Skins();
    private final TextView[] textViews = new TextView[8];
    private Integer[] listPrice = new Integer[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        TextView textViewCurrency = findViewById(R.id.textViewCurrency);
        textViewCurrency.setText(currency.getCurrency().toString());

        skins.getCurrentXSkin();

        assignTextViews();
        assignListPrice();

        Log.i("_KUPIONE SKINY", skins.getBoughtSkins());
    }

    // Grey  = #A6858585
    // Green = #A643CD49
    // Red   = #A6B80000

    private void assignTextViews() {
        for (int i = 1; i < textViews.length; i++) {
            String textViewID = "skin_o_" + i;
            int resourceID = getResources().getIdentifier(textViewID, "id", getPackageName());
            Log.i("textViews", String.valueOf(resourceID));
            textViews[i] = findViewById(resourceID);
            int temp = i;
            textViews[i].setOnClickListener(view -> {
                Log.i("ShopActivity","skin_o_" + temp +" clicked !");
                //setButtonBackgroundColor(textViews[temp]);
                buySkinButton(temp);
                //skins.setCurrentXSkin("ic_skins_" + temp);
            });
        }
    }

    private void assignListPrice(){

        int cost = 150;

        for(int i = 1; i < textViews.length; i++){
            listPrice[i] = cost;
            cost = cost + 150;
        }
    }

    private void setOtherButtonsGrey() {
        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.parseColor("#A6858585"));
        }
    }

    private void setButtonGreen(TextView textView){
        textView.setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    private void buySkinButton(int buttonNumber){

        Currency currency = new Currency();
        Skins skin = new Skins();

        if(currency.getCurrency() < listPrice[buttonNumber]){

            Context context = getApplicationContext();
            CharSequence text = "You don't have enough currency!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);

            builder.setCancelable(true);
            builder.setTitle("Do you want to buy a skin ?");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setButtonGreen(textViews[buttonNumber]);
                    textViews[buttonNumber].setClickable(false);
                    skins.setBoughtSkins(buttonNumber);
                    currency.setCurrency(currency.getCurrency() - listPrice[buttonNumber]);
                }
            });
            builder.show();
        }
        Log.i("_KUPIONE SKINY", skins.getBoughtSkins());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShopActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}