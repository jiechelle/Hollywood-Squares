package com.queens;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Player {

    private String username;
    private String password;
    private int current_score;
    private PrintWriter outWriter;

    ArrayList<Integer> high_score = new ArrayList<Integer>();

    public Player(String un, String pw, File scoreList) throws FileNotFoundException {

        outWriter = new PrintWriter(scoreList);
        setUserName(un);
        setPassword(pw);

    }

    public String getUserName() {

        return username;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String pw) {

        password = pw;

    }

    public void setUserName(String un) {

        username = un;

    }

    public void addHigh(int score) {

        high_score.add(score);

    }

    public void saveScore() {

        for (int i = 0; i < high_score.size(); i++) {
           // outWriter <<"a";
            //fix print to text file
          //  outWriter<<(high_score.get(i));
            System.out.println(high_score.get(i)); //WRITE TO TEXT FILE
        }

    }




}
