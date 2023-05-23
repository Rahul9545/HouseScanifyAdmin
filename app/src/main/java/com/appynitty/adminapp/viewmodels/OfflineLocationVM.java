package com.appynitty.adminapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.entities.OffLocation;
import com.appynitty.adminapp.repositories.OfflineLocationRepo;

import java.util.List;

public class OfflineLocationVM extends ViewModel {
    private OfflineLocationRepo offlineLocationRepo;
    private LiveData<List<OffLocation>> allLocationsLiveData;
    private MutableLiveData<Integer> offlineLocationsCount = new MutableLiveData<>();

    public void init() {
        offlineLocationRepo = new OfflineLocationRepo();
        allLocationsLiveData = offlineLocationRepo.getAllLocations();
    }

    public void insert(OffLocation location) {
        offlineLocationRepo.insertLocation(location);
    }

    public void update(OffLocation location) {
        offlineLocationRepo.updateLocation(location);
    }

    public void delete(OffLocation location) {
        offlineLocationRepo.deleteLocation(location);
    }

    public void deleteAllLocation() {
        offlineLocationRepo.deleteAllLocations();
    }

    public LiveData<List<OffLocation>> getAllLocationsLiveData() {
        return allLocationsLiveData;
    }

    public MutableLiveData<Integer> getOfflineLocationsCount() {
        return offlineLocationsCount;
    }
}
