package com.appynitty.adminapp.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.adminapp.viewmodels.AttendanceViewModel;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    //    private Application mApplication;
    private final Object[] mParams;

    public MyViewModelFactory(/*Application application,*/ Object... params) {
//        mApplication = application;
        mParams = params;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        /*if (modelClass == UlbDataViewModel.class) {
            return (T) new UlbDataViewModel(*//*mApplication, *//*mParams);
        }else*/
        if (modelClass == AttendanceViewModel.class) {
            return (T) new AttendanceViewModel((String) mParams[0]);
        }/*else if (modelClass == EmpDViewModel.class){
            return (T) new EmpDViewModel((String) mParams[0]);
        }*/
        /*else if (modelClass == ViewModel2.class) {
            return (T) new ViewModel2(mApplication, (Integer) mParams[0]);
        } else if (modelClass == ViewModel3.class) {
            return (T) new ViewModel3(mApplication, (Integer) mParams[0], (String) mParams[1]);
        }*/
        else {
            return super.create(modelClass);
        }
    }
}
