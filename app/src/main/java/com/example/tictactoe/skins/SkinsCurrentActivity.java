package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tictactoe.R;

public class SkinsCurrentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_current);

        ImageButton buttonSkinsChangerX = findViewById(R.id.skin_X);
        buttonSkinsChangerX.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SkinsChangerXActivity.class);
            view.getContext().startActivity(intent);});

        ImageButton buttonSkinsChangerO = findViewById(R.id.skin_O);
        buttonSkinsChangerO.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SkinsChangerOActivity.class);
            view.getContext().startActivity(intent);});

        Skins skins = new Skins();
        skins.getCurrentXSkin();
        skins.getCurrentOSkin();
        skins.getUnlockedSkins();
    }
}