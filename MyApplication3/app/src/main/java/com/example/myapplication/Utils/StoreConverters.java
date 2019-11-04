package com.example.myapplication.Utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class StoreConverters {
    @TypeConverter
    public static HashMap<String, ArrayList<String>> fromStringToMap(String value) {
        Type listType = new TypeToken<HashMap<String, ArrayList<String>>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromMapToString(HashMap<String, ArrayList<String>> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }
}
