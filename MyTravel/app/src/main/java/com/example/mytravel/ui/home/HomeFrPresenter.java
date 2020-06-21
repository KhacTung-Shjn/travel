package com.example.mytravel.ui.home;

import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.City;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFrPresenter extends BasePresenter implements HomeFrMvpPresenter {

    private HomeFrMvpView getMvpView;
    private String data;

    public HomeFrPresenter(HomeFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;

    }

    @Override
    public void getListCity() {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .orderBy("rate_city", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        getMvpView.showMessage(R.string.msg_error_unknown);
                    }
                    if (queryDocumentSnapshots != null) {
                        ArrayList<City> listCities = new ArrayList<>();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            data = getGSon().toJson(snapshot.getData());
                            if (data != null) {
                                City city = getGSon().fromJson(data, City.class);
                                listCities.add(city);
                            }
                        }
                        if (listCities.size() != 0) {
                            getMvpView.successGetListCity(listCities);
                        } else {
                            getMvpView.showMessage(R.string.msg_get_all_list_city_empty);
                        }
                    }
                });
    }
}
