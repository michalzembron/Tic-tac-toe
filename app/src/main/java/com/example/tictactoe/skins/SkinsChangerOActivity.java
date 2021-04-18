package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tictactoe.R;
import com.example.tictactoe.SettingsActivity;

public class SkinsChangerOActivity extends AppCompatActivity {
    Skins skins = new Skins();

    TextView textView_Skin_o_0;
    TextView textView_Skin_o_1;
    TextView textView_Skin_o_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_changer_o);

        skins.getCurrentOSkin();

        // Grey  = #A6858585
        // Green = #A643CD49
        // Red   = #A6B80000

        textView_Skin_o_0 = findViewById(R.id.skin_o_0);
        textView_Skin_o_1 = findViewById(R.id.skin_o_1);
        textView_Skin_o_2 = findViewById(R.id.skin_o_2);

        setBackgroundColor();

        textView_Skin_o_0.setOnClickListener(view -> {
            skins.setCurrentOSkin("ic_skins_o_0");
            textView_Skin_o_0.setBackgroundColor(Color.parseColor("#A643CD49"));
            textView_Skin_o_1.setBackgroundColor(Color.parseColor("#A6858585"));
        });

        textView_Skin_o_1.setOnClickListener(view -> {
            skins.setCurrentOSkin("ic_skins_1");
            textView_Skin_o_0.setBackgroundColor(Color.parseColor("#A6858585"));
            textView_Skin_o_1.setBackgroundColor(Color.parseColor("#A643CD49"));
            //textView_Skin_x_2.setBackgroundColor(Color.parseColor("#A643CD49"));

            //Firebase ustaw obecnie używany skin
        });

        textView_Skin_o_2.setOnClickListener(view -> {
            Log.i("SkinsChangerOActivity","ic_skins_2 clicked !");
        });
    }

    private void setBackgroundColor(){
        if(skins.getCurrentOSkin().equals("ic_skins_o_0")){
            textView_Skin_o_0.setBackgroundColor(Color.parseColor("#A643CD49"));
        } else if (skins.getCurrentOSkin().equals("ic_skins_1")){
            textView_Skin_o_1.setBackgroundColor(Color.parseColor("#A643CD49"));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SkinsChangerOActivity.this, SettingsActivity.class);
        finish();
        startActivity(intent);
    }
}