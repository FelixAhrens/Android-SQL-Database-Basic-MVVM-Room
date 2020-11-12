package com.android.example.sqldatabasebasic.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "child_table")
public class Child {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private int number;
    private int parentId;

    public Child(String title, int number, int parentId) {
        this.title = title;
        this.number = number;
        this.parentId = parentId;
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

    public int getNumber() {
        return number;
    }

    public int getParentId() {
        return parentId;
    }
}
