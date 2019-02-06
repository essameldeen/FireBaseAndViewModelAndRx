package com.example.essam.myapplication.LogIn.Register;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.essam.myapplication.LogIn.Login.LogInFireBase;
import com.example.essam.myapplication.LogIn.Login.loginModel;
import com.example.essam.myapplication.LogIn.Model.CurrentUser;
import com.example.essam.myapplication.LogIn.Model.RegisterModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<CurrentUser> regiter = new MutableLiveData<>();

    public MutableLiveData<CurrentUser> getRegiter() {
        return regiter;
    }


    public void register (Context context , RegisterModel registerModel){
      RegisterFireBase.getInstance().register(context,registerModel).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
              .subscribe(new Observer<CurrentUser>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onNext(CurrentUser value) {
                        regiter.postValue(value);
                  }

                  @Override
                  public void onError(Throwable e) {
                          regiter.postValue(null);
                  }

                  @Override
                  public void onComplete() {

                  }
              });

    }
}
