package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.tictactoe.currency.Currency;
import com.example.tictactoe.installation.Installation;
import com.example.tictactoe.skins.Skins;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private AdView adview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);



        // Ustawianie motywu ciemnego
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.apply();
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Log.i("TEST", database.getDatabase().getReference().toString());

        //Dodawanie domyślnego skina do zakupionych
        Skins skins = new Skins();
        //skins.setBoughtSkins(0);
        //skins.setBoughtSkins(3);
        //Log.i("dddd",String.valueOf(skins.getBoughtSkins()));
        Currency currrency = new Currency();
        currrency.setCurrency(150);


        //Generowanie i/lub odczyt ID uzytkownika
        Installation.id(this);
        Log.i("______User ID", Installation.id(this));

        Button buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PlayActivity.class);
            view.getContext().startActivity(intent);});

        Button buttonStatistics = findViewById(R.id.buttonStatistics);
        buttonStatistics.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), StatisticsActivity.class);
            view.getContext().startActivity(intent);});

        Button buttonShop = findViewById(R.id.buttonShop);
        buttonShop.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ShopActivity.class);
            view.getContext().startActivity(intent);});

        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SettingsActivity.class);
            view.getContext().startActivity(intent);});

        Button buttonExit = findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}