package com.example.essam.myapplication.LogIn.Register;

import android.Manifest;
import android.arch.lifecycle.Observer;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.essam.myapplication.LogIn.HomePage.HomeActivity;
import com.example.essam.myapplication.LogIn.Login.LoginActivity;
import com.example.essam.myapplication.LogIn.Model.CurrentUser;
import com.example.essam.myapplication.LogIn.Model.RegisterModel;
import com.example.essam.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private static int OPEN_GALLARY = 1;
    private static int PERMISSION_IMAGE = 2;

    private CircleImageView circleImageView;
    private EditText email;
    private EditText userName;
    private EditText password;
    private Button register;
    private Uri image_uri;
    private RegisterViewModel registerViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        initListener();
        initView();
    }

    private void initListener() {
        registerViewModel.getRegiter().observe(this, new Observer<CurrentUser>() {
            @Override
            public void onChanged(@Nullable CurrentUser currentUser) {
                hideProgress();
                if (currentUser != null) {
                    goToHomePage(currentUser.getUser_id());


                } else {
                    Toast.makeText(RegisterActivity.this, "Error Happen Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        circleImageView = (CircleImageView) findViewById(R.id.image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        userName = (EditText) findViewById(R.id.name);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void goToHomePage(String id) {
        Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
        home.putExtra("userId", id);
        startActivity(home);
        finish();
    }

    private void checkData() {
        if (image_uri != null && email.getText().toString() != "" && password.getText().toString() != null
                && userName.getText().toString() != "") {
            RegisterModel registerModel = new RegisterModel();
            registerModel.email = email.getText().toString();
            registerModel.password = password.getText().toString();
            registerModel.image = image_uri;
            registerModel.userName = userName.getText().toString();
            showProgress();
            registerViewModel.register(this, registerModel);


        } else {
            Toast.makeText(this, "Please Fill All Data", Toast.LENGTH_SHORT).show();
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
            circleImageView.setImageURI(image_uri);
        }
    }
}
