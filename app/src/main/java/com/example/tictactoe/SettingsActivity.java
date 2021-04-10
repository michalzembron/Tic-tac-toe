package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.skins.SkinsCurrentActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonSkins = findViewById(R.id.buttonSkins);
        buttonSkins.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SkinsCurrentActivity.class);
            view.getContext().startActivity(intent);});

        Button buttonThemes = findViewById(R.id.buttonThemes);
        buttonThemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThemesActivity.class);
                view.getContext().startActivity(intent);}
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}