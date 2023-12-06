package com.example.myapplication.data;

import java.io.Serializable;

public class BookListMainActivity implements Serializable {
    public int getImageResourceId() {
        return imageResourceId;
    }

    private final int imageResourceId;

    public String getName() {
        return name;
    }

    private String name;

    public BookListMainActivity(String name_, int imageResourceId_) {
        this.name = name_;
        this.imageResourceId = imageResourceId_;
    }

    public void setName(String name) {
        this.name = name;
    }
}
