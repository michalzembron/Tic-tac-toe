package com.example.tictactoe.skins;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tictactoe.GetContext;

public class Skins {

    GetContext getContext = new GetContext();

    @SuppressLint("ApplySharedPref")
    public void setUnlockedSkins(String unlocked_skins){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("unlockedSkinsSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("unlocked_skins_list", unlocked_skins);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setCurrentXSkin(String CURRENT_X_SKIN){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("XSkinSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("xSkin",CURRENT_X_SKIN);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setCurrentOSkin(String CURRENT_O_SKIN){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("OSkinSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("oSkin",CURRENT_O_SKIN);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void setBoughtSkins(Integer boughtSkins){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("BoughtSkins", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (boughtSkins == 0){
            editor.putString("BoughtSkinsList", "YNNNNNNNN");
        } else {
            StringBuilder myString = new StringBuilder(getBoughtSkins());
            myString.setCharAt(boughtSkins, 'Y');
            editor.putString("BoughtSkinsList", String.valueOf(myString));
        }
        editor.commit();
    }

    public String getUnlockedSkins(){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("unlockedSkinsSettings", Context.MODE_PRIVATE);
        String unlocked_skins = (settings.getString("unlocked_skins_list", ""));
        if (unlocked_skins.equals("")){
            setUnlockedSkins("ic_skins_x_0;ic_skins_o_0;ic_skins_1;");
        }
        Log.i("---getUnlocked_skins", unlocked_skins);
        return unlocked_skins;
    }

    public String getCurrentXSkin(){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("XSkinSettings", Context.MODE_PRIVATE);
        String CURRENT_X_SKIN = (settings.getString("xSkin", ""));
        if (CURRENT_X_SKIN.equals("")){
            setCurrentXSkin("ic_skins_x_0");
        }
        Log.i("---getCurrentXSkin", CURRENT_X_SKIN);
        return CURRENT_X_SKIN;
    }

    public String getCurrentOSkin(){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("OSkinSettings", Context.MODE_PRIVATE);
        String CURRENT_O_SKIN = (settings.getString("oSkin", ""));
        if (CURRENT_O_SKIN.equals("")){
            setCurrentOSkin("ic_skins_o_0");
        }
        Log.i("---getCurrentOSkin", CURRENT_O_SKIN);
        return CURRENT_O_SKIN;
    }

    public String getBoughtSkins(){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("BoughtSkins", Context.MODE_PRIVATE);
        String BoughtSkinsList = (settings.getString("BoughtSkinsList", ""));
        if (BoughtSkinsList.equals("")){
            setBoughtSkins(0);
            BoughtSkinsList = (settings.getString("BoughtSkinsList", ""));
            return BoughtSkinsList;
        }
        return BoughtSkinsList;
    }
}
