package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tictactoe.R;
import com.example.tictactoe.SettingsActivity;

public class SkinsChangerXActivity extends AppCompatActivity {
    Skins skins = new Skins();

    private final TextView[] textViews = new TextView[9];

    // Grey  = #A6858585
    // Green = #A643CD49
    // Red   = #A6B80000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_changer_x);

        skins.getCurrentXSkin();

        assignTextViews();

        setCurrentSkin();
    }

    private void assignTextViews() {
        for (int i = 0; i < textViews.length; i++) {
            String textViewID = "skin_x_" + i;
            int resourceID = getResources().getIdentifier(textViewID, "id", getPackageName());
            Log.i("textViews", String.valueOf(resourceID));
            textViews[i] = findViewById(resourceID);
            int temp = i;
            textViews[i].setOnClickListener(view -> {
                Log.i("SkinsChangerOActivity","skin_x_" + temp +" clicked !");
                setButtonBackgroundColor(textViews[temp]);
                skins.setCurrentXSkin("ic_skins_" + temp);
            });
        }
    }

    private void setOtherButtonsGrey() {
        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.parseColor("#A6858585"));
        }
    }

    private void setCurrentSkin(){
        setOtherButtonsGrey();
        int currentSkin = Integer.parseInt(skins.getCurrentXSkin().substring(skins.getCurrentXSkin().length() - 1));
        textViews[currentSkin].setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    private void setButtonBackgroundColor(TextView textView){
        setOtherButtonsGrey();
        textView.setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SkinsChangerXActivity.this, SettingsActivity.class);
        finish();
        startActivity(intent);
    }
}