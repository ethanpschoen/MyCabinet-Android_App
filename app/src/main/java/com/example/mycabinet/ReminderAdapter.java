package com.example.mycabinet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycabinet.Database.ReminderClass;
import com.example.mycabinet.R;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    Context context;
    List<ReminderClass> reminderClasses;

    public ReminderAdapter(Context context, List<ReminderClass> reminderClasses) {
        this.context = context;
        this.reminderClasses = reminderClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dates_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.eventText.setText(reminderClasses.get(position).getFoodName());
        holder.timeAndDateText.setText(reminderClasses.get(position).getFoodDate() + " " + reminderClasses.get(position).getFoodTime());
    }

    @Override
    public int getItemCount() {
        return reminderClasses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventText, timeAndDateText;
        private LinearLayout toplayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventText = (TextView) itemView.findViewById(R.id.event);
            timeAndDateText = (TextView) itemView.findViewById(R.id.time_and_date);
            toplayout = (LinearLayout) itemView.findViewById(R.id.toplayout);
        }
    }
}