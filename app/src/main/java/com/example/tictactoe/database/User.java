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
        database.child("Users").child(Installation.id(getContext)).child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
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
            }
        });
    }

    public void setValueInDatabase(String name, int value){
        database.child("Users").child(Installation.id(getContext)).child(name).setValue(value);
    }

    public void changeValueInDatabase(String name){
        database.child("Users").child(Installation.id(getContext)).child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if(String.valueOf(task.getResult().getValue()).equals("null")){
                        setValueInDatabase(name, 0);
                    }
                    if (!String.valueOf(task.getResult().getValue()).equals("null")){
                        database.child("Users").child(Installation.id(getContext)).child(name).setValue((Integer.parseInt(String.valueOf(task.getResult().getValue())) + 1));
                    }
                }
            }
        });
    }
}
