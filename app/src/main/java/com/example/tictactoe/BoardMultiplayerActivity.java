package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tictactoe.currency.Currency;
import com.example.tictactoe.database.User;
import com.example.tictactoe.installation.Installation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BoardMultiplayerActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private Installation installation = new Installation();
    String id = installation.id(this);
    private final ImageButton[] buttons = new ImageButton[9];
    private int winningPos = -1;
    boolean isThisPlayerOne;
    boolean isActivePlayer;

    Currency currency = new Currency();
    User user = new User();

    String player1Skin;
    String player2Skin;

    String  gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_board);
        AssignVariables();
        String gameCode = getIntent().getStringExtra("gameCode");
        getPlayerSkins(gameCode, "player1Skin");
        getPlayerSkins(gameCode, "player2Skin");

        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode + "/gameboard");
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
        getPlayerSkins(gameCode, "player1Skin");
        getPlayerSkins(gameCode, "player2Skin");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode);
        if (v.getTag() != "0") {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1));
        boolean hasBoardChanged=false;
        if (isThisPlayerOne && isActivePlayer) {
            ((ImageButton) v).setImageResource(getResources().getIdentifier(player1Skin, "drawable", getPackageName()));
            v.setTag("1");
            gameState = changeCharInPosition(gameStatePointer,'1',gameState);
            hasBoardChanged=true;
        } else if (!isThisPlayerOne && isActivePlayer) {
            ((ImageButton) v).setImageResource(getResources().getIdentifier(player2Skin, "drawable", getPackageName()));
            v.setTag("2");
            gameState = changeCharInPosition(gameStatePointer,'2',gameState);
            hasBoardChanged=true;
        }

        if(hasBoardChanged)
        {
            gamesRef.child("gameboard").setValue(gameState);
        }

        if (CheckWinner())
        {
            if (buttons[winningPos].getTag()=="1") {
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                if(isThisPlayerOne) {
                    currency.setCurrency(currency.getCurrency() + 10);
                    Toast.makeText(this, "You received +10 currency !", Toast.LENGTH_SHORT).show();
                    user.getPlayerID(gameCode, "player1", "Wins");
                    user.getPlayerID(gameCode, "player2", "Losts");
                }
                PlayAgain();
            } else {
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                if(!isThisPlayerOne) {
                    currency.setCurrency(currency.getCurrency() + 10);
                    Toast.makeText(this, "You received +10 currency !", Toast.LENGTH_SHORT).show();
                    user.getPlayerID(gameCode, "player1", "Losts");
                    user.getPlayerID(gameCode, "player2", "Wins");
                }
                PlayAgain();
            }
        }
        else if (!gameState.contains("0")) {
            user.getPlayerID(gameCode, "player1", "Draws");
            user.getPlayerID(gameCode, "player2", "Draws");
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
            PlayAgain();
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
                        gamesRef.child(gameCode).child("player1Skin").removeValue();
                    }
                }
                if (snapshot.child(gameCode).child("player2").getValue() != null) {
                    if (snapshot.child(gameCode).child("player2").getValue().toString().toLowerCase().equals(id.toLowerCase())) {
                        gamesRef.child(gameCode).child("player2").removeValue();
                        gamesRef.child(gameCode).child("player2Skin").removeValue();
                    }
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
            if (gameState.charAt(i) == '0') {
                buttons[i].setImageResource(0);
                buttons[i].setTag("0");
            } else if (gameState.charAt(i) == '1') {
                buttons[i].setImageResource(getResources().getIdentifier(player1Skin, "drawable", getPackageName()));
                buttons[i].setTag("1");
            } else if (gameState.charAt(i) == '2') {
                buttons[i].setImageResource(getResources().getIdentifier(player2Skin, "drawable", getPackageName()));
                buttons[i].setTag("2");
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
        // pierwszy rzad
        if(gameState.charAt(0)==gameState.charAt(1))
        {
            if(gameState.charAt(1)==gameState.charAt(2))
            {
                if(gameState.charAt(0)!='0') {
                    winningPos=0;
                    return true;
                }
            }
        }
        //drugi rzad
        if(gameState.charAt(3)==gameState.charAt(4))
        {

            if(gameState.charAt(4)==gameState.charAt(5))
            {
                if(gameState.charAt(3)!='0') {
                    winningPos=3;
                    return true;
                }
            }
        }
        // trzeci rzad
        if(gameState.charAt(6)==gameState.charAt(7))
        {
            if(gameState.charAt(7)==gameState.charAt(8))
            {
                if(gameState.charAt(6)!='0') {
                    winningPos=6;
                    return true;
                }
            }
        }
        // pierwsza kolumna
        if(gameState.charAt(0)==gameState.charAt(3))
        {
            if(gameState.charAt(3)==gameState.charAt(6))
            {
                if(gameState.charAt(0)!='0')
                {
                    winningPos=0;
                    return true;
                }
            }
        }
        // druga kolumna
        if(gameState.charAt(1)==gameState.charAt(4))
        {
            if(gameState.charAt(4)==gameState.charAt(7))
            {
                if(gameState.charAt(1)!='0')
                {
                    winningPos=1;
                    return true;
                }
            }
        }
        // trzecia kolumna
        if(gameState.charAt(2)==gameState.charAt(5))
        {
            if(gameState.charAt(5)==gameState.charAt(8))
            {
                if(gameState.charAt(2)!='0')
                {
                    winningPos=2;
                    return true;
                }
            }
        }
        // lewy gorny
        if(gameState.charAt(0)==gameState.charAt(4))
        {
            if(gameState.charAt(4)==gameState.charAt(8))
            {
                if(gameState.charAt(0)!='0')
                {
                    winningPos=0;
                    return true;
                }
            }
        }
        //lewy dolny
        if(gameState.charAt(2)==gameState.charAt(4))
        {
            if(gameState.charAt(4)==gameState.charAt(6))
            {
                if(gameState.charAt(2)!='0')
                {
                    winningPos=2;
                    return true;
                }
            }
        }
        return false;
    }

    public void PlayAgain(){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setTag("0");
        }
        gameState = "000000000";
        String gameCode = getIntent().getStringExtra("gameCode");
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games/" + gameCode+"/gameboard");
        gamesRef.setValue(gameState);
        UpdateBoardVisuals(gameState);
    }

    public String changeCharInPosition(int position, char ch, String str){
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }

    public void getPlayerSkins(String gameCode, String player){
        database.child("Games").child(gameCode).child(player).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                if (!String.valueOf(task.getResult().getValue()).equals("null")){
                    if (player.equals("player1Skin")) player1Skin = (String.valueOf(task.getResult().getValue()));
                    else if (player.equals("player2Skin")) player2Skin = (String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}