package com.example.myapplication.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    final String DATA_FILENAME = "bookitems.data";

    public ArrayList<BookListMainActivity> LoadBookItems(Context context) {

        ArrayList<BookListMainActivity> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<BookListMainActivity>) objectIn.readObject();//对象流
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "Data loaded successfully.item count" + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void SaveBookItems(Context context, ArrayList<BookListMainActivity> bookitems) {
        try {
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bookitems);
            out.close();
            fileOut.close();
            Log.d("Serialization","Data is serialized and saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
