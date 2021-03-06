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
                if (temp == 0){
                    skins.setCurrentOSkin("ic_skins_o_" + temp);
                } else {
                    skins.setCurrentOSkin("ic_skins_" + temp);
                }
            });
        }
    }

    private void setCurrentSkin(){
        int currentSkin = Integer.parseInt(skins.getCurrentOSkin().substring(skins.getCurrentOSkin().length() - 1));
        textViews[currentSkin].setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    private void setButtonBackgroundColor(TextView textView){
        StringBuilder myString = new StringBuilder(skins.getBoughtSkins());

        for(int i = 0; i < myString.length(); i++){

            if(myString.charAt(i) == 'Y'){
                textViews[i].setBackgroundColor(Color.parseColor("#A6858585"));
                textViews[i].setClickable(true);
            }
        }

        textView.setBackgroundColor(Color.parseColor("#A643CD49"));
    }

    private void setBoughtSkinBa(){
        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.parseColor("#A6B80000"));
            textView.setClickable(false);
        }
        StringBuilder myString = new StringBuilder(skins.getBoughtSkins());

        for(int i = 0; i < myString.length(); i++){

            if(myString.charAt(i) == 'Y'){
                textViews[i].setBackgroundColor(Color.parseColor("#A6858585"));
                textViews[i].setClickable(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SkinsChangerOActivity.this, SettingsActivity.class);
        finish();
        startActivity(intent);
    }
}