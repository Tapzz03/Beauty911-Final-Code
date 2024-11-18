package com.beauty911;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {

    private ArrayList<Service> serviceList;

    public ServicesAdapter(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.nameTextView.setText(service.getName());
        holder.priceTextView.setText("R" + service.getPrice());  // Assuming price is a double
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.serviceName);
            priceTextView = itemView.findViewById(R.id.servicePrice);
        }
    }
}
