package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tictactoe.database.Connection;
import com.example.tictactoe.installation.Installation;
import com.example.tictactoe.skins.Skins;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayMultiplayerActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Skins skins = new Skins();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_multiplayer);

        Connection connection = new Connection();
        mDatabase = connection.getDatabaseReference();
        String id = Installation.id(this);
        Button newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText gameCodeText = findViewById(R.id.game_code);
                String gameCode = gameCodeText.getText().toString();
                if(!gameCode.isEmpty())
                {
                    DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");
                    gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean isFirstSlotFree = !snapshot.child(gameCode).child("player1").exists();
                            boolean isSecondSlotFree = !snapshot.child(gameCode).child("player2").exists();
                            if(isFirstSlotFree)
                            {
                                mDatabase.child("Games").child(gameCode).child("player1").setValue(id);
                                mDatabase.child("Games").child(gameCode).child("gameboard").setValue("000000000");
                                mDatabase.child("Games").child(gameCode).child("player1Skin").setValue(skins.getCurrentXSkin());
                                Intent intent = new Intent(view.getContext(), BoardMultiplayerActivity.class);
                                intent.putExtra("gameCode", gameCode);
                                intent.putExtra("isFirst",true);
                                startActivity(intent);
                            }
                            else if(isSecondSlotFree)
                            {
                                mDatabase.child("Games").child(gameCode).child("player2").setValue(id);
                                mDatabase.child("Games").child(gameCode).child("gameboard").setValue("000000000");;
                                mDatabase.child("Games").child(gameCode).child("player2Skin").setValue(skins.getCurrentOSkin());
                                Intent intent = new Intent(view.getContext(), BoardMultiplayerActivity.class);
                                intent.putExtra("gameCode", gameCode);
                                intent.putExtra("isFirst",false);
                                startActivity(intent);
                            }
                            else Toast.makeText(PlayMultiplayerActivity.this, "Room already full", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //TODO : obsluga wyjatkow? a kto to widzial? a komu to potrzebne
                        }
                    });

                }
                else Toast.makeText(PlayMultiplayerActivity.this, "Game code cannot be empty", Toast.LENGTH_SHORT).show();
            }});

    }
}