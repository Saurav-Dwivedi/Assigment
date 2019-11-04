package com.example.myapplication.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    public static ArrayList<String> getArrayListFromColorString(String colors) {
        ArrayList<String> colorsList = new ArrayList<>();
        if (colors.contains(",")) {
            String[] color = colors.split(",");
            for (String data : color) {
                colorsList.add(data);
                Log.e("Saurav", data);
            }
        } else {
            colorsList.add(colors);
            Log.e("Saurav", colors);

        }
        return colorsList;
    }


    public static String setColorsFromList(ArrayList<String> colors) {
        StringBuilder builder = new StringBuilder();
        String colorData = "";
        if (!colors.isEmpty()) {
            int size = colors.size();
            for (int i = 0; i < size; i++) {
                String data = colors.get(i);
                if (data.contains("[")) {
                    colorData = data.replace("[", "");

                } else if (data.contains("]")) {
                    colorData = data.replace("]", "");
                } else {
                    colorData = data;
                }
                builder.append(colorData);
                if (i < size - 1) {
                    builder.append(",");
                }

            }
        }
        return builder.toString();
    }

    public static HashMap<String, ArrayList<String>> getStoreInformation(String stores, String address) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> addressList = getArrayListFromColorString(address);
        map.put(stores, addressList);
        return map;
    }


    public static String getStoreName(HashMap<String, ArrayList<String>> map) {
        String storeName = "";
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            storeName = entry.getKey();
        }
        return storeName;
    }

    public static String getStoreAddress(HashMap<String, ArrayList<String>> map) {
        ArrayList<String> storeAddress = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            storeAddress = entry.getValue();
        }
        return setColorsFromList(storeAddress);
    }


}
