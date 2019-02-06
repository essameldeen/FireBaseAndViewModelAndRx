package com.example.essam.myapplication.LogIn.Login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class LogInFireBase {

    private FirebaseAuth auth ;


    private static class Loader {
        static LogInFireBase INSTANCE = new LogInFireBase();
    }

    private LogInFireBase() {
        auth = FirebaseAuth.getInstance();
    }

    public static LogInFireBase getInstance() {
        return LogInFireBase.Loader.INSTANCE;
    }

    public Observable<loginModel> logIn(Context context, final String email , final String password){
        return Observable.create(new ObservableOnSubscribe<loginModel>() {
            @Override
            public void subscribe(final ObservableEmitter<loginModel> e) throws Exception {
                       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        loginModel loginModel = new loginModel();
                                        loginModel.setEmail(email);
                                        loginModel.setId(auth.getCurrentUser().getUid());
                                        e.onNext(loginModel);

                                    }else {
                                           e.onNext(null);
                                    }
                           }
                       });
            }
        });



    }




}
