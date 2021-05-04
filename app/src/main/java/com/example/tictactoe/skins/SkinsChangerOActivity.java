package com.example.tictactoe.skins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tictactoe.R;
import com.example.tictactoe.SettingsActivity;

import java.util.ArrayList;

public class SkinsChangerOActivity extends AppCompatActivity {
    Skins skins = new Skins();

    private final TextView[] textViews = new TextView[9];

    // Grey  = #A6858585
    // Green = #A643CD49
    // Red   = #A6B80000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins_changer_o);

        skins.getCurrentOSkin();

        assignTextViews();

        setBoughtSkinBa();
        setCurrentSkin();
    }

    private void assignTextViews() {
        for (int i = 0; i < textViews.length; i++) {
            String textViewID = "skin_o_" + i;
            int resourceID = getResources().getIdentifier(textViewID, "id", getPackageName());
            Log.i("textViews", String.valueOf(resourceID));
            textViews[i] = findViewById(resourceID);
            int temp = i;
            textViews[i].setOnClickListener(view -> {
                Log.i("SkinsChangerOActivity","skin_o_" + temp +" clicked !");
                setButtonBackgroundColor(textViews[temp]);
                skins.setCurrentOSkin("ic_skins_" + temp);
            });
        }
    }

    private void setOtherButtonsGrey() {
        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.parseColor("#A6858585"));
        }
    }

    private void setCurrentSkin(){
        //setOtherButtonsGrey();
        int currentSkin = Integer.parseInt(skins.getCurrentOSkin().substring(skins.getCurrentOSkin().length() - 1));
        textViews[currentSkin].setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    private void setButtonBackgroundColor(TextView textView){
        setOtherButtonsGrey();
        textView.setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    private void setBoughtSkinBa(){
        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.parseColor("#A6B80000"));
            textView.setClickable(false);
        }
        ArrayList<Integer> boughtSkins = skins.getBoughtSkins();
        for(int i = 0; i < boughtSkins.size(); i++){
            textViews[boughtSkins.get(i)].setBackgroundColor(Color.parseColor("#A643CD49"));
            textViews[boughtSkins.get(i)].setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SkinsChangerOActivity.this, SettingsActivity.class);
        finish();
        startActivity(intent);
    }
}