package com.example.mycabinet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder> {

    private List<FoodItem> mItemList;
    private SectionActivity mActivity;

    public ListItemAdapter(SectionActivity activity, List<FoodItem> itemList) {
        mItemList = itemList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ListItemAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlistitem, parent, false);

        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.ListItemHolder holder, int position) {
        FoodItem item = mItemList.get(position);
        holder.mName.setText(item.getItemName());
        holder.mDate.setText(item.getExpirationDate().toString());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mDate;

        public ListItemHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.item_name);
            mDate = view.findViewById(R.id.item_date);
            view.setClickable(true);
        }
    }
}
