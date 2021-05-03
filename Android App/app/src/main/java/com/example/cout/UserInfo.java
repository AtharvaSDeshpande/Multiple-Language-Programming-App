package com.example.cout;

public class UserInfo {

    String name,emailId;
    int score;
    public UserInfo(String name,int score)
    {
        this.name = name;
        this.score = score;
    }

    public int getScore(){return score;}
    public String getName(){return this.name;}
    public void display()
    {
        System.out.println("name: "+this.name+" Score:"+this.score);
    }
}
