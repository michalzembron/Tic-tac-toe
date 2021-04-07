package com.example.tictactoe.database;

public class User {

    private String name, pushId;
    public int wins;
    public int losts;
    public int draws;

    public void setWins(int wins){
        this.wins = wins;
    }

    public void setLosts(int losts){
        this.losts = losts;
    }

    public void setDraws(int draws){
        this.draws = draws;
    }

    public int getWins(){
        return wins;
    }

    public int getLosts() {
        return losts;
    }

    public int getDraws() {
        return draws;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
