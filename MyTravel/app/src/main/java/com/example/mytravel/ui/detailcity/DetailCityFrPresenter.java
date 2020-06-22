package com.example.mytravel.ui.detailcity;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class DetailCityFrPresenter extends BasePresenter implements DetailCityFrMvpPresenter {

    private DetailCityFrMvpView getMvpView;

    private String data;

    public DetailCityFrPresenter(DetailCityFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void getListExplore(String idCity) {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document(idCity)
                .collection("explore")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        getMvpView.showMessage(R.string.msg_error_unknown);
                    }

                    if (queryDocumentSnapshots != null) {
                        ArrayList<Explore> listExplore = new ArrayList<>();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            data = getGSon().toJson(snapshot.getData());
                            if (data != null) {
                                Explore explore = getGSon().fromJson(data, Explore.class);
                                listExplore.add(explore);
                            }
                        }
                        if (listExplore.size() != 0) {
                            getMvpView.successGetListExplore(listExplore);
                        } else {
                            getMvpView.showMessage(R.string.msg_get_all_list_explore_empty);
                        }
                    }
                });
    }

    @Override
    public void getListTourPopular(String idCity) {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document(idCity)
                .collection("tour_popular")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        getMvpView.showMessage(R.string.msg_error_unknown);
                    }

                    if (queryDocumentSnapshots != null) {
                        ArrayList<TourPopular> listTours = new ArrayList<>();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            data = getGSon().toJson(snapshot.getData());
                            if (data != null) {
                                TourPopular tourPopular = getGSon().fromJson(data, TourPopular.class);
                                listTours.add(tourPopular);
                            }
                        }
                        if (listTours.size() != 0) {
                            getMvpView.successGetListTourPopular(listTours);
                        } else {
                            getMvpView.showMessage(R.string.msg_get_all_list_tour_popular_empty);
                        }
                    }
                });
    }
}
