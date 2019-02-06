package com.example.essam.myapplication.LogIn.AllOrders;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.essam.myapplication.LogIn.Model.OrderModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AllOrderViewModel extends ViewModel {
    private MutableLiveData<List<OrderModel>> data = new MutableLiveData<>();

    public MutableLiveData<List<OrderModel>> getData() {
        return data;
    }


    public void getAllOrders() {
        AllOrderFireBase.getInstance().getAllOrders().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<List<OrderModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<OrderModel> value) {
                data.postValue(value);
            }

            @Override
            public void onError(Throwable e) {
                data.postValue(new ArrayList<OrderModel>());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
