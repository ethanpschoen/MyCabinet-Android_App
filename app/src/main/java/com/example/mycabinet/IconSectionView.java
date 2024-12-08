package com.example.mycabinet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
The IconSectionView Fragment is not implemented in the app yet.
When finished, it will be a scrollable display of the icons of each FoodSection in the Kitchen.
 */

public class IconSectionView extends Fragment {

    public IconSectionView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_icon_section_view, container, false);
    }
}