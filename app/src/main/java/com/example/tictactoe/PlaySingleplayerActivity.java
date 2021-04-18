package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe.currency.Currency;
import com.example.tictactoe.database.User;
import com.example.tictactoe.skins.Skins;

public class PlaySingleplayerActivity extends AppCompatActivity implements View.OnClickListener{
    Currency currency = new Currency();
    User user = new User();

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

    Skins skins = new Skins();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_singleplayer);
        AssignVariables();

    }

    @Override
    public void onClick(View v) {

        if (v.getTag()!="0")
        {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1));

        if (activePlayer){
            ((ImageButton) v).setImageResource(getResources().getIdentifier(skins.getCurrentXSkin(), "drawable", getPackageName()));
            v.setTag("1");
            gameState[gameStatePointer] = 1;
        } else {
            ((ImageButton) v).setImageResource(getResources().getIdentifier(skins.getCurrentOSkin(), "drawable", getPackageName()));
            v.setTag("2");
            gameState[gameStatePointer] = 2;
        }
        roundCount++;

        if (CheckWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                UpdatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                currency.setCurrency(currency.getCurrency() + 10);
                Toast.makeText(this, "Player One received +10 currency !", Toast.LENGTH_SHORT).show();
                user.changeValueInDatabase("Wins");
            } else {
                playerTwoScoreCount++;
                UpdatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                user.changeValueInDatabase("Losts");
            }
            PlayAgain();
        } else if (roundCount == 9) {
            PlayAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
            user.changeValueInDatabase("Draws");
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