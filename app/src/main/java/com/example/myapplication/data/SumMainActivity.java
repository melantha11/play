package com.example.myapplication.data;

public class SumMainActivity {
    public static int sum = 40;

    public SumMainActivity(int sum_) {

        sum = sum_;
    }

    public int getSum() {  return sum;}

    public void setSum(int sum) {    SumMainActivity.sum = sum;}

}