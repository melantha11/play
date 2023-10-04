package com.example.myapplication;//包的名字

//引入的库

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;

//声明类
public class MainActivity extends AppCompatActivity {       //extend派生

    private TextView textView1;
    private TextView textView2;
    private Button changeTextButton;

    @Override//重载（父类函数在子类重新实现）
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //调用父类的方法
        setContentView(R.layout.activity_main);             //设置布局；R->res


        // 找到TextView
        TextView textView = findViewById(R.id.text_vciew_hellow_world);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        changeTextButton = findViewById(R.id.button);

        // 从字符串资源中获取文本内容并设置到TextView
        String helloText = getResources().getString(R.string.hello_android);
        textView.setText(helloText);

        changeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 交换文本
                String tempText = textView1.getText().toString();
                textView1.setText(textView2.getText());
                textView2.setText(tempText);

                // 显示Toast
                Toast.makeText(MainActivity.this, "交换成功", Toast.LENGTH_SHORT).show();

                // 显示AlertDialog
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("交换成功")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击OK按钮的操作，如果不需要可以为空
                            }
                        });
            }
        });
    }


    public void changeText(View view) {
        // 交换TextView1和TextView2的文本
        String tempText = textView1.getText().toString();
        textView1.setText(textView2.getText());
        textView2.setText(tempText);

        // 显示Toast
        Toast.makeText(this, "交换成功", Toast.LENGTH_SHORT).show();

        // 显示AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("交换成功");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
