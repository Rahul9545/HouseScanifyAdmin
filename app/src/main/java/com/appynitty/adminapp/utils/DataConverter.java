package com.appynitty.adminapp.utils;

import androidx.room.TypeConverter;

import com.appynitty.adminapp.models.UserLocationDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

public class DataConverter implements Serializable {
    @TypeConverter
    public String fromUserLocationDto(UserLocationDTO userLocationDTO) {
        if (userLocationDTO == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<UserLocationDTO>() {
        }.getType();
        String json = gson.toJson(userLocationDTO, type);
        return json;
    }

    @TypeConverter
    public UserLocationDTO toUserLocationDto(String userLocationDtoString) {
        if (userLocationDtoString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<UserLocationDTO>() {
        }.getType();
        UserLocationDTO userLocationDTO = gson.fromJson(userLocationDtoString, type);
        return userLocationDTO;
    }
}
