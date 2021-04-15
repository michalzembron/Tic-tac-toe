package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tictactoe.database.User;
import com.example.tictactoe.installation.Installation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatisticsActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getValueFromDatabase("Wins");
        getValueFromDatabase("Losts");
        getValueFromDatabase("Draws");
    }

    public void getValueFromDatabase(String name){
        database.child("Users").child(Installation.id(this)).child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                    if (name.equals("Wins") && !String.valueOf(task.getResult().getValue()).equals("null")){
                        TextView totalWinsAmount = (TextView)findViewById(R.id.totalWinsAmount);
                        totalWinsAmount.setText(String.valueOf(task.getResult().getValue()));
                    } else if (name.equals("Losts") && !String.valueOf(task.getResult().getValue()).equals("null")){
                        TextView totalWinsAmount = (TextView)findViewById(R.id.totalLostAmount);
                        totalWinsAmount.setText(String.valueOf(task.getResult().getValue()));
                    } else if (name.equals("Draws") && !String.valueOf(task.getResult().getValue()).equals("null")){
                        TextView totalWinsAmount = (TextView)findViewById(R.id.totalDrawsAmount);
                        totalWinsAmount.setText(String.valueOf(task.getResult().getValue()));
                    }
                }
            }
        });
    }

    public void setValueInDatabase(String name, int value){
        database.child("Users").child(Installation.id(this)).child(name).setValue(value);
    }
}