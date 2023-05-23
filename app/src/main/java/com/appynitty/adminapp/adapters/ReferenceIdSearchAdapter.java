package com.appynitty.adminapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReferenceIdSearchAdapter extends ArrayAdapter implements Filterable {
    private List<String> mlistData;

    public ReferenceIdSearchAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mlistData = new ArrayList<>();
    }

    public void setData(List<String> list) {
        mlistData.clear();
        mlistData.addAll(list);
    }

    @Override
    public int getCount() {
        return mlistData.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mlistData.get(position);
    }

    public Object getObject(int position) {
        return mlistData.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    filterResults.values = mlistData;
                    filterResults.count = mlistData.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && (filterResults.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }
}