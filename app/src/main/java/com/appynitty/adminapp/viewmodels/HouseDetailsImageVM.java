package com.appynitty.adminapp.viewmodels;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.repositories.HouseDetailsImageRepo;

import java.util.List;

public class HouseDetailsImageVM extends ViewModel {
    private static final String TAG = "HouseDatailsImageVM";
    private int sImgCount = 0;
    public HouseDetailsImageRepo houseDetailsImageRepo = HouseDetailsImageRepo.getInstance();
    public MutableLiveData<Integer> mProgressLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> imageCountLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> houseQrImagesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> dumpyQrImagesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> streetQrImagesLiveData = new MutableLiveData<>();
    public MutableLiveData<List<HouseDetailsImageDTO>> liquidQrImagesLiveData = new MutableLiveData<>();

    /*public HouseDetailsImageVM() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getHouseQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                houseQrImagesLiveData.setValue(QrImageLiveData.getValue());
                for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                ) {
                    if (house.getqRCodeImage() != null) {
                        sImgCount += 1;

                    }
                }
                imageCountLiveData.setValue(sImgCount);
                Log.e(TAG, "onResponse: ImageCount " + sImgCount);
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });

    }*/

    public void callHouseApi(String empId) {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getHouseQrImageData(empId, new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                Log.e(TAG, "onResponse: houselist size: " + QrImageLiveData.getValue().size());
                mProgressLiveData.postValue(View.INVISIBLE);
                houseQrImagesLiveData.setValue(QrImageLiveData.getValue());
                for (HouseDetailsImageDTO house : QrImageLiveData.getValue()) {
                    if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                        sImgCount += 1;

                    }
                }
//                imageCountLiveData.setValue(sImgCount);
                imageCountLiveData.setValue(QrImageLiveData.getValue().size());
                Log.e(TAG, "onResponse: ImageCount " + sImgCount);
            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void callDumpYardApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getDumpyQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                if (QrImageLiveData.getValue().size() == 0) {
                    imageCountLiveData.setValue(0);
                    dumpyQrImagesLiveData.setValue(QrImageLiveData.getValue());
                } else {
                    dumpyQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                    ) {
                        if (house.getQRCodeImage() != null) {
                            if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                                sImgCount += 1;

                            }
                        }
                    }
                    imageCountLiveData.setValue(sImgCount);
                    Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                }

            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void callLiquidWasteApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getLiquidQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                if (QrImageLiveData.getValue().size() == 0) {
                    imageCountLiveData.setValue(0);
                    liquidQrImagesLiveData.setValue(QrImageLiveData.getValue());
                } else {
                    liquidQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                    ) {
                        if (house.getQRCodeImage() != null) {
                            if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                                sImgCount += 1;

                            }
                        }
                    }
                    imageCountLiveData.setValue(sImgCount);
                    Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                }

            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void callStreetWasteApi() {
        mProgressLiveData.postValue(View.VISIBLE);
        houseDetailsImageRepo.getStreetQrImageData(new HouseDetailsImageRepo.IQrImageResponse() {
            @Override
            public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                mProgressLiveData.postValue(View.INVISIBLE);
                streetQrImagesLiveData.setValue(QrImageLiveData.getValue());
                imageCountLiveData.setValue(QrImageLiveData.getValue().size());
                Log.e(TAG, "onResponse: ImageCount " + QrImageLiveData.getValue().size());
                /*if (QrImageLiveData.getValue().size() == 0) {
                    imageCountLiveData.setValue(0);
                    streetQrImagesLiveData.setValue(QrImageLiveData.getValue());
                } else {
                    streetQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    *//*for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                    ) {
                        if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                            sImgCount += 1;

                        }
                    }*//*
                    imageCountLiveData.setValue(QrImageLiveData.getValue().size());
                    Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                }*/

            }

            @Override
            public void onFailure(Throwable s) {
                mProgressLiveData.postValue(View.INVISIBLE);
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    public void setFilterData(Bundle filterData) {
        Log.e(TAG, "setFilterData: frmDate: " + filterData.get("frmDate")
                + " toDate: " + filterData.get("toDate")
                + " appId: " + filterData.get("appId")
                + "filter Type: " + filterData.get("filterType")
                + " userId: " + filterData.get("userId"));

        String frmDate = filterData.get("frmDate").toString();
        String userId = filterData.get("userId").toString();
        String appId = filterData.get("appId").toString();
        String filterType = filterData.getString("filterType");

        if (filterType.matches("HW")) {
            mProgressLiveData.postValue(View.VISIBLE);
            houseDetailsImageRepo.getFilteredHouseDetails(frmDate, frmDate, appId, userId, new HouseDetailsImageRepo.IQrImageResponse() {
                @Override
                public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                    /*if (!QrImageLiveData.getValue().isEmpty())
                        Log.e(TAG, " FilteredHouseDetails: HouseId:- " + QrImageLiveData.getValue().get(0).getReferanceId());*/
                    Log.e(TAG, "onResponse: houselist size: " + QrImageLiveData.getValue().size());
                    mProgressLiveData.postValue(View.INVISIBLE);
                    houseQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                    ) {
                        if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                            sImgCount += 1;

                        }
                    }
//                imageCountLiveData.setValue(sImgCount);
                    imageCountLiveData.setValue(QrImageLiveData.getValue().size());
                    Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                }

                @Override
                public void onFailure(Throwable s) {
                    Log.e(TAG, "onFailure: " + s.getMessage());
                }
            });
        } else if (filterType.matches("DY")) {
            mProgressLiveData.postValue(View.VISIBLE);
            houseDetailsImageRepo.getFilteredDumpYardDetails(frmDate, frmDate, appId, userId, new HouseDetailsImageRepo.IQrImageResponse() {
                @Override
                public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                    /*if (!QrImageLiveData.getValue().isEmpty())
                        Log.e(TAG, "onResponse: FilteredDumpYardDetails" + QrImageLiveData.getValue().get(0).getReferanceId());*/
                    mProgressLiveData.postValue(View.INVISIBLE);
                    if (QrImageLiveData.getValue().size() == 0) {
                        imageCountLiveData.setValue(0);
                        dumpyQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    } else {
                        dumpyQrImagesLiveData.setValue(QrImageLiveData.getValue());
                        for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                        ) {
                            if (house.getQRCodeImage() != null) {
                                if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                                    sImgCount += 1;

                                }
                            }
                        }
                        imageCountLiveData.setValue(sImgCount);
                        Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                    }
                }

                @Override
                public void onFailure(Throwable s) {
                    Log.e(TAG, "onFailure: " + s.getMessage());
                }
            });
        } else if (filterType.matches("LW")) {
            mProgressLiveData.postValue(View.VISIBLE);
            houseDetailsImageRepo.getFilteredLiquidDetails(frmDate, frmDate, appId, userId, new HouseDetailsImageRepo.IQrImageResponse() {

                @Override
                public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                    /*if (!QrImageLiveData.getValue().isEmpty())
                        Log.e(TAG, "onResponse: FilteredLiquidDetails" + QrImageLiveData.getValue().get(0).getReferanceId());*/
                    mProgressLiveData.postValue(View.INVISIBLE);
                    if (QrImageLiveData.getValue().size() == 0) {
                        imageCountLiveData.setValue(0);
                        liquidQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    } else {
                        liquidQrImagesLiveData.setValue(QrImageLiveData.getValue());
                        for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                        ) {
                            if (house.getQRCodeImage() != null) {
                                if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                                    sImgCount += 1;

                                }
                            }
                        }
                        imageCountLiveData.setValue(sImgCount);
                        Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                    }
                }

                @Override
                public void onFailure(Throwable s) {
                    Log.e(TAG, "onFailure: " + s.getMessage());
                }
            });
        } else if (filterType.matches("SW")) {
            mProgressLiveData.postValue(View.VISIBLE);
            houseDetailsImageRepo.getFilteredStreetDetails(frmDate, frmDate, appId, userId, new HouseDetailsImageRepo.IQrImageResponse() {
                @Override
                public void onResponse(MutableLiveData<List<HouseDetailsImageDTO>> QrImageLiveData) {
                    /*if (!QrImageLiveData.getValue().isEmpty())
                        Log.e(TAG, "onResponse: FilteredStreetDetails" + QrImageLiveData.getValue().get(0).getReferanceId());*/
                    mProgressLiveData.postValue(View.INVISIBLE);
                    if (QrImageLiveData.getValue().size() == 0) {
                        imageCountLiveData.setValue(0);
                        streetQrImagesLiveData.setValue(QrImageLiveData.getValue());
                    } else {
                        streetQrImagesLiveData.setValue(QrImageLiveData.getValue());
                        for (HouseDetailsImageDTO house : QrImageLiveData.getValue()
                        ) {
                            if (!house.getQRCodeImage().matches("/Images/default_not_upload.png")) {
                                sImgCount += 1;

                            }
                        }
                        imageCountLiveData.setValue(sImgCount);
                        Log.e(TAG, "onResponse: ImageCount " + sImgCount);
                    }
                }

                @Override
                public void onFailure(Throwable s) {
                    Log.e(TAG, "onFailure: " + s.getMessage());
                }
            });
        }

    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getHouseQrImagesLiveData() {
        return houseQrImagesLiveData;
    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getDumpyQrImagesLiveData() {
        return dumpyQrImagesLiveData;
    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getLiquidQrImagesLiveData() {
        return liquidQrImagesLiveData;
    }

    public MutableLiveData<List<HouseDetailsImageDTO>> getStreetQrImagesLiveData() {
        return streetQrImagesLiveData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressLiveData;
    }

    public LiveData<Integer> getImageCount() {
        return imageCountLiveData;
    }
}
