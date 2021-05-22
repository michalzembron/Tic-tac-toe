package com.example.tictactoe.currency;

import android.content.SharedPreferences;

import com.example.tictactoe.GetContext;

public class Currency {

    GetContext getContext = new GetContext();

    public void setCurrency(Integer CURRENCY){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("Wallet", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("currency", CURRENCY);
        editor.apply();
    }

    public Integer getCurrency(){
        SharedPreferences settings = getContext.getAppContext().getSharedPreferences("Wallet", 0);
        int CURRENCY = (settings.getInt("currency", 0));
        if (CURRENCY == 0){
            setCurrency(0);
        }
        return CURRENCY;
    }
}
