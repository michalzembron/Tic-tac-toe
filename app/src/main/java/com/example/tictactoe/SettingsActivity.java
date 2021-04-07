package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.tictactoe.skins.SkinsChangerXActivity;
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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}