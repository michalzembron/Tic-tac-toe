package com.example.tictactoe.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {

    public DatabaseReference ref;
    public FirebaseDatabase database;

    public Connection(){
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getDatabaseReference(String url){
        return ref = database.getReference(url);
    }
}
