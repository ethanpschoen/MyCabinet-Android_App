package com.example.mycabinet;

import android.content.Intent;
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
The ListSectionAdapter is an adapter for the RecyclerView in MainActivity.
It is used by the ListSectionView to display the sections in the Kitchen.
 */

public class ListSectionAdapter extends RecyclerView.Adapter<ListSectionAdapter.ListSectionHolder> {

    /*
    Member variables

    mSectionList: ArrayList of FoodSection objects representing the sections in the Kitchen
    mActivity: MainActivity representing the activity the adapter is associated with
     */
    private ArrayList<FoodSection> mSectionList;
    private MainActivity mActivity;


    // Constructor to initialize member variables
    public ListSectionAdapter(MainActivity activity, ArrayList<FoodSection> sectionList) {
        mSectionList = sectionList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ListSectionAdapter.ListSectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each section item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_list_item, parent, false);

        return new ListSectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSectionAdapter.ListSectionHolder holder, int position) {
        // Set the text for each section item
        FoodSection section = mSectionList.get(holder.getAdapterPosition());

        holder.mTitle.setText(section.getSectionName());
        holder.mSize.setText("Items: " + section.getSectionSize());

        // Set up click listener for each section item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current position of clicked section
                int currentPosition = holder.getAdapterPosition();

                // If a valid position, start SectionActivity for the clicked section
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(mActivity, SectionActivity.class);

                    FoodSection clickedSection = mSectionList.get(currentPosition);

                    // Pass the name of the clicked section to SectionActivity
                    intent.putExtra("SECTION_TO_VIEW", clickedSection.getSectionName());

                    mActivity.startActivity(intent);
                }
            }
        });

        // Set up the delete button for each FoodSection
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current position of clicked section
                int currentPosition = holder.getAdapterPosition();

                // Remove the section from the list and notify the adapter
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Find the item
                    FoodSection clickedSection = mSectionList.get(currentPosition);

                    // Remove from list and notify adapter
                    mSectionList.remove(section);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, mSectionList.size());

                    // Remove the section from Kitchen
                    Kitchen kitchen = Kitchen.getInstance();
                    kitchen.removeSection(section);

                    // Display toast message to confirm the deletion
                    Toast.makeText(mActivity, "Section deleted: " + clickedSection.getSectionName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSectionList.size();
    }

    // ViewHolder class for each section item
    public class ListSectionHolder extends RecyclerView.ViewHolder {

        /*
        Member variables

        mTitle: TextView object representing the name of the section
        mSize: TextView object representing the number of items in the section
        deleteButton: Button object representing the delete button
         */

        TextView mTitle;
        TextView mSize;
        Button deleteButton;

        // Constructor to initialize member variables
        public ListSectionHolder(View view) {
            super(view);
            // Find the views in the layout
            mTitle = view.findViewById(R.id.section_name);
            mSize = view.findViewById(R.id.item_date);
            deleteButton = view.findViewById(R.id.delete_section_button);
        }
    }
}