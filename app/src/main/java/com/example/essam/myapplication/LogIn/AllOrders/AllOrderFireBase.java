package com.example.essam.myapplication.LogIn.AllOrders;

import android.support.annotation.NonNull;

import com.example.essam.myapplication.LogIn.Model.OrderModel;
import com.example.essam.myapplication.LogIn.MyProile.MyProfileFireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class AllOrderFireBase {
    private FirebaseFirestore firestore;
    private List<OrderModel> list;
    private FirebaseAuth auth;

    private static class Loader {
        static AllOrderFireBase INSTANCE = new AllOrderFireBase();
    }

    private AllOrderFireBase() {
        list = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static AllOrderFireBase getInstance() {
        return AllOrderFireBase.Loader.INSTANCE;
    }

    public Observable<List<OrderModel>> getAllOrders() {
        return Observable.create(new ObservableOnSubscribe<List<OrderModel>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<OrderModel>> emitter) throws Exception {

                if (list.size() > 0) {
                    emitter.onNext(list);
                } else {
                    firestore.collection("Orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                String id = auth.getCurrentUser().getUid();
                                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                    String orderId = queryDocumentSnapshot.getString("UserId");
                                    if (orderId.equals(id)) {
                                        OrderModel orderModel = new OrderModel();
                                        orderModel.setEmail(queryDocumentSnapshot.get("Email").toString());
                                        orderModel.setName(queryDocumentSnapshot.get("UserName").toString());
                                        orderModel.setTitle(queryDocumentSnapshot.get("OrderTitle").toString());
                                        orderModel.setDescription(queryDocumentSnapshot.get("OrderDescription").toString());
                                        orderModel.setImageUrlString(queryDocumentSnapshot.get("OrderImage").toString());
                                        orderModel.setOrder_id(queryDocumentSnapshot.get("UserId").toString());
                                        list.add(orderModel);
                                    }
                                }
                                emitter.onNext(list);


                            } else {
                                emitter.onNext(list);
                            }
                        }
                    });
                }
            }
        });


    }


}
