package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tictactoe.R;

public class SkinsCurrentActivity extends AppCompatActivity {
    Skins skins = new Skins();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_current);
        skins.getCurrentXSkin();
        skins.getCurrentOSkin();
        skins.getUnlockedSkins();

        ImageButton buttonSkinsChangerX = findViewById(R.id.skin_X);
        ImageButton buttonSkinsChangerO = findViewById(R.id.skin_O);

        buttonSkinsChangerX.setImageResource(getResources().getIdentifier(skins.getCurrentXSkin(), "drawable", getPackageName()));
        buttonSkinsChangerO.setImageResource(getResources().getIdentifier(skins.getCurrentOSkin(), "drawable", getPackageName()));

        buttonSkinsChangerX.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SkinsChangerXActivity.class);
            view.getContext().startActivity(intent);});

        buttonSkinsChangerO.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SkinsChangerOActivity.class);
            view.getContext().startActivity(intent);});
    }
}