package com.example.mycabinet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemHolder> {

    private ArrayList<FoodItem> mItemList;
    private SearchActivity mActivity;

    public SearchItemAdapter(SearchActivity activity, ArrayList<FoodItem> items) {
        mItemList = items;
        mActivity = activity;
    }

    @NonNull
    @Override
    public SearchItemAdapter.SearchItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_item, parent, false);

        return new SearchItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemAdapter.SearchItemHolder holder, int position) {
        FoodItem item = mItemList.get(position);

        holder.mName.setText(item.getItemName());
        holder.mDate.setText(item.getExpirationDate().toString());
        // Change date text to red if within specified reminder days

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();

                if (currentPosition != RecyclerView.NO_POSITION) {
                    FoodItem clickedItem = mItemList.get(currentPosition);

                    mItemList.remove(clickedItem);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, mItemList.size());

                    Kitchen kitchen = Kitchen.getInstance();

                    kitchen.removeItem(clickedItem);

                    Toast.makeText(mActivity, "Item deleted: " + clickedItem.getItemName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class SearchItemHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mDate;
        Button deleteButton;

        public SearchItemHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.item_name);
            mDate = view.findViewById(R.id.item_date);
            deleteButton = view.findViewById(R.id.delete_item_button);
        }
    }
}
