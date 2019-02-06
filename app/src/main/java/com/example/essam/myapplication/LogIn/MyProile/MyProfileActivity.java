package com.example.essam.myapplication.LogIn.MyProile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.example.essam.myapplication.LogIn.Model.CurrentUser;
import com.example.essam.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    private CircleImageView imageProfile;
    private TextView userName;
    private TextView email;
    private Toolbar toolbar;
    private MyProfileViewModel myProfileViewModel;
    private ProgressBar progressBar;
    private String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        currentId = getIntent().getStringExtra("userId");
        myProfileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel.class);
        initView();
        initListener();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        showProgress();
        myProfileViewModel.getInfo(currentId);
    }

    private void initListener() {
        myProfileViewModel.getInfo().observe(this, new Observer<CurrentUser>() {
            @Override
            public void onChanged(@Nullable CurrentUser currentUser) {
                hideProgress();
                if (currentUser != null) {

                    updateView(currentUser);
                } else {
                    Toast.makeText(MyProfileActivity.this, "Error Happen Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateView(CurrentUser currentUser) {

        userName.setText(currentUser.getUserName());
        email.setText(currentUser.getEmail());
        Glide.with(this).load(currentUser.getImage_url()).into(imageProfile);


    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageProfile = (CircleImageView) findViewById(R.id.imageProfile);
        userName = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.emailUser);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
