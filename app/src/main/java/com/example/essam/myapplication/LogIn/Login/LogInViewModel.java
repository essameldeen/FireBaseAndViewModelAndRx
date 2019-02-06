package com.example.essam.myapplication.LogIn.Login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.essam.myapplication.LogIn.Login.LogInFireBase;
import com.example.essam.myapplication.LogIn.Login.loginModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LogInViewModel extends ViewModel {

    private  MutableLiveData<loginModel> login = new MutableLiveData<>();

    public MutableLiveData<loginModel> getLogin() {
        return login;
    }


   public void logIn (Context context ,String email ,String password ){
       LogInFireBase.getInstance().logIn(context,email,password).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
               .subscribe(new Observer<loginModel>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(loginModel value) {
                        login.postValue(value);
                   }

                   @Override
                   public void onError(Throwable e) {
                          login.postValue(null);
                   }

                   @Override
                   public void onComplete() {

                   }
               });
   }
}
