package com.android.example.sqldatabasebasic.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parent_table")
public class Parent {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int number;

    public Parent(String title, String description, int number) {
        this.title = title;
        this.description = description;
        this.number = number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }
}
