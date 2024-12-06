package com.example.mycabinet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListItemView extends Fragment {

    private RecyclerView recyclerView;
    private FoodSection section;
    private ListItemAdapter adapter;

    public ListItemView(FoodSection section) {
        this.section = section;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_item_view, container, false);

        if (getActivity() instanceof SectionActivity) {
            SectionActivity activity = (SectionActivity) getActivity();

            recyclerView = view.findViewById(R.id.item_list);
            adapter = new ListItemAdapter(activity, section.getSectionItems());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {
            throw new RuntimeException("Not attached to activity");
        }

        return view;
    }
}