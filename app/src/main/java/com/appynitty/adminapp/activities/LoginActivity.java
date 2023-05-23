package com.appynitty.adminapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ActivityLoginBinding;
import com.appynitty.adminapp.models.LoginResult;
import com.appynitty.adminapp.models.LoginUserDTO;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.LoginViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    String reqStatus = "";
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        if (Prefs.getBoolean(MainUtils.IS_LOGIN)) {
//            if (MainUtils.EMP_TYPE.matches("A"))
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            /*else
                startActivity(new Intent(LoginActivity.this, AttendanceActivity.class));
*/
            finish();
        }
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);

        ctx = LoginActivity.this;

        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<LoginUserDTO>() {
            @Override
            public void onChanged(LoginUserDTO loginUser) {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getUserLoginId())) {
                    binding.etUserName.setError("Enter a username!");
                    binding.etUserName.requestFocus();
                } else if (!loginUser.isUserIdValid()) {
                    binding.etUserName.setError("Username must contain atleast 4 digits");
                    binding.etUserName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getUserPassword())) {
                    binding.etPassword.setError("Enter a password!");
                    binding.etPassword.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.etPassword.setError("password must contain atleast 4 digits");
                    binding.etPassword.requestFocus();
                } else {
                    Log.e(TAG, "onChanged: userName: " + loginUser.getUserLoginId() + " password: " + loginUser.getUserPassword());
                }
            }
        });

        loginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.progressBar.setVisibility(visibility);
            }
        });

        loginViewModel.getLoginResponse().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if (loginResult != null && loginResult.getStatus() != null) {
                    Log.e(TAG, "onChanged: status: " + loginResult.getUserId());
                    reqStatus = loginResult.getStatus();
                    if (reqStatus.equals(MainUtils.STATUS_SUCCESS)) {
                        MainUtils.success(loginResult.getMessage(), 0);
//                        if (loginResult.getEmpType().matches("A"))
                        startActivity(new Intent(ctx, DashboardActivity.class));
                       /* else
                            startActivity(new Intent(ctx, AttendanceActivity.class));
*/
                        Prefs.putString(MainUtils.EMP_TYPE, loginResult.getEmpType());
                        Prefs.putString(MainUtils.EMP_ID, String.valueOf(loginResult.getUserId()));
                        Prefs.putString(MainUtils.USER_ID, loginResult.getUserId().toString());
                        Prefs.putString(MainUtils.USER_NAME, loginResult.getUserName().toString());
                        Prefs.putBoolean(MainUtils.IS_LOGIN, true);
                        finish();
                    } else if (reqStatus.equals(MainUtils.STATUS_ERROR))
                        MainUtils.error(loginResult.getMessage(), 0);
                } else {
                    MainUtils.error("an error has occurred", 0);
                }
            }
        });
    }


}