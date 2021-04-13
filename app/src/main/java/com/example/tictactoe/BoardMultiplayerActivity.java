package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tictactoe.database.Connection;
import com.example.tictactoe.installation.Installation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BoardMultiplayerActivity extends AppCompatActivity implements View.OnClickListener {

    String id = Installation.id(this);
    private final ImageButton[] buttons = new ImageButton[9];
    private int winningPos=-1;
    int roundCount=0;
    boolean isThisPlayerOne;
    boolean isActivePlayer;
    // empty => 0
    // p1 => 1
    // p2 => 2
    int[] gameState = {0, 0, 0,
            0, 0, 0,
            0, 0, 0};

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //wiersze
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //kolumny
            {0, 4, 8}, {2, 4, 6} //na krzyz
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_multiplayer);
        AssignVariables();
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode);
        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UpdateBoardVisuals((int[]) snapshot.child(gameCode).child("gameboard").getValue());
                roundCount++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode);
        if (v.getTag() != "0") {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1));
        boolean hasBoardChanged=false;
        if (isThisPlayerOne && isActivePlayer) {
            //TODO: zrobic rozne ikonki albo standaryzacje
            ((ImageButton) v).setImageResource(R.drawable.ic_person_black_24dp);
            v.setTag("1");
            gameState[gameStatePointer] = 1;
            hasBoardChanged=true;
        } else if (!isThisPlayerOne && isActivePlayer) {
            ((ImageButton) v).setImageResource(R.drawable.ic_people_alt_black_24dp);
            v.setTag("2");
            gameState[gameStatePointer] = 2;
            hasBoardChanged=true;
        }

        if(hasBoardChanged)
        {
            gamesRef.child("gameboard").setValue(gameState);
        }

        if (CheckWinner()) {
            if (buttons[winningPos].getTag()=="1") {
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
            }
        } else if (roundCount == 9) {
            PlayAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
        } else
        {
            isActivePlayer = !isActivePlayer;
        }

    }

    @Override
    public void onBackPressed() {
        RemovePlayerFromLobby();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        RemovePlayerFromLobby();
        super.onDestroy();
    }

    void RemovePlayerFromLobby() {
        Intent intent = getIntent();
        String gameCode = intent.getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");
        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(gameCode).child("player1").getValue() != null) {
                    if (snapshot.child(gameCode).child("player1").getValue().toString().toLowerCase().equals(id.toLowerCase())) {
                        gamesRef.child(gameCode).child("player1").removeValue();
                    } else gamesRef.child(gameCode).child("player2").removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    void UpdateBoardVisuals(int[] newGameState) {
        gameState = newGameState;
        for (int i = 0; i < 10; i++) {
            if (gameState[i] == 0) {
                buttons[i].setImageResource(0);
            } else if (gameState[i] == 1) {
                buttons[i].setImageResource(R.drawable.ic_person_black_24dp);
            } else if (gameState[i] == 2) {
                buttons[i].setImageResource(R.drawable.ic_people_alt_black_24dp);
            }

        }


    }

    void AssignVariables() {
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode);
        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(gameCode).child("player1").getValue().toString().toLowerCase().equals(id.toLowerCase())) {
                    isThisPlayerOne = true;
                    isActivePlayer = true;
                } else {
                    isThisPlayerOne = false;
                    isActivePlayer = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        buttons[0] = (ImageButton) findViewById(R.id.im_btn_mp0);
        buttons[1] = (ImageButton) findViewById(R.id.im_btn_mp1);
        buttons[2] = (ImageButton) findViewById(R.id.im_btn_mp2);
        buttons[3] = (ImageButton) findViewById(R.id.im_btn_mp3);
        buttons[4] = (ImageButton) findViewById(R.id.im_btn_mp4);
        buttons[5] = (ImageButton) findViewById(R.id.im_btn_mp5);
        buttons[6] = (ImageButton) findViewById(R.id.im_btn_mp6);
        buttons[7] = (ImageButton) findViewById(R.id.im_btn_mp7);
        buttons[8] = (ImageButton) findViewById(R.id.im_btn_mp8);
        for (int i = 0; i < buttons.length; i++) {
            //String buttonID = "im_btn_mp" + i;
            //int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            //buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
            buttons[i].setTag("0");
            buttons[i].setImageResource(0);
        }
    }

    public boolean CheckWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : winningPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 0) {
                winnerResult = true;
                winningPos = gameState[winningPosition[0]];
                break;
            }
        }

        return winnerResult;
    }

    public void PlayAgain(){
        roundCount = 0;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setTag("0");
        }
        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 0;
        }
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode);
        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gamesRef.child(gameCode).child("gameboard").setValue(gameState);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}