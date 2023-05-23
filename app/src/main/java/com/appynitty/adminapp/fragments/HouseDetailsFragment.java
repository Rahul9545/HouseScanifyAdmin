package com.appynitty.adminapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.activities.HomeActivity;
import com.appynitty.adminapp.adapters.HouseDetailsAdapter;
import com.appynitty.adminapp.databinding.FragmentHouseDetailsBinding;
import com.appynitty.adminapp.dialogs.FilterDialog;
import com.appynitty.adminapp.dialogs.FilterDialogFragment;
import com.appynitty.adminapp.models.HouseDetailsImageDTO;
import com.appynitty.adminapp.models.QrImageStatusDTO;
import com.appynitty.adminapp.repositories.QrImageStatusRepo;
import com.appynitty.adminapp.utils.MainUtils;
import com.appynitty.adminapp.viewmodels.HouseDetailsImageVM;
import com.appynitty.adminapp.viewmodels.QrImageStatusViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


public class HouseDetailsFragment extends Fragment {
    private static final String TAG = "HouseDetailsFragment";
    private Context context;
    AppCompatActivity activity;
    private View view;
    private RadioGroup rdGroup;
    private String filterType = "HW", appId;
    private int currentImageCount = 0, totalImageCount = 0;
    private RadioButton rdHouse, rdDumpYard, rdLiquid, rdStreet;
    private List<HouseDetailsImageDTO> imageDataList;
    private RecyclerView recyclerHouseImage;
    SnapHelper snapHelper;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private LinearLayoutManager layoutManager;
    private HouseDetailsAdapter houseDetailsAdapter;
    private ProgressBar loader;
    ImageButton ib;
    private TextView txtNoData, tvCount, tvBottomEntryCount;
    private EditText etHouseFilter;
    private Bundle filterExtras;
    private CardView crdFilter;
    private FilterDialogFragment filterDialog;
    HouseDetailsImageVM houseDetailsImageVM;
    QrImageStatusViewModel qrImageStatusVM;
    FragmentHouseDetailsBinding binding;
    ImageView scrollIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            /*binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house_details, container, false);
            view = binding.getRoot();
            binding.setLifecycleOwner(this);*/
            view = inflater.inflate(R.layout.fragment_house_details, container, false);
            init();
        }
        return view;
    }


    private void init() {
        String empId = getArguments().getString(MainUtils.EMP_ID);

        appId = Prefs.getString(MainUtils.APP_ID);
        int houseCount = getArguments().getInt("houseCount");
        int dumpCount = getArguments().getInt("dumpCount");
        int liquidCount = getArguments().getInt("liquidCount");
        int streetCount = getArguments().getInt("streetCount");
        String fromDate = getArguments().getString("fromDate");
        Log.e(TAG, "init: emp-id: " + empId + ", fromDate: " + fromDate);
        context = getActivity();
        activity = (AppCompatActivity) view.getContext();
        recyclerHouseImage = view.findViewById(R.id.recycler_House_image);
        scrollIndicator = view.findViewById(R.id.ivHoriIndicatorDown);
        imageDataList = new ArrayList<>();
        crdFilter = view.findViewById(R.id.card_filter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerHouseImage);
        loader = view.findViewById(R.id.progress_circular);
        loader.setVisibility(View.GONE);
        txtNoData = view.findViewById(R.id.txt_no_data);
        tvCount = view.findViewById(R.id.tvItementries);
        tvBottomEntryCount = view.findViewById(R.id.txt_entries_bottom1);
        txtNoData.setVisibility(View.GONE);
        etHouseFilter = view.findViewById(R.id.etHouseFilter);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        ib = activity.findViewById(R.id.ib_home);
        ib.setImageResource(R.drawable.ic_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment empListFrag = new EmpListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_frame_layout, empListFrag).addToBackStack(null).commit();
            }
        });

        rdGroup = view.findViewById(R.id.rd_group);

        rdHouse = view.findViewById(R.id.rdHouse);
        rdDumpYard = view.findViewById(R.id.rdDumpyard);
        rdLiquid = view.findViewById(R.id.rdLiquid);
        rdStreet = view.findViewById(R.id.rdStreet);

        if (houseCount > 0)
            rdHouse.setTextColor(ContextCompat.getColor(context, R.color.red));

        if (dumpCount > 0)
            rdDumpYard.setTextColor(ContextCompat.getColor(context, R.color.red));

        if (liquidCount > 0)
            rdLiquid.setTextColor(ContextCompat.getColor(context, R.color.red));

        if (streetCount > 0)
            rdStreet.setTextColor(ContextCompat.getColor(context, R.color.red));

        recyclerHouseImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    // Scrolling right
                    scrollIndicator.setAnimation(null);
                    scrollIndicator.setVisibility(View.GONE);

                } else {
                    // Scrolling left
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    System.out.println("SCROLL_STATE_IDLE");
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerHouseImage.getLayoutManager();
                    currentImageCount = linearLayoutManager.findFirstVisibleItemPosition() + 1;
                    tvCount.setText(getResources().getString(R.string.imageCount, totalImageCount, currentImageCount));
                    Log.e(TAG, "onScrollStateChanged: position: " + currentImageCount);
                }
            }

        });

        houseDetailsImageVM = new ViewModelProvider(this).get(HouseDetailsImageVM.class);
        if (!fromDate.isEmpty()) {
            filterExtras = new Bundle();
            filterExtras.putString("frmDate", fromDate);
            filterExtras.putString("toDate", fromDate);
            filterExtras.putString("userId", empId);
            filterExtras.putString("filterType", filterType);
            filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID));
            houseDetailsImageVM.setFilterData(filterExtras);
        } else {
            houseDetailsImageVM.callHouseApi(empId);
        }

        qrImageStatusVM = new ViewModelProvider(this).get(QrImageStatusViewModel.class);

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rdHouse:
                        if (rdHouse.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdHouse");
                        filterType = "HW";

                        if (filterExtras != null) {
                            filterExtras.putString("filterType", filterType);
                            houseDetailsImageVM.setFilterData(filterExtras);
                        }

                        houseDetailsImageVM.callHouseApi(empId);
                        break;
                    case R.id.rdDumpyard:
                        if (rdDumpYard.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdDumpyard");
                        filterType = "DY";
                        if (filterExtras != null) {
                            filterExtras.putString("filterType", filterType);
                            houseDetailsImageVM.setFilterData(filterExtras);
                        }
                        houseDetailsImageVM.callDumpYardApi();
                        break;
                    case R.id.rdLiquid:
                        if (rdLiquid.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdLiquid");
                        filterType = "LW";
                        if (filterExtras != null) {
                            filterExtras.putString("filterType", filterType);
                            houseDetailsImageVM.setFilterData(filterExtras);
                        }
                        houseDetailsImageVM.callLiquidWasteApi();
                        break;
                    case R.id.rdStreet:
                        if (rdStreet.isChecked())
                            Log.e(TAG, "onRadioBtnClicked: checked rdStreet");
                        filterType = "SW";

                        if (filterExtras != null) {
                            filterExtras.putString("filterType", filterType);
                            houseDetailsImageVM.setFilterData(filterExtras);
                        }

                        houseDetailsImageVM.callStreetWasteApi();
                        break;
                }
            }
        });

        etHouseFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        houseDetailsImageVM.getHouseQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {

            @Override
            public void onChanged(List<HouseDetailsImageDTO> houseDetailsImageDTOS) {
                imageDataList.clear();

                for (HouseDetailsImageDTO house : houseDetailsImageDTOS
                ) {
                    imageDataList.add(house);
                }
                setOnRecycler(imageDataList);
            }
        });

        houseDetailsImageVM.getDumpyQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {
            int size = 0;

            @Override
            public void onChanged(List<HouseDetailsImageDTO> dumpYardWasteList) {

                if (!imageDataList.isEmpty())
                    imageDataList.clear();

                for (HouseDetailsImageDTO dumpYard : dumpYardWasteList) {
                    imageDataList.add(dumpYard);
                    Log.e(TAG, "onChanged: DumpYardId: " + dumpYard.getReferanceId());
                }
                setOnRecycler(imageDataList);
                Log.e(TAG, "onChanged: DumpYardId: Size---" + dumpYardWasteList.size());
            }
        });

        houseDetailsImageVM.getLiquidQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {
            @Override
            public void onChanged(List<HouseDetailsImageDTO> liquidWasteList) {
                if (!imageDataList.isEmpty())
                    imageDataList.clear();

                for (HouseDetailsImageDTO liquidWaste :
                        liquidWasteList) {
                    imageDataList.add(liquidWaste);
                    Log.e(TAG, "onChanged: liquidId: " + liquidWaste.getReferanceId());
                }
                setOnRecycler(imageDataList);
                Log.e(TAG, "onChanged: LiquidWasteList: Size---" + liquidWasteList.size());
            }
        });

        houseDetailsImageVM.getStreetQrImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<HouseDetailsImageDTO>>() {
            @Override
            public void onChanged(List<HouseDetailsImageDTO> streetWasteList) {
                if (!imageDataList.isEmpty())
                    imageDataList.clear();

                for (HouseDetailsImageDTO streetWaste :
                        streetWasteList) {
                    imageDataList.add(streetWaste);
                    Log.e(TAG, "onChanged: liquidId: " + streetWaste.getReferanceId());
                }
                setOnRecycler(imageDataList);
                Log.e(TAG, "onChanged: streetWasteList: Size---" + streetWasteList.size());
            }
        });

        houseDetailsImageVM.getProgress().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                loader.setVisibility(integer);
            }
        });

        houseDetailsImageVM.getImageCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer imgCount) {
//                tvCount.setText(getResources().getString(R.string.imageCount, imgCount, currentImageCount));
                totalImageCount = imgCount;
                if (imgCount == 0){
                    currentImageCount = 0;
                }else{
                    currentImageCount = 1;
                }

                tvCount.setText(getResources().getString(R.string.imageCount, totalImageCount, currentImageCount));
                tvBottomEntryCount.setText(getResources().getString(R.string.imageCount, imgCount, currentImageCount));
                Log.e(TAG, "onChanged: totalImageCount: " + imgCount);
            }
        });

        setOnClick();

    }

    private void setOnClick() {
        crdFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    private void openDialog() {

        filterDialog = new FilterDialogFragment("houseDetails");

        filterDialog.setFilterDialogListener(new FilterDialog.FilterDialogInterface() {
            @Override
            public void onFilterDialogDismiss(String frmDate, String toDate, String userId) {
                Log.e(TAG, "onFilterDialogDismiss: fromDate: " + frmDate + " userId: " + userId);
                filterExtras = new Bundle();
                filterExtras.putString("frmDate", frmDate);
                filterExtras.putString("toDate", frmDate);
                filterExtras.putString("userId", userId);
                filterExtras.putString("filterType", filterType);
                filterExtras.putString("appId", Prefs.getString(MainUtils.APP_ID));
                houseDetailsImageVM.setFilterData(filterExtras);
            }
        });

        filterDialog.show(getChildFragmentManager(), TAG);
    }

    private void filter(String text) {
        List<HouseDetailsImageDTO> filteredList = new ArrayList<>();

        for (HouseDetailsImageDTO item : imageDataList) {
            if (item.getReferanceId().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        houseDetailsAdapter.filterList(filteredList);
    }


    private void setOnRecycler(List<HouseDetailsImageDTO> imageDataList) {

        if (imageDataList.size() > 1) {
            scrollIndicator.setVisibility(View.VISIBLE);
            translate(scrollIndicator);
        } else {
            scrollIndicator.setAnimation(null);
            scrollIndicator.setVisibility(View.GONE);
        }
        QrImageStatusRepo imageStatusRepo = QrImageStatusRepo.getInstance();
        txtNoData.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
        houseDetailsAdapter = new HouseDetailsAdapter(context, imageDataList, new HouseDetailsAdapter.MyClickListener() {
            @Override
            public void onItemClicked(String houseId, Boolean status) {
                Log.e(TAG, "onItemClicked: houseId: " + houseId + ", status: " + status);
                qrImageStatusVM.initApi(appId, houseId, status);
                qrImageStatusVM.getQrImageStatusLivedata().observe(getViewLifecycleOwner(), new Observer<List<QrImageStatusDTO>>() {
                    @Override
                    public void onChanged(List<QrImageStatusDTO> qrImageStatusDTOS) {
                        MainUtils.success(qrImageStatusDTOS.get(0).getMessage(), Toast.LENGTH_SHORT);
                    }
                });
                qrImageStatusVM.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        MainUtils.error(s, Toast.LENGTH_SHORT);
                    }
                });
                qrImageStatusVM.getProgressStatus().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        loader.setVisibility(integer);
                    }
                });

            }
        });
        houseDetailsAdapter.notifyDataSetChanged();
        recyclerHouseImage.setLayoutManager(layoutManager);
        recyclerHouseImage.setAdapter(houseDetailsAdapter);
    }

    private void moveToNewActivity() {

        Intent i = new Intent(getActivity(), HomeActivity.class);
        startActivity(i);
        activity.overridePendingTransition(0, 0);

    }

    public void translate(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(15.0f, 50.0f, 0.0f, 0.0f);
        translateAnimation.setDuration(800);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(translateAnimation);
    }

}