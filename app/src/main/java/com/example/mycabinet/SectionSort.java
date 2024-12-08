package com.example.mycabinet;

import java.util.ArrayList;
import java.util.Collections;

/*
The SectionSort class is a helper class for the MainActivity.
It sorts the ArrayList of FoodSections based on either name or size, using a recursive merge sort.
 */

public class SectionSort {

    /*
    Member variable

    sections: an ArrayList of FoodSections for which to sort
     */
    private ArrayList<FoodSection> sections;


    // Constructor to initialize the object
    public SectionSort(ArrayList<FoodSection> sections) {
        this.sections = sections;
    }


    /*
    public void sortByName()

    Public method to sort the member variable sections by name (alphabetical), using the sort() method below
     */
    public void sortByName() {
        sort("NAME");
    }


    /*
    public void sortBySize()

    Public method to sort the member variable sections by size (largest first), using the sort() method below
     */
    public void sortBySize() {
        sort("SIZE");
        // Reverse the list to get largest first (since otherwise lower numbers are sorted first)
        Collections.reverse(sections);
    }


    /*
    private void sort(String sortType)

    Takes in a String referring to how to sort the member variable sections
    This is the wrapper function for a recursive merge sort
     */
    private void sort(String sortType) {
        if (sections == null || sections.size() <= 1) {
            return;
        }

        recurSort(sortType, 0, sections.size());
    }

    /*
    private void recurSort(String sortType, int start, int end)

    Takes in a String referring to how to sort the member variable sections and
    the start and ending indices for the current iteration
    This is a recursive merge sort algorithm that sorts the member variable sections
     */
    private void recurSort(String sortType, int start, int end) {
        // Return if sub-list is one or fewer elements
        if (end - start <= 1) {
            return;
        }

        // Find middle index
        int mid = (start + end) / 2;

        // Sort left and right halves first
        recurSort(sortType, start, mid);
        recurSort(sortType, mid, end);


        // Merge the two sorted halves
        int left_index = 0;
        int right_index = 0;

        // Temporary list to hold sorted sections
        ArrayList<FoodSection> temp = new ArrayList<>();

        // Strings to hold data for each list
        String left_data;
        String right_data;

        // While left and right sub-lists are not empty, compare their next elements
        while (left_index < mid - start && right_index < end - mid) {
            if (sortType.equals("NAME")) {
                // If sorting by name, compare their names
                left_data = getName(sections.get(left_index + start));
                right_data = getName(sections.get(mid + right_index));
            } else {
                // If sorting by size, compare their sizes
                left_data = getSize(sections.get(left_index + start));
                right_data = getSize(sections.get(mid + right_index));
            }

            // If left item is less than right item, add it to the temp list and move left index up
            // Else, add the right item to the temp list and move right index up
            if (left_data.compareTo(right_data) < 0) {
                temp.add(sections.get(start + left_index));
                left_index++;
            } else {
                temp.add(sections.get(mid + right_index));
                right_index++;
            }
        }

        // Empty remaining items to temp list
        while (left_index < mid - start) {
            temp.add(sections.get(start + left_index));
            left_index++;
        }

        while (right_index < end - mid) {
            temp.add(sections.get(mid + right_index));
            right_index++;
        }


        // Add sorted items to original list
        for (int i = 0; i < temp.size(); i++) {
            sections.set(start + i, temp.get(i));
        }
    }

    /*
    private String getName(FoodSection section)

    Takes in a FoodSection and returns its name
     */
    private String getName(FoodSection section) {
        return section.getSectionName();
    }

    /*
    private String getSize(FoodSection section)

    Takes in a FoodSection and returns its size
     */
    private String getSize(FoodSection section) {
        int size = section.getSectionSize();
        return Integer.toString(size);
    }
}
