package com.example.myapplication.data;

import java.io.Serializable;

public class BookListMainActivity implements Serializable {




    public String getName() {
        return name;
    }
    public int getScore() {  return score;}
    public String getTimes() {  return times;}

    private String name;
    private int score;
    private String times;

    public BookListMainActivity(String name_,int score_,String times_) {

        this.name = name_;
        this.score = score_;
        this.times = times_;
    }

    public void setName(String name) {  this.name = name;}

    public void setScore(int score) {    this.score =  score;}

    public void setTimes(String times) {  this.times = times;}


}

