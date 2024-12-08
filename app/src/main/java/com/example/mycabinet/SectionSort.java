package com.example.mycabinet;

import java.util.ArrayList;
import java.util.Collections;

public class SectionSort {

    private ArrayList<FoodSection> sections;

    public SectionSort(ArrayList<FoodSection> sections) {
        this.sections = sections;
    }

    public void sortByName() {
        sort("NAME");
    }

    public void sortBySize() {
        sort("SIZE");
        Collections.reverse(sections);
    }

    private void sort(String sortType) {
        if (sections == null || sections.size() <= 1) {
            return;
        }

        recurSort(sortType, sections, 0, sections.size());
    }

    private void recurSort(String sortType, ArrayList<FoodSection> sections, int start, int end) {
        if (end - start <= 1) {
            return;
        }

        int mid = (start + end) / 2;

        recurSort(sortType, sections, start, mid);
        recurSort(sortType, sections, mid, end);

        int left_index = 0;
        int right_index = 0;

        ArrayList<FoodSection> temp = new ArrayList<>();

        String left_data;
        String right_data;

        while (left_index < mid - start && right_index < end - mid) {
            if (sortType.equals("NAME")) {
                left_data = getName(sections.get(left_index + start));
                right_data = getName(sections.get(mid + right_index));
            } else {
                left_data = getSize(sections.get(left_index + start));
                right_data = getSize(sections.get(mid + right_index));
            }

            if (left_data.compareTo(right_data) < 0) {
                temp.add(sections.get(start + left_index));
                left_index++;
            } else {
                temp.add(sections.get(mid + right_index));
                right_index++;
            }
        }

        while (left_index < mid - start) {
            temp.add(sections.get(start + left_index));
            left_index++;
        }

        while (right_index < end - mid) {
            temp.add(sections.get(mid + right_index));
            right_index++;
        }

        for (int i = 0; i < temp.size(); i++) {
            sections.set(start + i, temp.get(i));
        }
    }

    private String getName(FoodSection section) {
        return section.getSectionName();
    }

    private String getSize(FoodSection section) {
        int size = section.getSectionSize();
        return Integer.toString(size);
    }
}
