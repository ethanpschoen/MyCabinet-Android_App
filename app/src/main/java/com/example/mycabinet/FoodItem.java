package com.example.mycabinet;
import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    public static final String FOODNAME = "fooditem.txt";

    private Context mContext;
    private List<String> mFoodItemList;

    public FoodItem(Context context) {
        mContext = context;
        mFoodItemList = new ArrayList<>();
    }

    public void addItem(String item) throws IllegalArgumentException {
        mFoodItemList.add(item);
    }

    public String[] getItems() {
        String[] items = new String[mFoodItemList.size()];
        return mFoodItemList.toArray(items);
    }

    public void clear() {
        mFoodItemList.clear();
    }

    public void saveToFile() throws IOException {

        // Write list to file in internal storage
        FileOutputStream outputStream = mContext.openFileOutput(FOODNAME, Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }

    public void readFromFile() throws IOException {

        // Read in list from file in internal storage
        FileInputStream inputStream = mContext.openFileInput(FOODNAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            mFoodItemList.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                mFoodItemList.add(line);
            }
        }
        catch (FileNotFoundException ex) {
            // Ignore
        }
    }

    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : mFoodItemList) {
            writer.println(item);
        }
        writer.close();
    }
}
