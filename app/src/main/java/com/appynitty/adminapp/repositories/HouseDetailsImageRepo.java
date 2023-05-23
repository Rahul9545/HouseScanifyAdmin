package com.appynitty.adminapp.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.networking.RetrofitClient;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.webservices.QRImageDataWebservice;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseDetailsImageRepo {

    private static final String TAG = "HouoseDetailsImageRepo";
    private static final HouseDetailsImageRepo instance = new HouseDetailsImageRepo();
    String date = MainUtils.getDate();
    String appId, userId;
    private final MutableLiveData<List<HouseDetailsImageDTO>> HouseQrImagesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<HouseDetailsImageDTO>> DumpYardQrImagesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<HouseDetailsImageDTO>> LiquidQrImagesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<HouseDetailsImageDTO>> StreetQrImagesLiveData = new MutableLiveData<>();

    public static HouseDetailsImageRepo getInstance() {
        return instance;
    }

    public void getHouseQrImageData(String userId, IQrImageResponse listener) {
        appId = Prefs.getString(MainUtils.APP_ID);
        this.userId = userId;
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getHouseQrImages(MainUtils.CONTENT_TYPE, date, date, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
                    HouseQrImagesLiveData.setValue(response.body());
                    listener.onResponse(HouseQrImagesLiveData);
                    Log.e(TAG, "onResponse: in getHouseQrImageData");
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t);
            }
        });
    }

    public void getDumpyQrImageData(IQrImageResponse listener) {
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getDumpYardQrImages(MainUtils.CONTENT_TYPE, date, date, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
//                    Log.e(TAG, "onResponse: " + response.body().toString());
                    DumpYardQrImagesLiveData.setValue(response.body());
                    listener.onResponse(DumpYardQrImagesLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t);
            }
        });
    }

    public void getStreetQrImageData(IQrImageResponse listener) {
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getStreetDetailsQrImages(MainUtils.CONTENT_TYPE, date, date, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: " + response.body().toString());
                    StreetQrImagesLiveData.setValue(response.body());
                    listener.onResponse(StreetQrImagesLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                listener.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getLiquidQrImageData(IQrImageResponse listener) {
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getLiquidDetailsQrImages(MainUtils.CONTENT_TYPE, date, date, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
//                    Log.e(TAG, "onResponse: " + response.body().toString());
                    LiquidQrImagesLiveData.setValue(response.body());
                    listener.onResponse(LiquidQrImagesLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t);
            }
        });
    }

    public void getFilteredHouseDetails(String frmDate, String toDate, String appIdq, String userId, IQrImageResponse listener) {
        Log.e(TAG, "getFilteredHouseDetails: frmDate: " + frmDate + ", toDate: " + toDate + ", appId: " + appIdq + ", userId: " + userId);
        appId = Prefs.getString(MainUtils.APP_ID);
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getHouseQrImages(MainUtils.CONTENT_TYPE, frmDate, toDate, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
                    HouseQrImagesLiveData.setValue(response.body());
                    listener.onResponse(HouseQrImagesLiveData);
//                    Log.e(TAG, "getFilteredHouseDetails: " + response.body().get(response.body().size() - 1).getQRStatus());
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t);
            }
        });
    }

    public void getFilteredDumpYardDetails(String frmDate, String toDate, String appId1, String userId, IQrImageResponse listener) {
        Log.e(TAG, "getFilteredHouseDetails: frmDate: " + frmDate + ", toDate: " + toDate + ", appId: " + appId1 + ", userId: " + userId);
        appId = Prefs.getString(MainUtils.APP_ID);
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getDumpYardQrImages(MainUtils.CONTENT_TYPE, frmDate, toDate, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
//                    Log.e(TAG, "onResponse: " + response.body().toString());
                    DumpYardQrImagesLiveData.setValue(response.body());
                    listener.onResponse(DumpYardQrImagesLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t);
            }
        });
    }

    public void getFilteredLiquidDetails(String frmDate, String toDate, String appId1, String userId, IQrImageResponse listener) {
        Log.e(TAG, "getFilteredHouseDetails: frmDate: " + frmDate + ", toDate: " + toDate + ", appId: " + appId1 + ", userId: " + userId);
        appId = Prefs.getString(MainUtils.APP_ID);
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getLiquidDetailsQrImages(MainUtils.CONTENT_TYPE, frmDate, toDate, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
//                    Log.e(TAG, "onResponse: " + response.body().toString());
                    LiquidQrImagesLiveData.setValue(response.body());
                    listener.onResponse(LiquidQrImagesLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t);
            }
        });
    }

    public void getFilteredStreetDetails(String frmDate, String toDate, String appId1, String userId, IQrImageResponse listener) {
        Log.e(TAG, "getFilteredHouseDetails: frmDate: " + frmDate + ", toDate: " + toDate + ", appId: " + appId1 + ", userId: " + userId);
        appId = Prefs.getString(MainUtils.APP_ID);
        QRImageDataWebservice qrImageDataWebservice = RetrofitClient.createService(QRImageDataWebservice.class, MainUtils.BASE_URL);
        Call<List<HouseDetailsImageDTO>> qrImageDataCall = qrImageDataWebservice.getStreetDetailsQrImages(MainUtils.CONTENT_TYPE, frmDate, toDate, appId, userId, "Y");
        qrImageDataCall.enqueue(new Callback<List<HouseDetailsImageDTO>>() {
            @Override
            public void onResponse(Call<List<HouseDetailsImageDTO>> call, Response<List<HouseDetailsImageDTO>> response) {
                if (response.code() == 200) {
//                    Log.e(TAG, "onResponse: " + response.body().toString());
                    StreetQrImagesLiveData.setValue(response.body());
                    listener.onResponse(StreetQrImagesLiveData);
                } else if (response.code() == 500) {
                    Log.e(TAG, "onResponse: Internal Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<HouseDetailsImageDTO>> call, Throwable t) {
                listener.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface IQrImageResponse {
        void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData);

        void onFailure(Throwable s);
    }

}
