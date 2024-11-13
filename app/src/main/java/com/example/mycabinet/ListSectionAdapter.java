package com.example.mycabinet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListSectionAdapter extends RecyclerView.Adapter<ListSectionAdapter.ListItemHolder> {

    private List<FoodSection> mSectionList;
    private MainActivity mActivity;

    public ListSectionAdapter(MainActivity activity, List<FoodSection> sectionList) {
        mSectionList = sectionList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ListSectionAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sectionlistitem, parent, false);

        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSectionAdapter.ListItemHolder holder, int position) {
        FoodSection section = mSectionList.get(position);
        holder.mTitle.setText(section.getSectionName());
        holder.mSize.setText("Items: " + section.getSectionSize());
    }

    @Override
    public int getItemCount() {
        return mSectionList.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;
        TextView mSize;

        public ListItemHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.section_name);
            mSize = view.findViewById(R.id.section_size);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mActivity.showSection(getAdapterPosition());
        }
    }
}
