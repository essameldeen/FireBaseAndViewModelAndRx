package com.example.essam.myapplication.LogIn.MyProile;

import android.support.annotation.NonNull;

import com.example.essam.myapplication.LogIn.Login.LogInFireBase;
import com.example.essam.myapplication.LogIn.Model.CurrentUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MyProfileFireBase {
    private FirebaseFirestore firestore ;


    private static class Loader {
        static MyProfileFireBase INSTANCE = new MyProfileFireBase();
    }

    private MyProfileFireBase() {
        firestore = FirebaseFirestore.getInstance();
    }

    public static MyProfileFireBase getInstance() {
        return MyProfileFireBase.Loader.INSTANCE;
    }


    public Observable<CurrentUser> getUserInfo(final String userId){
        return Observable.create(new ObservableOnSubscribe<CurrentUser>() {
            @Override
            public void subscribe(final ObservableEmitter<CurrentUser> emitter) throws Exception {
                firestore.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    CurrentUser currentUser = new CurrentUser();
                                    currentUser.setUserName(task.getResult().get("UserName").toString());
                                    currentUser.setImage_url(task.getResult().get("Image").toString());
                                    currentUser.setEmail(task.getResult().get("Email").toString());
                                    emitter.onNext(currentUser);
                                }else {
                                    emitter.onNext(null);
                                }
                    }
                });
            }
        });


    }

}
