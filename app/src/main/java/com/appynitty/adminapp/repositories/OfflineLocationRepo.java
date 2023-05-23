package com.appynitty.adminapp.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.appynitty.adminapp.daos.LocationsDao;
import com.appynitty.adminapp.db.AppDatabase;
import com.appynitty.adminapp.entities.OffLocation;
import com.appynitty.adminapp.utils.MainUtils;

import java.util.List;

public class OfflineLocationRepo {
    LocationsDao locationsDao;
    LiveData<List<OffLocation>> allLocations;
    List<OffLocation> offlineLocatnList;

    public OfflineLocationRepo() {
        AppDatabase db = AppDatabase.getInstance(MainUtils.mainApplicationConstant);
        locationsDao = db.locationsDao();
        allLocations = locationsDao.getAllLocations();
        offlineLocatnList = locationsDao.getAllLocationsList();
    }

    public void insertLocation(OffLocation location) {
        new InsertLocationAsyncTask(locationsDao).execute(location);
    }

    public void updateLocation(OffLocation location) {
        new UpdateLocationsAsyncTask(locationsDao).execute(location);
    }

    public void deleteLocation(OffLocation location) {
        new DeleteLocationsAsyncTask(locationsDao).execute(location);
    }

    public void deleteLocationById(int mId) {
        locationsDao.deleteById(mId);
    }


    public void deleteAllLocations() {
        new DeleteAllLocAsyncTask(locationsDao).execute();
    }

    public LiveData<List<OffLocation>> getAllLocations() {
        return allLocations;
    }

    public List<OffLocation> getOfflineLocatnList() {
        return offlineLocatnList;
    }

    private static class InsertLocationAsyncTask extends AsyncTask<OffLocation, Void, Void> {
        private LocationsDao locationsDao;

        public InsertLocationAsyncTask(LocationsDao locationsDao) {
            this.locationsDao = locationsDao;
        }

        @Override
        protected Void doInBackground(OffLocation... locations) {
            locationsDao.insert(locations[0]);
            return null;
        }
    }

    private static class UpdateLocationsAsyncTask extends AsyncTask<OffLocation, Void, Void> {
        private LocationsDao locationsDao;

        public UpdateLocationsAsyncTask(LocationsDao locationsDao) {
            this.locationsDao = locationsDao;
        }

        @Override
        protected Void doInBackground(OffLocation... locations) {
            locationsDao.update(locations[0]);
            return null;
        }
    }

    private static class DeleteLocationsAsyncTask extends AsyncTask<OffLocation, Void, Void> {
        LocationsDao locationsDao;

        public DeleteLocationsAsyncTask(LocationsDao locationsDao) {
            this.locationsDao = locationsDao;
        }

        @Override
        protected Void doInBackground(OffLocation... locations) {
            locationsDao.delete(locations[0]);
            return null;
        }
    }

    /*private static class DeleteLocationByIdAsyncTask extends AsyncTask<OffLocation, Void, Void> {
        LocationsDao locationsDao;
        int sId;

        public DeleteLocationByIdAsyncTask(LocationsDao locationsDao) {
            this.locationsDao = locationsDao;
        }

        @Override
        protected Void doInBackground(OffLocation... offLocations) {
            locationsDao.deleteById();
            return null;
        }
    }*/

    private static class DeleteAllLocAsyncTask extends AsyncTask<OffLocation, Void, Void> {
        LocationsDao locationsDao;

        public DeleteAllLocAsyncTask(LocationsDao locationsDao) {
            this.locationsDao = locationsDao;
        }

        @Override
        protected Void doInBackground(OffLocation... locations) {
            locationsDao.deleteAllLocations();
            return null;
        }
    }


}
