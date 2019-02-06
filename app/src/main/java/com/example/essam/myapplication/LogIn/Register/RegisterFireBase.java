package com.example.essam.myapplication.LogIn.Register;

import android.content.Context;
import android.support.annotation.NonNull;


import com.example.essam.myapplication.LogIn.Login.LogInFireBase;
import com.example.essam.myapplication.LogIn.Model.CurrentUser;
import com.example.essam.myapplication.LogIn.Model.RegisterModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RegisterFireBase {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;

    private String image_url = "";

    private static class Loader {
        static RegisterFireBase INSTANCE = new RegisterFireBase();
    }

    private RegisterFireBase() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public static RegisterFireBase getInstance() {
        return RegisterFireBase.Loader.INSTANCE;
    }


    public Observable<CurrentUser> register(Context context, final RegisterModel registerModel) {
            return Observable.create(new ObservableOnSubscribe<CurrentUser>() {
                @Override
                public void subscribe(final ObservableEmitter<CurrentUser> emitter) throws Exception {

                    auth.createUserWithEmailAndPassword(registerModel.email, registerModel.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final String randomName = UUID.randomUUID().toString();
                                StorageReference filePath = storageReference.child("User_Profile").child(randomName + ".jpg");
                                filePath.putFile(registerModel.image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            image_url = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                                            Map<String, Object> newUser = new HashMap<>();
                                            newUser.put("Email", registerModel.email);
                                            newUser.put("UserName", registerModel.userName);
                                            newUser.put("Image", image_url);
                                            newUser.put("User_id", auth.getCurrentUser().getUid());
                                            firestore.collection("Users")
                                                    .document(auth.getCurrentUser().getUid())
                                                    .set(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                         if(task.isSuccessful()){
                                                             CurrentUser currentUser = new CurrentUser();
                                                             currentUser.setEmail(registerModel.email);
                                                             currentUser.setUser_id(auth.getCurrentUser().getUid());
                                                             currentUser.setUserName(registerModel.userName);
                                                             currentUser.setImage_url(image_url);
                                                             emitter.onNext(currentUser);
                                                         }else {
                                                             emitter.onNext(null);
                                                         }
                                                }
                                            });
                                        } else {
                                          emitter.onNext(null);
                                        }
                                    }
                                });

                            } else {
                                emitter.onNext(null);
                            }
                        }
                    });
                }
            });


        }



}
