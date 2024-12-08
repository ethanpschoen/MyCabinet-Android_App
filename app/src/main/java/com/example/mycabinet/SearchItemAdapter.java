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

/*
The SearchItemAdapter class is responsible for populating the list of items in a section.
It is used by the SearchActivity class to display the items in a RecyclerView.
 */

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemHolder> {

    /*
    Member variables

    mItemList: ArrayList of FoodItems to be displayed in the RecyclerView
    mActivity: SearchActivity containing the RecyclerView
     */
    private ArrayList<FoodItem> mItemList;
    private SearchActivity mActivity;


    // Constructor to initialize member variables
    public SearchItemAdapter(SearchActivity activity, ArrayList<FoodItem> items) {
        mItemList = items;
        mActivity = activity;
    }

    @NonNull
    @Override
    public SearchItemAdapter.SearchItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_item, parent, false);

        return new SearchItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemAdapter.SearchItemHolder holder, int position) {
        // Set the text for each item
        FoodItem item = mItemList.get(position);

        holder.mName.setText(item.getItemName());
        holder.mDate.setText(item.getExpirationDate().toString());
        // Will soon implement change of date text to red if within Chef-specified reminder days

        // Set up the delete button for each FoodItem
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get current position of the clicked item
                int currentPosition = holder.getAdapterPosition(); // Not sure what the non-deprecated method is

                // Remove the item from the list and notify the adapter
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Find the item
                    FoodItem clickedItem = mItemList.get(currentPosition);

                    // Remove the item from the list and notify the adapter
                    mItemList.remove(clickedItem);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, mItemList.size());

                    // Remove the item from the section
                    Kitchen kitchen = Kitchen.getInstance();
                    kitchen.removeItem(clickedItem);

                    // Show a toast message to confirm the deletion
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

        /*
        Member variables

        mName: TextView representing the name of the item
        mDate: TextView representing the expiration date of the item
        deleteButton: Button representing the delete button
         */
        TextView mName;
        TextView mDate;
        Button deleteButton;

        // Constructor to initialize the ViewHolder
        public SearchItemHolder(View view) {
            super(view);
            // Find the views in the layout
            mName = view.findViewById(R.id.item_name);
            mDate = view.findViewById(R.id.item_date);
            deleteButton = view.findViewById(R.id.delete_item_button);
        }
    }
}
