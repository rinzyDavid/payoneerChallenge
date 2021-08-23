package com.payoneerchallange.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.payoneerchallange.R;
import com.payoneerchallange.data.Gateway;
import com.payoneerchallange.databinding.PaymentMethodItemBinding;


import java.util.List;

/**
 * GatewayViewAdapter class : For handling recylerview data
 *
 */
public class GateWayViewAdapter extends RecyclerView.Adapter<GateWayViewAdapter.CustomViewHolder> {

    private Activity context;
    private List<Gateway> gateways;

    public GateWayViewAdapter(Activity context, List<Gateway> gateways){
        this.context = context;
        this.gateways = gateways;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PaymentMethodItemBinding binding = PaymentMethodItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Gateway gateway = gateways.get(position);
        holder.bind(gateway);

    }

    @Override
    public int getItemCount() {
                return gateways == null ? 0 : gateways.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder{

        PaymentMethodItemBinding binding;

        public CustomViewHolder(@NonNull PaymentMethodItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(Gateway gateway){

            Glide.with(context)
                    .load(gateway.getLink().getLogo())
                    .placeholder(R.drawable.ic_no_image)
                    .into(binding.logo);

            binding.paymentName.setText(gateway.getLabel());

        }
    }
}
