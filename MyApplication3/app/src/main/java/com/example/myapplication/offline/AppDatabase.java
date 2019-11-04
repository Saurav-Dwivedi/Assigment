package com.example.myapplication.offline;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.Utils.Converters;
import com.example.myapplication.Utils.StoreConverters;
import com.example.myapplication.model.Model;
import com.example.myapplication.offline.dao.ProductDao;

@Database(entities = {Model.class}, version = 2 ,exportSchema = false)
@TypeConverters({Converters.class, StoreConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "personlist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d("AppDataBase", "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d("AppDataBase", "Getting the database instance");
        return sInstance;
    }


    public abstract ProductDao taskDao();

}