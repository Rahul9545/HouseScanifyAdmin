package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.EmpDModelDTO;
import com.appynitty.adminapp.repositories.EmpDRepository;

import java.util.List;

public class EmpDViewModel extends ViewModel {
    private static final String TAG = "EmpDViewModel";

    public MutableLiveData<Integer> totalEntries = new MutableLiveData<>();
    public MutableLiveData<List<EmpDModelDTO>> empDResponseLiveData;
    public MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public EmpDRepository empDRepository = EmpDRepository.getInstance();
    public Boolean rdBtnActive = true;
    public Boolean rdBtnInactive = false;



   /* public EmpDViewModel(String appId){
        mProgressMutableData.postValue(View.VISIBLE);
        empDRepository.getEmpDList(rdBtnActive, appId, new EmpDRepository.IEmpDResponse() {
            @Override
            public void onResponse(MutableLiveData<List<EmpDModelDTO>> empDResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                totalEntries.setValue(empDResponse.getValue().size());
                empDResponseLiveData.setValue(empDResponse.getValue());
                Log.e(TAG,"onResponse: " + empDResponse.getValue());

            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.setValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        }*/
    public void onSplitTypeChanged(RadioGroup radioGroup, int id) {
        radioGroup.getId();
        switch (id){
            case R.id.rd_active_ED:
                rdBtnActive = (radioGroup).callOnClick();
                rdBtnActive = true;
                Log.e(TAG, "rdActiveSP: " + rdBtnActive);
                break;
            case R.id.rd_inactive_ED:
                rdBtnInactive = (radioGroup).callOnClick();
                 rdBtnActive = true;
                Log.e(TAG, "rdInactiveSp: " + rdBtnInactive);
                break;

            default:
        }
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public MutableLiveData<List<EmpDModelDTO>> getEmpDResponseLiveData() {
        if (empDResponseLiveData == null) {
            empDResponseLiveData = new MutableLiveData<>();
        }
        return empDResponseLiveData;
    }

    public MutableLiveData<List<EmpDModelDTO>> getEmpDResponseLiveDataInActive() {
        if (empDResponseLiveData == null) {
            empDResponseLiveData = new MutableLiveData<>();
        }
        return empDResponseLiveData;
    }

}
