package com.android.example.sqldatabasebasic.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChildDao {

    @Insert
    void insert(Child child);

    @Update
    void update(Child child);

    @Delete
    void delete(Child child);

    @Query("DELETE FROM child_table")
    void deleteAllChildren();

    @Query("SELECT * FROM child_table ORDER BY number ASC")
    LiveData<List<Child>> getAllChildren();

}
