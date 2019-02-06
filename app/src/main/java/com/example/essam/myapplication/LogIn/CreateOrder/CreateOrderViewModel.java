package com.example.essam.myapplication.LogIn.CreateOrder;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.essam.myapplication.LogIn.Model.CurrentUser;
import com.example.essam.myapplication.LogIn.Model.OrderModel;
import com.example.essam.myapplication.LogIn.Model.RegisterModel;
import com.example.essam.myapplication.LogIn.Register.RegisterFireBase;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateOrderViewModel extends ViewModel {
    private MutableLiveData<Boolean> create = new MutableLiveData<>();

    public MutableLiveData<Boolean> getCreate() {
        return create;
    }


    public void createOrder (OrderModel orderModel){
        CreateFireBase.getInstance().createOrder(orderModel).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        create.postValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        create.postValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
