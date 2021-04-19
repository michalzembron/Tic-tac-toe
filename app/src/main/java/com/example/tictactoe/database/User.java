package com.example.tictactoe.database;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tictactoe.GetContext;
import com.example.tictactoe.installation.Installation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    GetContext getContext = new GetContext();

    public void getValueFromDatabase(String name, TextView textView){
        database.child("Users").child(Installation.id(getContext)).child(name).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                if(String.valueOf(task.getResult().getValue()).equals("null")){
                    setValueInDatabase(name, 0);
                }
                if (!String.valueOf(task.getResult().getValue()).equals("null")){
                    textView.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    public void setValueInDatabase(String name, int value){
        database.child("Users").child(Installation.id(getContext)).child(name).setValue(value);
    }

    public void changeValueInDatabase(String name){
        database.child("Users").child(Installation.id(getContext)).child(name).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                if(String.valueOf(task.getResult().getValue()).equals("null")){
                    setValueInDatabase(name, 1);
                }
                if (!String.valueOf(task.getResult().getValue()).equals("null")){
                    database.child("Users").child(Installation.id(getContext)).child(name).setValue((Integer.parseInt(String.valueOf(task.getResult().getValue())) + 1));
                }
            }
        });
    }

    public void getPlayerID(String gameCode, String player, String state){
        database.child("Games").child(gameCode).child(player).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                if (!String.valueOf(task.getResult().getValue()).equals("null")){
                    changeValueInDatabaseMultiplayer(String.valueOf(task.getResult().getValue()), state);
                }
            }
        });
    }

    public void changeValueInDatabaseMultiplayer(String playerID, String name){
        database.child("Users").child(playerID).child(name).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                if(String.valueOf(task.getResult().getValue()).equals("null")){
                    setValueInDatabase(name, 1);
                }
                if (!String.valueOf(task.getResult().getValue()).equals("null")){
                    database.child("Users").child(playerID).child(name).setValue((Integer.parseInt(String.valueOf(task.getResult().getValue()))) + 1);
                }
            }
        });
    }
}
