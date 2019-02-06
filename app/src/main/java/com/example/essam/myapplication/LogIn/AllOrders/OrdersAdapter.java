package com.example.essam.myapplication.LogIn.AllOrders;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.essam.myapplication.LogIn.Model.OrderModel;
import com.example.essam.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private List<OrderModel> list;
    private Context context;
    private OrderAdapterInterface orderAdapterInterface;

    public OrdersAdapter(Context context, OrderAdapterInterface anInterface) {
        this.context = context;
        this.orderAdapterInterface = anInterface;
    }

    public void setData(List<OrderModel> list) {
        this.list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    private OrderModel getOrder(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_single_order, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrderModel order = getOrder(i);
        viewHolder.descriptionOrder.setText(order.getDescription());
        viewHolder.titleOrder.setText(order.getTitle());
        Glide.with(context).load(order.getImage_url()).into(viewHolder.imageOrder);
        viewHolder.model = order;
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageOrder;
        private TextView titleOrder;
        private TextView descriptionOrder;
        private OrderModel model;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOrder = itemView.findViewById(R.id.imageOrder);
            titleOrder = itemView.findViewById(R.id.titleOrder);
            descriptionOrder = itemView.findViewById(R.id.descriptionOrder);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            orderAdapterInterface.onClick(model);
        }
    }
}
