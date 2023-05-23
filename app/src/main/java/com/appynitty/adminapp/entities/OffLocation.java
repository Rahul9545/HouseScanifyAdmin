package com.appynitty.adminapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.appynitty.adminapp.models.UserLocationDTO;
import com.appynitty.adminapp.utils.DataConverter;

import java.io.Serializable;

@Entity(tableName = "locations_table")
@TypeConverters(DataConverter.class)
public class OffLocation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private UserLocationDTO locationObj;

    private String date;

    public OffLocation(UserLocationDTO locationObj, String date) {
        this.locationObj = locationObj;
        this.date = date;
    }

    public OffLocation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserLocationDTO getLocationObj() {
        return locationObj;
    }

    public void setLocationObj(UserLocationDTO locationObj) {
        this.locationObj = locationObj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
