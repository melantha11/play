package com.example.myapplication;//包的名字

//引入的库

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//声明类
public class MainActivity extends AppCompatActivity {       //extend派生

    @Override//重载（父类函数在子类重新实现）
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //调用父类的方法
        setContentView(R.layout.activity_main);             //设置布局；R->res
    }
}