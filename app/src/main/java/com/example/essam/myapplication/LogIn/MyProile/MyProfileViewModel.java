package com.example.essam.myapplication.LogIn.MyProile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.essam.myapplication.LogIn.Model.CurrentUser;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyProfileViewModel extends ViewModel {
    private MutableLiveData<CurrentUser> info =new MutableLiveData<>();

    public MutableLiveData<CurrentUser> getInfo() {
        return info;
    }

    public void getInfo (String id){
        MyProfileFireBase.getInstance().getUserInfo(id).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<CurrentUser>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CurrentUser value) {
              info.postValue(value);
            }

            @Override
            public void onError(Throwable e) {
             info.postValue(null);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
