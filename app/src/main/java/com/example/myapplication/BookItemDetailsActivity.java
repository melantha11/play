package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookItemDetailsActivity extends AppCompatActivity {
    private int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item_details);

        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            if(null != name){
                EditText editTextItemName = findViewById(R.id.edittext_item_name);
                position = intent.getIntExtra("position",-1);
                editTextItemName.setText(name);
            }
        }

        Button buttonOk = findViewById(R.id.button_item_details_ok);
        buttonOk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                EditText editTextItemName = findViewById(R.id.edittext_item_name);
                intent.putExtra("name",editTextItemName.getText().toString());
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent);
                BookItemDetailsActivity.this.finish();
            }
        });

        Button buttonCancel = findViewById(R.id.button_item_details_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BookItemDetailsActivity.this.finish();
            }
        });
    }
}