package com.example.myapplication.offline.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Model;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Model")
    List<Model> getAll();

    @Insert
    void insert(Model task);

    @Delete
    void delete(Model task);

    @Update
    void update(Model task);
}
