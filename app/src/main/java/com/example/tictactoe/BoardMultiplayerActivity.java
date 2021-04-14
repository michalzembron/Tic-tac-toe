package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //wiersze
            {0,3,6}, {1,4,7}, {2,5,8}, //kolumny
            {0,4,8}, {2,4,6} //na krzyz
    };
    String  gameState = "000000000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_board);
        AssignVariables();
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode+"/gameboard");
        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UpdateBoardVisuals(snapshot.getValue().toString());
                isActivePlayer=!isActivePlayer;
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
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1));
        boolean hasBoardChanged=false;
        if (isThisPlayerOne && isActivePlayer) {
            ((ImageButton) v).setImageResource(R.drawable.ic_person_black_24dp);
            v.setTag("1");
            gameState = changeCharInPosition(gameStatePointer,'1',gameState);
            hasBoardChanged=true;
        } else if (!isThisPlayerOne && isActivePlayer) {
            ((ImageButton) v).setImageResource(R.drawable.ic_people_alt_black_24dp);
            v.setTag("2");
            gameState = changeCharInPosition(gameStatePointer,'2',gameState);
            hasBoardChanged=true;
        }

        if(hasBoardChanged)
        {
            gamesRef.child("gameboard").setValue(gameState);
        }

//        if (CheckWinner())
//        {
//            if (buttons[winningPos].getTag()=="1") {
//                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
//            }
//        } else if (roundCount == 9) {
//            PlayAgain();
//            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
//        } else
//       {
//       }
//
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

    void UpdateBoardVisuals(String newGameState) {
        gameState = newGameState;
        Log.d("gamestate",gameState);
        for (int i = 0; i < 9; i++) {
            if (gameState.charAt(i) == 0) {
                buttons[i].setImageResource(0);
            } else if (gameState.charAt(i) == '1') {
                buttons[i].setImageResource(R.drawable.ic_person_black_24dp);
            } else if (gameState.charAt(i) == '2') {
                buttons[i].setImageResource(R.drawable.ic_people_alt_black_24dp);
            }
        }
    }

    void AssignVariables() {
        Boolean isFirst = getIntent().getBooleanExtra("isFirst",true);
        //bo po dolaczeniu 2 gracza sie wszystko zmienia
        isActivePlayer = !isFirst;
        isThisPlayerOne = isFirst;

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "im_btn_mp" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
            buttons[i].setTag("0");
            buttons[i].setImageResource(0);
        }
    }

    public boolean CheckWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : winningPositions){
            if (gameState.charAt(winningPosition[0]) == gameState.charAt(winningPosition[1]) &&
                    gameState.charAt(winningPosition[1]) == gameState.charAt(winningPosition[2]) &&
                    gameState.charAt(winningPosition[0]) != 0) {
                winnerResult = true;
                winningPos = gameState.charAt(winningPosition[0]);
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
        gameState = "000000000";
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode);
        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gamesRef.child(gameCode).child("gameboard").setValue((gameState));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public String changeCharInPosition(int position, char ch, String str){
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }
}