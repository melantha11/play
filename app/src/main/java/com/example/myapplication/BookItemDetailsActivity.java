package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BookItemDetailsActivity extends AppCompatActivity {
    private Spinner spinner;
    private int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item_details);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                    new String[]{"每日任务", "每周任务", "普通任务"});

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) parent.getItemAtPosition(position);
                Toast.makeText(BookItemDetailsActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            int score = intent.getIntExtra("score",0);
            if(null != name ){
                EditText editTextItemName = findViewById(R.id.edittext_item_name);
                //EditText editTextItemScore= findViewById(R.id.edittext_item_score);
                position = intent.getIntExtra("position",-1);
                editTextItemName.setText(name);
                //editTextItemScore.setText(score);
            }
        }
        //&& null != score

        Button buttonOk = findViewById(R.id.button_item_details_ok);
        buttonOk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                EditText editTextItemName = findViewById(R.id.edittext_item_name);
                EditText editTextItemScore = findViewById(R.id.edittext_item_score);
                intent.putExtra("name",editTextItemName.getText().toString());
                intent.putExtra("score",editTextItemScore.getText().toString());
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