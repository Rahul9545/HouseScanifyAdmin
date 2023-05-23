package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUserDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.LoginWebService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private static final String TAG = "LoginRepository";
    LoginUserDTO loginUser = new LoginUserDTO();

    public void loginRemote(MutableLiveData<LoginUserDTO> loginBody, ILoginResponse iLoginResponse) {
        LoginWebService loginService = RetrofitClient.createService(LoginWebService.class, MainUtils.BASE_URL);
        loginUser.setUserLoginId(Objects.requireNonNull(loginBody.getValue()).getUserLoginId().trim());
        loginUser.setUserPassword(loginBody.getValue().getUserPassword().trim());
        Call<LoginResult> initiateLogin = loginService.saveLoginDetails(MainUtils.CONTENT_TYPE, MainUtils.EMP_TYPE_ADMIN, loginUser);


        initiateLogin.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(@NonNull Call<LoginResult> call, @NonNull Response<LoginResult> response) {
//                assert response.body() != null;
                if (response.code() == 200) {
                    iLoginResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                } else if (response.code() == 500) {
                    iLoginResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResult> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iLoginResponse.onFailure(t);
            }


        });

    }

    public interface ILoginResponse {
        void onResponse(LoginResult loginResponse);

        void onFailure(Throwable t);
    }
}
