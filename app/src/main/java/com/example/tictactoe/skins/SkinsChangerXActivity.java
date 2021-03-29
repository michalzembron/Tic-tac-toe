package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        TextView buttonSkins = findViewById(R.id.skin_x_0);
        buttonSkins.setOnClickListener(view -> {
            //Intent intent = new Intent(view.getContext(), SkinsCurrentActivity.class);
            //view.getContext().startActivity(intent);
            Log.i("SkinsChangerXActivity","skin_x_0 clicked !");
        });
    }
}