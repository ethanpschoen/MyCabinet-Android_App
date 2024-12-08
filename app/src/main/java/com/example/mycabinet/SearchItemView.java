package com.example.mycabinet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SearchItemView extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<FoodItem> items;
    private SearchItemAdapter adapter;

    public SearchItemView(ArrayList<FoodItem> items) {
        this.items = items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_item_view, container, false);

        if (getActivity() instanceof SearchActivity) {
            SearchActivity activity = (SearchActivity) getActivity();

            recyclerView = view.findViewById(R.id.search_list);

            adapter = new SearchItemAdapter(activity, items);

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