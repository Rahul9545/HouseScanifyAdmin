package com.appynitty.adminapp.adapters;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.adminapp.R;
import com.appynitty.adminapp.databinding.ItemUlbCheckboxBinding;
import com.appynitty.adminapp.generated.callback.OnClickListener;
import com.appynitty.adminapp.models.UlbDTO;
import com.appynitty.adminapp.models.UserRoleModelDTO;
import com.appynitty.adminapp.utils.DoublyLinkedList;
import com.appynitty.adminapp.utils.MainUtils;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*****
 * Ulb list adapter - rahul rokade
 * */
public class UlbListAdapter extends RecyclerView.Adapter<UlbListAdapter.MyViewHolder> {

    private static final String TAG = "UlbListAdapter";
    private Context context;
    List<UlbDTO> ulbList;
    public boolean isAllChecked = false;
    public boolean isAllCheckedUpdate = false;
    public boolean isChecked = false;
    private UserRoleModelDTO userRoleRightDetails;
    public String arg = "";
    int position;
    String [] strings = new String [] {"1", "2" };
    List<String> stringList = new ArrayList<String>(Arrays.asList(strings));
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> listUp = new ArrayList<String>();
    private String appIdList;
    ArrayList<String> unselectList = new ArrayList<String>();
    private UlbListAdapterInterface listener;
    private List<String> selectedAppIdList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();
    private ArrayList<String>  existingWithSelectionList = new ArrayList<>();
    private List<String> existingWithNonSelectionList = new ArrayList<>();

    ArrayList<String> results = new ArrayList<>();

    public void setAppIdList(String appIdList) {
        this.appIdList = appIdList;
    }

    public UlbListAdapter(Context context, List<UlbDTO> ulbList, UlbListAdapterInterface listener) {
        this.context = context;
        this.ulbList = ulbList;
        this.listener = listener;
    }

    public interface UlbListAdapterInterface{
       // void onItemClicked(ArrayList<String> list);
        void onItemClicked(String list);
        void onItemClickedUpdate(String listUp);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUlbCheckboxBinding ulbCheckboxBinding = ItemUlbCheckboxBinding.inflate(layoutInflater,parent, false);
        return new UlbListAdapter.MyViewHolder(ulbCheckboxBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final UlbDTO ulb = ulbList.get(position);
        holder.ulbCheckboxBinding.setUlbList(ulb);

        /*****
         * Update user right - existing user get ulb list then checked checkbox
         * */

        if (appIdList != null && !appIdList.equals("")){
            String regex = "\\[|\\]";
            appIdList = appIdList.replaceAll(regex,"");

            String[] items = appIdList.split(",");
            Log.e(TAG, "onBindViewHolder: "+items );
            selectedAppIdList.clear();
            for (int i =0 ; i<items.length; i++){
                selectedAppIdList.add(items[i].trim().replaceAll("\\s",""));
            }
            Log.e(TAG, "already exist app id list: "+selectedAppIdList);

            /*StringBuilder aa = new StringBuilder();
            for (UlbDTO s : ulbList) {
                aa.append(s.getAppId()+",");
                String result = aa.toString();
                String ss = result.replaceAll(",$", "");
                String[] itemListTwo = ss.split(",");
                secondList.clear();
                for (int j =0 ; j<itemListTwo.length; j++){
                    secondList.add(itemListTwo[j].trim());
                }
                Log.e(TAG, "main ulb list: "+secondList);
            }*/
        }

        /*if (!selectedAppIdList.isEmpty() ){

            for (int i =0; i<selectedAppIdList.size(); i++){
                Log.i("RahulDev", "onBindViewHolder: "+ulb.getAppId() + "   " + selectedAppIdList.get(i));

                Log.d(TAG, "onBindViewHolder: "+selectedAppIdList);
                if (selectedAppIdList.get(i).equals(String.valueOf(ulb.getAppId()))){
                    holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);

                }
            }
        }else {
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(false);
        }*/

        /*****
         * Add user right - select all then save button clicked
         * */
        if(!isAllChecked){
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(false);

        }
        else {
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);
            StringBuilder sb = new StringBuilder();
            for (UlbDTO s : ulbList) {
                sb.append(s.getAppId()+",");
            }
            String result = sb.toString();
            String ss = result.replaceAll(",$","");
            ArrayList<String> list = new ArrayList<String>();
            list.add(ss.trim());
            Log.e(TAG, "active ulb is :- " +list);
            listener.onItemClicked(ss.trim().replaceAll("\\s",""));

        }

        /*****
         * Update user right - select all then update button clicked
         * */
        if(!isAllCheckedUpdate){
            if (!selectedAppIdList.isEmpty() ){

                for (int i =0; i<selectedAppIdList.size(); i++){
                   // Log.i("RahulDev", "onBindViewHolder: "+ulb.getAppId() + "   " + selectedAppIdList.get(i));

                    Log.d(TAG, "onBindViewHolder: "+selectedAppIdList);
                    if (selectedAppIdList.get(i).equals(String.valueOf(ulb.getAppId()))){
                        holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);

                    }
                }
                String rahulExistList = Arrays.toString(selectedAppIdList.toArray()).replace("[","").replace("]","");
                listener.onItemClickedUpdate(rahulExistList.trim().replaceAll("\\s",""));
            }else {
                //holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(false);
            }
            //holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(false);

        }else {
            holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);
            StringBuilder sb = new StringBuilder();
            for (UlbDTO s : ulbList) {
                sb.append(s.getAppId()+",");
            }
            String result = sb.toString();
            String ss = result.replaceAll(",$","");
            ArrayList<String> listUpdate = new ArrayList<String>();
            listUpdate.add(ss.trim());
            Log.e(TAG, "all updated active ulb is :- " +listUpdate);
            String rahulListUpdate = Arrays.toString(listUpdate.toArray()).replace("[","").replace("]","");
            Log.i("check", "stringCheck: "+rahulListUpdate.trim());
            listener.onItemClickedUpdate(rahulListUpdate.trim().replaceAll("\\s",""));
        }

        /*****
         * Add user right - select single click checkbox then get ulb appId value as well as remove selection
         * then removing in array list of item
         * after that generate in array list then covert in string and then clicked save button.
         * */

       /* holder.ulbCheckboxBinding.chkBoxUlbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
                if (compoundButton.isShown()){
                    int position = compoundButton.getId();  // get position of check box

                    if (compoundButton.isSelected()) {
                        getItemId(holder.getAdapterPosition());
                        String ulbId = String.valueOf(ulb.getAppId());
                        String ulbName = String.valueOf(ulb.getUlbName());
                        Log.e(TAG, "ulb is : " + ulbName + ", " + "ulb id : " + ulbId);

                        StringBuilder sb = new StringBuilder();
                        String ww = TextUtils.join(",", Collections.singleton(sb.append(ulbId)));
                        list.add(ww);
                        Log.e(TAG, String.valueOf(list));
                        listener.onItemClicked(list);

                    } else {
                        getItemId(holder.getAdapterPosition());
                        String ulbName = String.valueOf(ulb.getUlbName());
                        String ulbId = String.valueOf(ulb.getAppId());
                        Log.e(TAG, "ulb unselected : " +ulbName + " "+ "ulb id : " +ulbId);

                        ArrayList<String> unselectList = new ArrayList<String>();
                        StringBuilder sb = new StringBuilder();
                        String wo = TextUtils.join(",", Collections.singleton(sb.append(ulbId)));
                        unselectList.add(wo);
                        Log.e(TAG, "unselect ulb list is :- " +unselectList);

                        for(int i=0;i<list.size();i++){
                            if(list.get(i).equals(wo))
                            {
                                list.remove(i);
                                break;
                            }
                        }
                        Log.i(TAG, "unselect ulb list is :- " +list);
                        listener.onItemClicked(list);
                    }

                }
            }
        });*/

        holder.ulbCheckboxBinding.chkBoxUlbName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.ulbCheckboxBinding.chkBoxUlbName.isChecked()) {
                    getItemId(holder.getAdapterPosition());
                    String ulbId = String.valueOf(ulb.getAppId());
                    String ulbName = String.valueOf(ulb.getUlbName());
                    Log.e(TAG, "ulb is : " + ulbName + "," + "ulb id : " + ulbId);

                    StringBuilder sb = new StringBuilder();
                    String ww = TextUtils.join(",",Collections.singleton(sb.append(ulbId)));
                    list.add(ww.trim());
                    Log.e(TAG, String.valueOf(list));
                    listener.onItemClicked(ww.trim().replaceAll("\\s",""));

                } else {
                    getItemId(holder.getAdapterPosition());
                    String ulbName = String.valueOf(ulb.getUlbName());
                    String ulbId = String.valueOf(ulb.getAppId());
                    Log.e(TAG, "ulb unselected : " +ulbName + " "+ "ulb id : " +ulbId);

                    ArrayList<String> unselectList = new ArrayList<String>();
                    StringBuilder sb = new StringBuilder();
                    String wo = TextUtils.join(",",Collections.singleton(sb.append(ulbId)));
                    unselectList.add(wo.trim());
                    Log.e(TAG, "unselect ulb list is :- " +unselectList);

                    for(int i=0;i<list.size();i++){
                        if(list.get(i).equals(wo.trim()))
                        {
                            list.remove(i);
                            break;
                        }
                    }
                    Log.i(TAG, "unselect ulb list is :- " +list);
                    String rahulListUnSelectSave = Arrays.toString(list.toArray()).replace("[","").replace("]","");
                    Log.i("check", "stringCheck: "+rahulListUnSelectSave.trim());
                    listener.onItemClicked(rahulListUnSelectSave.trim().replaceAll("\\s",""));
                }
            }
        });


        /*****
         * Update user right - select single click checkbox then get ulb appId value as well as remove selection
         * then removing in array list of item
         * after that generate in array list then covert in string and then update click button.
         * */
        /*holder.ulbCheckboxBinding.chkBoxUlbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isSelectedUpdate) {
                if (buttonView.isShown()){
                    int position = buttonView.getId();

                    if (buttonView.isChecked()) {
                        getItemId(holder.getAdapterPosition());
                        String ulbIdU = String.valueOf(ulb.getAppId());
                        String ulbNameU = String.valueOf(ulb.getUlbName());
                        Log.e(TAG, "ulb is : " + ulbNameU + ", " + "ulb id : " + ulbIdU);

                        StringBuilder sb = new StringBuilder();
                        String ww = TextUtils.join(",", Collections.singleton(sb.append(ulbIdU)));

                        listUp.add(ww);

                    *//*JSONObject object = new JSONObject();
                    try {
                        object.put("activeUlb",list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*//*
                        Log.e(TAG, String.valueOf(listUp));
                        Log.e(TAG, "updated ulb list is :"+listUp);
                        listener.onItemClickedUpdate(listUp);

                    } else {
                        getItemId(holder.getAdapterPosition());
                        String ulbNameU = String.valueOf(ulb.getUlbName());
                        String ulbIdU = String.valueOf(ulb.getAppId());
                        Log.e(TAG, "ulb unselected : " +ulbNameU + " "+ "ulb id : " +ulbIdU);

                        ArrayList<String> unselectList = new ArrayList<String>();
                        StringBuilder sb = new StringBuilder();
                        String wo = TextUtils.join(",", Collections.singleton(sb.append(ulbIdU)));
                        unselectList.add(wo);
                        Log.e(TAG, "unselect list is :- " +unselectList);

                        for(int i=0;i<listUp.size();i++){
                            if(listUp.get(i).equals(wo))
                            {
                                listUp.remove(i);
                                break;
                            }
                        }
                        Log.i(TAG, "remaining updated unselect list is :- " +listUp);
                        listener.onItemClickedUpdate(listUp);

                    }
                }
            }
        });*/

        holder.ulbCheckboxBinding.chkBoxUlbName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getItemId(holder.getAdapterPosition());
                String ulbIdU;
                String ulbNameU;
                if (holder.ulbCheckboxBinding.chkBoxUlbName.isChecked()) {
                    ulbIdU = String.valueOf(ulb.getAppId());
                    ulbNameU = String.valueOf(ulb.getUlbName());
                    Log.e(TAG, "ulb is : " + ulbNameU + "," + "ulb id : " + ulbIdU);

                    StringBuilder sb = new StringBuilder();
                    String ww = TextUtils.join(",",Collections.singleton(sb.append(ulbIdU)));

                    listUp.add(ww.trim());

                    JSONObject object = new JSONObject();
                    try {
                        object.put("activeUlb",list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, String.valueOf(listUp));
                    Log.e(TAG, "updated ulb list is :"+listUp);
                    String rahulOneByOneListUpdate = Arrays.toString(listUp.toArray()).replace("[","").replace("]","");
                    Log.i("check", "stringCheck: "+rahulOneByOneListUpdate.trim());
                    listener.onItemClickedUpdate(rahulOneByOneListUpdate.trim().replaceAll("\\s",""));


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        existingWithSelectionList = (ArrayList<String>) Stream.concat(selectedAppIdList.stream(), listUp.stream()).collect(Collectors.toList());
                    }
                    Log.d(TAG, "already exist with selection list: "+existingWithSelectionList);
                    String rahulExistUpdate = Arrays.toString(existingWithSelectionList.toArray()).replace("[","").replace("]","");
                    Log.i("check", "stringCheck: "+rahulExistUpdate.trim());
                    listener.onItemClickedUpdate(rahulExistUpdate.trim().replaceAll("\\s",""));


                    if (!selectedAppIdList.isEmpty() ){

                        for (int i =0; i<selectedAppIdList.size(); i++){
                            // Log.i("RahulDev", "onBindViewHolder: "+ulb.getAppId() + "   " + selectedAppIdList.get(i));

                            Log.d(TAG, "onBindViewHolder: "+selectedAppIdList);
                            if (selectedAppIdList.get(i).equals(String.valueOf(ulb.getAppId()))){
                                holder.ulbCheckboxBinding.chkBoxUlbName.setChecked(true);

                            }
                        }
                    }

                } else {
                    Log.i(TAG, "remaining updated unselect list is :- " +listUp);
                    ulbIdU = String.valueOf(ulb.getUlbName());
                    ulbNameU = String.valueOf(ulb.getAppId());
                    Log.e(TAG, "ulb unselected : " + ulbIdU + " "+ "ulb id : " + ulbNameU);

                    ArrayList<String> unselectList = new ArrayList<String>();
                    StringBuilder sb = new StringBuilder();
                    String wo = TextUtils.join(",",Collections.singleton(sb.append(ulbNameU)));
                    unselectList.add(wo.trim());
                    Log.e(TAG, "unselect list is :- " +unselectList);

                    for(int i=0;i<listUp.size();i++){
                        if(listUp.get(i).equals(wo.trim()))
                        {
                            listUp.remove(i);
                            break;
                        }
                    }
                    Log.i(TAG, "remaining updated unselect list is :- " +listUp);
                    String rahulUnSeUpdate = Arrays.toString(listUp.toArray()).replace("[","").replace("]","");
                    Log.i("check", "stringCheck: "+rahulUnSeUpdate.trim());
                    listener.onItemClickedUpdate(rahulUnSeUpdate.trim().replaceAll("\\s",""));

                    for(int i=0;i<selectedAppIdList.size();i++){
                        if(selectedAppIdList.get(i).equals(wo.trim()))
                        {
                            selectedAppIdList.remove(i);
                            break;
                        }
                    }

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        existingWithNonSelectionList = (ArrayList<String>) Stream.concat(selectedAppIdList.stream(), listUp.stream()).collect(Collectors.toList());
                    }
                    Log.d(TAG, "already exist with non selection list: "+existingWithNonSelectionList);
                    String rahulUnSelectListUpdate = Arrays.toString(existingWithNonSelectionList.toArray()).replace("[","").replace("]","");
                    Log.i("check", "stringCheck: "+rahulUnSelectListUpdate.trim());
                    listener.onItemClickedUpdate(rahulUnSelectListUpdate.trim().replaceAll("\\s",""));

                }
               // listener.onItemClickedUpdate(listUp);
            }
        });

    }




    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<UlbDTO> searchList) {
        ulbList = searchList;
        notifyDataSetChanged();
        Log.e(TAG, "search list : "+ ulbList.size());
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setAllChecked(boolean isAllChecked) {
        Log.d(TAG, "RahulSaveSideCheck: "+isAllChecked);
        this.isAllChecked = isAllChecked;
        notifyDataSetChanged();
    }

    public void setAllCheckedUpdate(boolean isAllCheckedUpdate) {
        this.isAllCheckedUpdate = isAllCheckedUpdate;
        notifyDataSetChanged();
    }

    public void selectAll(){
        Log.e("onClickSelectAll","yes");
        isAllChecked =true;
        notifyDataSetChanged();
    }
        /****
         * Fix scroll adapter
         * */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return ulbList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemUlbCheckboxBinding ulbCheckboxBinding;
       // private CheckBox checkboxUlbName;
        public MyViewHolder(@NonNull ItemUlbCheckboxBinding ulbCheckboxBinding) {
            super(ulbCheckboxBinding.getRoot());
            this.ulbCheckboxBinding = ulbCheckboxBinding;
           // checkboxUlbName = itemView.findViewById(R.id.chk_box_ulb_name);

        }
    }
}
