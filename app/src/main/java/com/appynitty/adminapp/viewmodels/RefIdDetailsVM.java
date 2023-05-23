package com.appynitty.adminapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.ReferenceIdDetailsDTO;
import com.appynitty.adminapp.repositories.ReferenceIdDetailsRepo;

import java.util.List;

public class RefIdDetailsVM extends ViewModel {
    private static final String TAG = "RefIdDetailsVM";
    MutableLiveData<List<ReferenceIdDetailsDTO>> referenceIdDetailsLiveData = new MutableLiveData<>();
    MutableLiveData<Throwable> refIdDetailsErrorLiveData = new MutableLiveData<>();
    ReferenceIdDetailsRepo repo = ReferenceIdDetailsRepo.getInstance();

    public void getRefIdDetails(String refId) {
        repo.getReferenceIdDetails(refId, new ReferenceIdDetailsRepo.IRefIdDetailsResponse() {
            @Override
            public void onResponse(List<ReferenceIdDetailsDTO> refIdDetailsLive) {
                referenceIdDetailsLiveData.setValue(refIdDetailsLive);
            }

            @Override
            public void onFailure(Throwable errorLivedata) {
                refIdDetailsErrorLiveData.setValue(errorLivedata);
            }
        });
    }

    public MutableLiveData<List<ReferenceIdDetailsDTO>> getReferenceIdDetailsLiveData() {
        return referenceIdDetailsLiveData;
    }

    public MutableLiveData<Throwable> getRefIdDetailsErrorLiveData() {
        return refIdDetailsErrorLiveData;
    }
}
