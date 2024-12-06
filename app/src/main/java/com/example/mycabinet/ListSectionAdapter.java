package com.example.mycabinet;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        FoodSection section = mSectionList.get(position);
        holder.mTitle.setText(section.getSectionName());
        holder.mSize.setText("Items: " + section.getSectionSize());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    FoodSection section = mSectionList.get(clickedPosition);

                    Toast.makeText(mActivity, "Section clicked: " + section.getSectionName(), Toast.LENGTH_SHORT).show();
                    Log.d("ListSectionAdapter", "onBindViewHolder: Section clicked: " + section.getSectionName());

                    Intent intent = new Intent(mActivity, SectionActivity.class);

                    intent.putExtra("SECTION_TO_VIEW", section);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSectionList.size();
    }

    public class ListSectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;
        TextView mSize;

        public ListSectionHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.section_name);
            mSize = view.findViewById(R.id.item_date);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mActivity.showSection(getAdapterPosition());
        }
    }
}