package com.example.mycabinet;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListSectionView extends Fragment {

    private RecyclerView recyclerView;
    private Kitchen kitchen;
    private ListSectionAdapter adapter;

    public ListSectionView(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_section_view, container, false);

        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();

            recyclerView = view.findViewById(R.id.section_list);
            adapter = new ListSectionAdapter(activity, kitchen.getSections());
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