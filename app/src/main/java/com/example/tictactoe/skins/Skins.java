package com.example.tictactoe.skins;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.tictactoe.GetContext;

public class Skins {
    private static String unlocked_skins;
    private String CURRENT_X_SKIN;
    private String CURRENT_O_SKIN;

    public void setUnlockedSkins(String unlocked_skins){
        SharedPreferences settings = GetContext.getAppContext().getSharedPreferences("unlockedSkinsSettings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("unlocked_skins_list", unlocked_skins);
        editor.apply();
    }

    public void setCurrentXSkin(String CURRENT_X_SKIN){
        SharedPreferences settings = GetContext.getAppContext().getSharedPreferences("XSkinSettings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("xSkin",CURRENT_X_SKIN);
        editor.apply();
    }

    public void setCurrentOSkin(String CURRENT_O_SKIN){
        SharedPreferences settings = GetContext.getAppContext().getSharedPreferences("OSkinSettings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("oSkin",CURRENT_O_SKIN);
        editor.apply();
    }

    public void getUnlockedSkins(){
        SharedPreferences settings = GetContext.getAppContext().getSharedPreferences("unlockedSkinsSettings", 0);
        unlocked_skins = (settings.getString("unlocked_skins_list", ""));
        if (unlocked_skins.equals("")){
            setUnlockedSkins("ic_skins_x_0;ic_skins_o_0;ic_skins_1;");
        }
        Log.i("---getUnlocked_skins", unlocked_skins);
    }

    public void getCurrentXSkin(){
        SharedPreferences settings = GetContext.getAppContext().getSharedPreferences("XSkinSettings", 0);
        CURRENT_X_SKIN = (settings.getString("xSkin", ""));
        if (CURRENT_X_SKIN.equals("")){
            setCurrentXSkin("ic_skins_x_0");
        }
        Log.i("---getCurrentXSkin", CURRENT_X_SKIN);
    }

    public void getCurrentOSkin(){
        SharedPreferences settings = GetContext.getAppContext().getSharedPreferences("OSkinSettings", 0);
        CURRENT_O_SKIN = (settings.getString("oSkin", ""));
        if (CURRENT_O_SKIN.equals("")){
            setCurrentOSkin("ic_skins_o_0");
        }
        Log.i("---getCurrentOSkin", CURRENT_O_SKIN);
    }
}
