package com.example.mycabinet;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListSectionAdapter extends RecyclerView.Adapter<ListSectionAdapter.ListSectionHolder> {

    private List<FoodSection> mSectionList;
    private MainActivity mActivity;

    public ListSectionAdapter(MainActivity activity, List<FoodSection> sectionList) {
        mSectionList = sectionList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ListSectionAdapter.ListSectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sectionlistitem, parent, false);

        return new ListSectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSectionAdapter.ListSectionHolder holder, int position) {
        FoodSection section = mSectionList.get(holder.getAdapterPosition());

        holder.mTitle.setText(section.getSectionName());
        holder.mSize.setText("Items: " + section.getSectionSize());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();

                if (currentPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(mActivity, SectionActivity.class);

                    FoodSection clickedSection = mSectionList.get(currentPosition);

                    intent.putExtra("SECTION_TO_VIEW", clickedSection.getSectionName());

                    mActivity.startActivity(intent);
                }
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();

                if (currentPosition != RecyclerView.NO_POSITION) {
                    FoodSection clickedSection = mSectionList.get(currentPosition);

                    mSectionList.remove(section);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, mSectionList.size());


                    Kitchen kitchen = Kitchen.getInstance();
                    kitchen.removeSection(section);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSectionList.size();
    }

    public class ListSectionHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mSize;
        Button deleteButton;

        public ListSectionHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.section_name);
            mSize = view.findViewById(R.id.item_date);
            deleteButton = view.findViewById(R.id.delete_section_button);
        }
    }
}