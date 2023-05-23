package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.repositories.UserRoleRepository;

import java.util.List;

public class UserRoleViewModel extends ViewModel {

    private static final String TAG = "UserRoleViewModel";

    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<UserRoleModelDTO>> userRoleResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public UserRoleRepository userRoleRepository = UserRoleRepository.getInstance();
    public Boolean rdBtnActiveU = true;
    public Boolean rdBtnInactiveU = false;


     /*public UserRoleViewModel(){
        mProgressMutableData.postValue(View.VISIBLE);
         userRoleRepository.getUserRoleInactiveList(rdBtnActiveU, new UserRoleRepository.IUserRoleResponse() {
            @Override
            public void onResponse(MutableLiveData<List<UserRoleModelDTO>> userRoleResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                totalEntries.setValue(userRoleResponse.getValue().size());
                userRoleResponseLiveData.setValue(userRoleResponse.getValue());
                Log.e(TAG,"onResponse: " + userRoleResponse.getValue());

            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

     }*/



    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public MutableLiveData<List<UserRoleModelDTO>> getUserRoleActiveResponseLiveData() {
        if (userRoleResponseLiveData == null) {
            userRoleResponseLiveData = new MutableLiveData<>();
        }
        return userRoleResponseLiveData;
    }

    public MutableLiveData<List<UserRoleModelDTO>> getUserRoleResponseLiveDataInActive() {
        if (userRoleResponseLiveData == null) {
            userRoleResponseLiveData = new MutableLiveData<>();
        }
        return userRoleResponseLiveData;
    }
}
