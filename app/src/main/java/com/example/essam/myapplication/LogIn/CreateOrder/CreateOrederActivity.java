package com.example.essam.myapplication.LogIn.CreateOrder;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.essam.myapplication.LogIn.HomePage.HomeActivity;
import com.example.essam.myapplication.LogIn.Model.OrderModel;
import com.example.essam.myapplication.R;

public class CreateOrederActivity extends AppCompatActivity {


    private static final int OPEN_GALLARY = 1;
    private static final int PERMISSION_IMAGE = 2;
    private ImageView imageOrder;
    private EditText titleOrder;
    private EditText descriptionOrder;
    private ProgressBar progressBar;
    private Button create;
    private Uri image_uri;
    private CreateOrderViewModel orderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_oreder);
        orderViewModel = ViewModelProviders.of(this).get(CreateOrderViewModel.class);
        initView();
        initListener();
    }

    private void initListener() {
        orderViewModel.getCreate().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                hideProgress();
                if (aBoolean) {
                    Toast.makeText(CreateOrederActivity.this, "Order Added Successfully", Toast.LENGTH_SHORT).show();
                   backToHomePage();
                } else {
                    Toast.makeText(CreateOrederActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void backToHomePage() {
        Intent intent = new Intent(CreateOrederActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        imageOrder = (ImageView) findViewById(R.id.imageOrder);
        titleOrder = (EditText) findViewById(R.id.titleOrder);
        descriptionOrder = (EditText) findViewById(R.id.descriptionOrder);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        imageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

    }

    private void checkData() {
        if (image_uri != null && titleOrder.getText().toString() != "" && descriptionOrder.getText().toString() != "") {
            OrderModel orderModel = new OrderModel();
            orderModel.setDescription(descriptionOrder.getText().toString());
            orderModel.setTitle(titleOrder.getText().toString());
            orderModel.setImage_url(image_uri);
            orderModel.setName("Essam");
            orderModel.setEmail("esam@gmail.com");
            showProgress();
            orderViewModel.createOrder(orderModel);


        } else {
            Toast.makeText(this, "Please Fill All Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_IMAGE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK)
                .setType("image/*");
        startActivityForResult(gallery, OPEN_GALLARY);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_IMAGE && grantResults != null && grantResults.length > 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "please Take Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_GALLARY && resultCode == RESULT_OK && data != null) {
            image_uri = data.getData();
            imageOrder.setImageURI(image_uri);
        }
    }


}
