package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe.database.Connection;
import com.example.tictactoe.installation.Installation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaySingleplayerActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private final ImageButton [] buttons = new ImageButton[9];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;

    // empty => 0
    // p1 => 1
    // p2 => 2
    int [] gameState = {0,0,0,
                        0,0,0,
                        0,0,0};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //wiersze
            {0,3,6}, {1,4,7}, {2,5,8}, //kolumny
            {0,4,8}, {2,4,6} //na krzyz
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_singleplayer);
        AssignVariables();

    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase.getInstance().getReference().child("Users").setValue("ssssssss");
        Connection connection = new Connection();
        //DatabaseReference ref = connection.getDatabaseReference("Users");

       // DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Installation installation = new Installation();

        if (v.getTag()!="0")
        {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1));

        if (activePlayer){
            //TODO: zrobic rozne ikonki albo standaryzacje
            ((ImageButton) v).setImageResource(R.drawable.ic_person_black_24dp);
            v.setTag("1");
            gameState[gameStatePointer] = 1;
        } else {
            ((ImageButton) v).setImageResource(R.drawable.ic_people_alt_black_24dp);
            v.setTag("2");
            gameState[gameStatePointer] = 2;
        }
        roundCount++;

        if (CheckWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                UpdatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
             //   ref.child(installation.id(this)).child("Wins").setValue(1);
                Log.i("Database write!!!!!!!!!", installation.id(this));
            } else {
                playerTwoScoreCount++;
                UpdatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
            }
            PlayAgain();
        } else if (roundCount == 9) {
            PlayAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer = !activePlayer;
        }

        if (playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText(R.string.p1Winning);
        } else if (playerOneScoreCount < playerTwoScoreCount) {
            playerStatus.setText(R.string.p2Winning);
        } else {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(v1 -> {
            PlayAgain();
            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            playerStatus.setText("");
            UpdatePlayerScore();
        });
    }

    public boolean CheckWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : winningPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 0) {
                winnerResult = true;
                break;
            }
        }

        return winnerResult;
    }

    @SuppressLint("SetTextI18n")
    public void UpdatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void PlayAgain(){
        roundCount = 0;
        activePlayer = true;
        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 0;
            buttons[i].setImageResource(0);
            buttons[i].setTag("0");
        }
    }

    private void AssignVariables()
    {
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.resetGame);

        for (int i = 0; i < buttons.length; i++){
            String buttonID = "im_btn" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (ImageButton) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
            buttons[i].setTag("0");
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }
}