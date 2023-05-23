package com.appynitty.adminapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appynitty.adminapp.entities.OffLocation;

import java.util.List;

@Dao
public abstract class LocationsDao {

    @Insert
    public abstract void insert(OffLocation loc);

    @Update
    public abstract void update(OffLocation loc);

    @Delete
    public abstract void delete(OffLocation loc);

    @Query("DELETE FROM LOCATIONS_TABLE WHERE id = :locId")
    public abstract void deleteById(int locId);



    @Query("DELETE FROM LOCATIONS_TABLE")
    public abstract void deleteAllLocations();

    @Query("SELECT * FROM LOCATIONS_TABLE")
    public abstract LiveData<List<OffLocation>> getAllLocations();

    @Query("SELECT * FROM LOCATIONS_TABLE")
    public abstract List<OffLocation> getAllLocationsList();

    @Query("SELECT COUNT(id) FROM LOCATIONS_TABLE")
    public abstract int getCount();
}
