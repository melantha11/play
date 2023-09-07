package com.example.myapplication;//包的名字

//引入的库

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;



import android.os.Bundle;

//声明类
public class MainActivity extends AppCompatActivity {       //extend派生

    @Override//重载（父类函数在子类重新实现）
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //调用父类的方法
        setContentView(R.layout.activity_main);             //设置布局；R->res


                // 找到TextView
                TextView textView = findViewById(R.id.text_vciew_hellow_world);

                // 从字符串资源中获取文本内容并设置到TextView
                String helloText = getResources().getString(R.string.hello_android);
                textView.setText(helloText);
            }
        }
