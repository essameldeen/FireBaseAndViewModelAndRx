package com.example.essam.myapplication.LogIn.CreateOrder;

import android.support.annotation.NonNull;

import com.example.essam.myapplication.LogIn.Model.OrderModel;
import com.example.essam.myapplication.LogIn.Register.RegisterFireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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


public class CreateFireBase {
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String imageUrl;
    private StorageReference storageReference;

    private static class Loader {
        static CreateFireBase INSTANCE = new CreateFireBase();
    }

    private CreateFireBase() {
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    public static CreateFireBase getInstance() {
        return CreateFireBase.Loader.INSTANCE;
    }


    public Observable<Boolean> createOrder(final OrderModel orderModel) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {

                final String randomName = UUID.randomUUID().toString();
                StorageReference filePath = storageReference.child("Order_image").child(randomName + ".jpg");
                filePath.putFile(orderModel.getImage_url()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            imageUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                            Map<String, Object> newOrder = new HashMap<>();
                            newOrder.put("UserName", orderModel.getName());
                            newOrder.put("Email", orderModel.getEmail());
                            newOrder.put("OrderTitle", orderModel.getTitle());
                            newOrder.put("OrderImage", imageUrl);
                            newOrder.put("OrderDescription", orderModel.getDescription());
                            newOrder.put("UserId", auth.getCurrentUser().getUid());

                            firestore.collection("Orders").document().set(newOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        emitter.onNext(true);
                                    } else {
                                        emitter.onNext(false);
                                    }
                                }
                            });

                        } else {
                            emitter.onNext(false);
                        }
                    }
                });


            }
        });
    }

}
