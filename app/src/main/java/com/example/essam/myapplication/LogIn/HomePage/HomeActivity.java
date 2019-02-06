package com.example.essam.myapplication.LogIn.HomePage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.essam.myapplication.LogIn.AllOrders.AllOrdersActivity;
import com.example.essam.myapplication.LogIn.CreateOrder.CreateOrederActivity;
import com.example.essam.myapplication.LogIn.MyProile.MyProfileActivity;
import com.example.essam.myapplication.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView profile;
    private CardView orders;
    private CardView createOrder;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userId= getIntent().getStringExtra("userId");

        initView();

    }

    private void initView() {
        profile=(CardView)findViewById(R.id.myProfile);
        orders=(CardView)findViewById(R.id.allOrders);
        createOrder=(CardView)findViewById(R.id.create);

        profile.setOnClickListener(this);
        orders.setOnClickListener(this);
        createOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myProfile:
                Intent intent = new Intent(HomeActivity.this,MyProfileActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
                case R.id.allOrders:
                    Intent orders = new Intent(HomeActivity.this,AllOrdersActivity.class);
                    startActivity(orders);
                break;
                case R.id.create:
                    Intent create = new Intent(HomeActivity.this,CreateOrederActivity.class);
                    startActivity(create);
                break;
                default:
                    break;
        }
    }
}
