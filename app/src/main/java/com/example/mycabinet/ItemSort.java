package com.example.mycabinet;

import java.util.ArrayList;

/*
The ItemSort class is a helper class for the SectionActivity.
It sorts the ArrayList of FoodItems based on either name or date, using a recursive merge sort.
 */

public class ItemSort {

    /*
    Member variable

    items: an ArrayList of FoodItems for which to sort
     */
    private ArrayList<FoodItem> items;


    // Constructor to initialize the object
    public ItemSort(ArrayList<FoodItem> items) {
        this.items = items;
    }


    /*
    public void sortByName()

    Public method to sort the member variable items by name (alphabetical), using the sort() method below
     */
    public void sortByName() {
        sort("NAME");
    }

    /*
    public void sortByDate()

    Public method to sort the member variable items by date (earliest first), using the sort() method below
     */
    public void sortByDate() {
        sort("DATE");
    }


    /*
    private void sort(String sortType)

    Takes in a String referring to how to sort the member variable items
    This is the wrapper function for a recursive merge sort
     */
    private void sort(String sortType) {
        if (items == null || items.size() <= 1) {
            return;
        }

        recurSort(sortType, 0, items.size());
    }

    /*
    private void recurSort(String sortType, ArrayList<FoodItem> items, int start, int end)

    Takes in a String referring to how to sort the member variable items and
    the start and ending indices for the current iteration
    This is a recursive merge sort algorithm that sorts the member variable items
     */
    private void recurSort(String sortType, int start, int end) {
        // Return if sub-list is one or fewer elements
        if (end - start <= 1) {
            return;
        }

        // Find middle index
        int mid = (start + end) / 2;

        // Sort left and right halves of the list
        recurSort(sortType, start, mid);
        recurSort(sortType, mid, end);


        // Merge the two sorted halves
        int left_index = 0;
        int right_index = 0;

        // Temporary list to hold sorted items
        ArrayList<FoodItem> temp = new ArrayList<>();

        // Strings to hold data for each list
        String left_data;
        String right_data;

        // While left and right sub-lists are not empty, compare their next elements
        while (left_index < mid - start && right_index < end - mid) {
            if (sortType.equals("NAME")) {
                // If sorting by name, compare their names
                left_data = getName(items.get(left_index + start));
                right_data = getName(items.get(mid + right_index));
            } else {
                // If sorting by date, compare their dates
                left_data = getDate(items.get(left_index + start));
                right_data = getDate(items.get(mid + right_index));
            }

            // If left item is less than right item, add it to the temp list and move left index up
            // Else, add the right item to the temp list and move right index up
            if (left_data.compareTo(right_data) < 0) {
                temp.add(items.get(start + left_index));
                left_index++;
            } else {
                temp.add(items.get(mid + right_index));
                right_index++;
            }
        }

        // Empty remaining items to temp list
        while (left_index < mid - start) {
            temp.add(items.get(start + left_index));
            left_index++;
        }

        while (right_index < end - mid) {
            temp.add(items.get(mid + right_index));
            right_index++;
        }


        // Add sorted items to original list
        for (int i = 0; i < temp.size(); i++) {
            items.set(start + i, temp.get(i));
        }
    }

    /*
    private String getName(FoodItem item)

    Takes in a FoodItem and returns its name
     */
    private String getName(FoodItem item) {
        return item.getItemName();
    }

    /*
    private String getDate(FoodItem item)

    Takes in a FoodItem and returns its expiration date
     */
    private String getDate(FoodItem item) {
        return item.getExpirationDate().toString();
    }
}
