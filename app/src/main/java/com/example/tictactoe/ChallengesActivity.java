package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tictactoe.installation.Installation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChallengesActivity extends AppCompatActivity {

    private Installation installation = new Installation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        ImageView meczy5 = findViewById(R.id.meczy5Image);
        ImageView meczy10 = findViewById(R.id.meczy10Image);
        ImageView meczy15 = findViewById(R.id.meczy15Image);
        ImageView meczy50 = findViewById(R.id.meczy50Image);
        ImageView meczy75 = findViewById(R.id.meczy75Image);
        ImageView meczy100 = findViewById(R.id.meczy100Image);
        ImageView meczy250 = findViewById(R.id.meczy250Image);
        ImageView meczy500 = findViewById(R.id.meczy500Image);
        ImageView meczy1000 = findViewById(R.id.meczy1000Image);
        TextView wygraneMeczeText = (TextView)findViewById(R.id.iloscWygranychMeczyTekst);
        GetContext context= new GetContext();
        String id = installation.id(context);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int iloscWygranychMeczy = snapshot.child("Users").child(id).child("Wins").getValue(Integer.class);
                if(iloscWygranychMeczy>=5) {
                    meczy5.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy5.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=10) {
                    meczy10.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy10.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=15) {
                    meczy15.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy15.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=50) {
                    meczy50.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy50.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=75) {
                    meczy75.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy75.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=100) {
                    meczy100.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy100.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=250) {
                    meczy250.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy250.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=500) {
                    meczy500.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy500.setColorFilter(Color.parseColor("#A643CD49"));
                }
                if(iloscWygranychMeczy>=1000) {
                    meczy1000.setImageDrawable(getResources().getDrawable(R.drawable.ic_mark));
                    meczy1000.setColorFilter(Color.parseColor("#A643CD49"));
                }
                wygraneMeczeText.setText(String.valueOf(iloscWygranychMeczy));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}