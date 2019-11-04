package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.Utils.Converters;
import com.example.myapplication.Utils.StoreConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Entity
public class Model implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "regular_price")
    private int regular_price;

    @ColumnInfo(name = "sale_price")
    private int sale_price;

    @TypeConverters(Converters.class)
    private ArrayList<String> colors = new ArrayList<>();

    @TypeConverters(StoreConverters.class)
    private HashMap<String, ArrayList<String>> stores = new HashMap<>();

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @Ignore
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(int regular_price) {
        this.regular_price = regular_price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public HashMap<String, ArrayList<String>> getStores() {
        return stores;
    }

    public void setStores(HashMap<String, ArrayList<String>> stores) {
        this.stores = stores;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", regular_price=" + regular_price +
                ", sale_price=" + sale_price +
                ", colors='" + colors + '\'' +
                ", stores='" + stores + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}

