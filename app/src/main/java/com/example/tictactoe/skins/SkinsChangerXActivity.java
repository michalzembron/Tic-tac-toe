package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.R;

public class SkinsChangerXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_changer_x);

        Skins skins = new Skins();
        skins.getCurrentXSkin();

        TextView textView_Skin_x_0 = findViewById(R.id.skin_x_0);
        TextView textView_Skin_x_1 = findViewById(R.id.skin_x_1);
        TextView textView_Skin_x_2 = findViewById(R.id.skin_x_2);

        // Grey  = #A6858585
        // Green = #A643CD49
        // Red   = #A6B80000

        textView_Skin_x_0.setOnClickListener(view -> {
            //Intent intent = new Intent(view.getContext(), SkinsCurrentActivity.class);
            //view.getContext().startActivity(intent);
            textView_Skin_x_0.setBackgroundColor(Color.parseColor("#A643CD49"));
            textView_Skin_x_1.setBackgroundColor(Color.parseColor("#A6858585"));
        });

        textView_Skin_x_1.setOnClickListener(view -> {
            textView_Skin_x_0.setBackgroundColor(Color.parseColor("#A6858585"));
            textView_Skin_x_1.setBackgroundColor(Color.parseColor("#A643CD49"));
            //textView_Skin_x_2.setBackgroundColor(Color.parseColor("#A643CD49"));

            //Firebase ustaw obecnie używany skin
        });

        textView_Skin_x_2.setOnClickListener(view -> {
            Log.i("SkinsChangerXActivity","skin_x_2 clicked !");
        });
    }
}