package com.example.essam.myapplication.LogIn.AllOrders;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.essam.myapplication.LogIn.Model.OrderModel;
import com.example.essam.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends AppCompatActivity implements OrderAdapterInterface {

    private RecyclerView recyclerOrders;
    private OrdersAdapter ordersAdapter;
    private ProgressBar progressBar;
    private List<OrderModel> list;
    private AllOrderViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        viewModel = ViewModelProviders.of(this).get(AllOrderViewModel.class);

        iniListener();
        initView();
        showProgress();
        viewModel.getAllOrders();
    }

    private void iniListener() {
        viewModel.getData().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderModel> orderModels) {
                hideProgress();
                if (orderModels != null && orderModels.size() > 0) {
                    list = new ArrayList<>();
                    list = orderModels;
                    ordersAdapter.setData(list);

                } else {
                    Toast.makeText(AllOrdersActivity.this, "Not Have Any orders.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerOrders = (RecyclerView) findViewById(R.id.recyclerOrder);
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrders.setHasFixedSize(true);
        ordersAdapter = new OrdersAdapter(this, this);
        recyclerOrders.setAdapter(ordersAdapter);
    }

    @Override
    public void onClick(OrderModel orderModel) {
        Toast.makeText(this, orderModel.getOrder_id(), Toast.LENGTH_SHORT).show();

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
