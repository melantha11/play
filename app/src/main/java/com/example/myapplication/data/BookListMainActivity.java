package com.example.myapplication.data;

public class BookListMainActivity {
    public int getImageResourceId() {
        return imageResourceId;
    }

    private final int imageResourceId;

    public String getName() {
        return name;
    }

    private final String name;

    public BookListMainActivity(String name_, int imageResourceId_) {
        this.name = name_;
        this.imageResourceId = imageResourceId_;
    }
}
