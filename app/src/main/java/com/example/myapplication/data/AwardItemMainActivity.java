package com.example.myapplication.data;

public class AwardItemMainActivity {

    public String getName() {
        return name;
    }
    public int getCost() {  return cost;}
    public String getTimes() {  return times;}

    private String name;
    private int cost;
    private String times;

    public AwardItemMainActivity(String name_, String times_ ,int cost_) {

        this.name = name_;
        this.times = times_;
        this.cost = cost_;
    }
}
