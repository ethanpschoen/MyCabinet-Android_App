package com.example.mycabinet;

import java.util.ArrayList;

public class ItemSort {

    private ArrayList<FoodItem> items;

    public ItemSort(ArrayList<FoodItem> items) {
        this.items = items;
    }

    public void sortByName() {
        sort("NAME");
    }

    public void sortByDate() {
        sort("DATE");
    }

    private void sort(String sortType) {
        if (items == null || items.size() <= 1) {
            return;
        }

        recurSort(sortType, items, 0, items.size());
    }

    private void recurSort(String sortType, ArrayList<FoodItem> items, int start, int end) {
        if (end - start <= 1) {
            return;
        }

        int mid = (start + end) / 2;

        recurSort(sortType, items, start, mid);
        recurSort(sortType, items, mid, end);

        int left_index = 0;
        int right_index = 0;

        ArrayList<FoodItem> temp = new ArrayList<>();

        String left_data;
        String right_data;

        while (left_index < mid - start && right_index < end - mid) {
            if (sortType.equals("NAME")) {
                left_data = getName(items.get(left_index + start));
                right_data = getName(items.get(mid + right_index));
            } else {
                left_data = getDate(items.get(left_index + start));
                right_data = getDate(items.get(mid + right_index));
            }

            if (left_data.compareTo(right_data) < 0) {
                temp.add(items.get(start + left_index));
                left_index++;
            } else {
                temp.add(items.get(mid + right_index));
                right_index++;
            }
        }

        while (left_index < mid - start) {
            temp.add(items.get(start + left_index));
            left_index++;
        }

        while (right_index < end - mid) {
            temp.add(items.get(mid + right_index));
            right_index++;
        }

        for (int i = 0; i < temp.size(); i++) {
            items.set(start + i, temp.get(i));
        }
    }

    private String getName(FoodItem item) {
        return item.getItemName();
    }

    private String getDate(FoodItem item) {
        return item.getExpirationDate().toString();
    }
}
