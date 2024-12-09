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
The ListItemAdapter class is responsible for populating the list of items in a section.
It is used by the ListItemView class to display the items in a RecyclerView.
 */

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder> {

    /*
    Member variables

    mSection: FoodSection representing the section of the kitchen
    mItemList: ArrayList<FoodItem> representing the list of items in the section
    mActivity: SectionActivity representing the activity that the adapter is associated with
     */
    private FoodSection mSection;
    private ArrayList<FoodItem> mItemList;
    private SectionActivity mActivity;


    // Constructor to initialize the adapter
    public ListItemAdapter(SectionActivity activity, FoodSection section) {
        mSection = section;
        mItemList = section.getSectionItems();
        mActivity = activity;
    }

    @NonNull
    @Override
    public ListItemAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each list item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_item, parent, false);

        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.ListItemHolder holder, int position) {
        // Set the text for each list item
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
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, mItemList.size());

                    // Remove the item from the section
                    mSection.removeFoodItem(clickedItem);

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

    // ViewHolder class for each list item
    public class ListItemHolder extends RecyclerView.ViewHolder {

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
        public ListItemHolder(View view) {
            super(view);
            // Find the views in the layout
            mName = view.findViewById(R.id.item_name);
            mDate = view.findViewById(R.id.item_date);
            deleteButton = view.findViewById(R.id.delete_item_button);
        }
    }
}
