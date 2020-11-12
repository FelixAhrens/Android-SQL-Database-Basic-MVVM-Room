package com.android.example.sqldatabasebasic.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ParentDao {

    @Insert
    void insert(Parent parent);

    @Update
    void update(Parent parent);

    @Delete
    void delete(Parent parent);

    @Query("DELETE FROM parent_table")
    void deleteAllParents();

    @Query("SELECT * FROM parent_table ORDER BY number DESC")
    LiveData<List<Parent>> getAllParents();

}
