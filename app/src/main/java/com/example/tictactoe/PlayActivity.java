package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ImageButton buttonSingleplayer = findViewById(R.id.Button_Singleplayer);
        buttonSingleplayer.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PlaySingleplayerActivity.class);
            view.getContext().startActivity(intent);});

        ImageButton buttonMultiplayer = findViewById(R.id.Button_Multiplayer);
        buttonMultiplayer.setOnClickListener(view -> {
            if(arePlayServicesOk())
            {
                Intent intent = new Intent(view.getContext(), UserListActivity.class);
                view.getContext().startActivity(intent);
            } });

    }

    private boolean arePlayServicesOk(){
        final GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        final int resultCode = googleAPI.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(resultCode)) {
                googleAPI.getErrorDialog(this, resultCode, 5000).show();
            }
            return false;
        }

        return true;
    }
}