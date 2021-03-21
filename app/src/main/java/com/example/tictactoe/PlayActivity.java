package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ImageButton buttonSingleplayer = findViewById(R.id.Button_Singleplayer);
        buttonSingleplayer.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Play_Singleplayer.class);
            view.getContext().startActivity(intent);});
    }
}