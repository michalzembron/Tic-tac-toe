package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tictactoe.database.User;

public class StatisticsActivity extends AppCompatActivity {
    User user = new User();

    TextView totalWinsAmount;
    TextView totalLostsAmount;
    TextView totalDrawsAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        totalWinsAmount = (TextView)findViewById(R.id.totalWinsAmount);
        totalLostsAmount = (TextView)findViewById(R.id.totalLostAmount);
        totalDrawsAmount = (TextView)findViewById(R.id.totalDrawsAmount);

        user.getValueFromDatabase("Wins", totalWinsAmount);
        user.getValueFromDatabase("Losts", totalLostsAmount);
        user.getValueFromDatabase("Draws", totalDrawsAmount);
    }
}