package com.appynitty.adminapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.models.DutyDTO;
import com.appynitty.adminapp.repositories.DutyOnOffRepo;
import com.appynitty.adminapp.utils.MainUtils;
import com.pixplicity.easyprefs.library.Prefs;

public class DutyOnOffViewModel extends ViewModel {
    private static final String TAG = "DutyOnOffViewModel";
    DutyOnOffRepo dutyOnOffRepo = DutyOnOffRepo.getInstance();
    MutableLiveData<DutyDTO> dutyDTOMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> progressLiveData = new MutableLiveData<>();
    private Boolean status = false;
    MutableLiveData<Boolean> statusChk = new MutableLiveData<>();
    MutableLiveData<String> dutyErrorLiveData = new MutableLiveData<>();


    public void setAttendance(DutyDTO reqPacket) {
        progressLiveData.setValue(View.VISIBLE);
        dutyOnOffRepo.setDuty(reqPacket, new DutyOnOffRepo.IDutyResponse() {
            @Override
            public void onResponse(MutableLiveData<DutyDTO> dutyOnOffMutableLiveData) {
                Log.e(TAG, "onResponse: " + dutyOnOffMutableLiveData.getValue().getMessage());
                dutyDTOMutableLiveData.setValue(dutyOnOffMutableLiveData.getValue());
                progressLiveData.setValue(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                dutyErrorLiveData.setValue(t.getMessage());
                progressLiveData.setValue(View.GONE);
            }
        });
    }

    public void checkAttendance() {
        dutyOnOffRepo.checkAttendancefromServer(new DutyOnOffRepo.IDutyResponse() {
            @Override
            public void onResponse(MutableLiveData<DutyDTO> dutyOnOffMutableLiveData) {
                Log.e(TAG, "onResponse: isAttendanceOff: " + dutyOnOffMutableLiveData.getValue().getIsAttendanceOff());
                statusChk.setValue(dutyOnOffMutableLiveData.getValue().getIsAttendanceOff());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSwitch) {
            status = ((SwitchCompat) view).isChecked();
        }
    }

    public void changeDuty(Boolean b) {   /* Here we check if user wants to turn the duty or off. For ex: if it wants to turn of the duty the
        the value of b would be ture and vice versa */
        if (b) {
            if (Prefs.contains(MainUtils.LAT)) {
                DutyDTO dataPacketOn = new DutyDTO(Prefs.getString(MainUtils.LAT), Prefs.getString(MainUtils.LONG), "", "",
                        MainUtils.getTime(), MainUtils.getDate(), "", ""
                );
                setAttendance(dataPacketOn);
            }
        } else {
            if (Prefs.contains(MainUtils.LAT)) {
                DutyDTO dataPacketOff = new DutyDTO("", "", Prefs.getString(MainUtils.LAT), Prefs.getString(MainUtils.LONG),
                        "", "", MainUtils.getTime(), MainUtils.getDate()
                );
//                dutyDTOMutableLiveData.setValue(dataPacketOff);
                setAttendance(dataPacketOff);
            }
        }
    }

    public MutableLiveData<DutyDTO> getDutyDTOMutableLiveData() {
        return dutyDTOMutableLiveData;
    }

    public MutableLiveData<Boolean> getAttendanceChk() {
        return statusChk;
    }

    public MutableLiveData<String> getDutyErrorLiveData() {
        return dutyErrorLiveData;
    }

    public MutableLiveData<Integer> getProgressLiveData() {
        return progressLiveData;
    }
}
