package com.example.airmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowDataAdapter extends RecyclerView.Adapter<ShowDataAdapter.ViewHolder> {

    Context context;
    List<HelperClass> list;

    public ShowDataAdapter(Context context, List<HelperClass> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ShowDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDataAdapter.ViewHolder holder, int position) {
        HelperClass helperClass=list.get(position);
        holder.dateValue.setText(helperClass.dateTime);
        holder.dewPointValue.setText(helperClass.dewPoint);
        holder.pressureValue.setText(helperClass.pressure);
        holder.humidityValue.setText(helperClass.humidity);
        holder.tempValue.setText(helperClass.temperature);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateValue,tempValue,humidityValue,pressureValue,dewPointValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateValue=itemView.findViewById(R.id.dateValue);
            tempValue=itemView.findViewById(R.id.tempValue);
            humidityValue=itemView.findViewById(R.id.humidityValue);
            pressureValue=itemView.findViewById(R.id.pressureValue);
            dewPointValue=itemView.findViewById(R.id.dewPointValue);
        }
    }
}
