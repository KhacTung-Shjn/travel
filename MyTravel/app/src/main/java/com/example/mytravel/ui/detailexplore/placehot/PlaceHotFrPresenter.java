package com.example.mytravel.ui.detailexplore.placehot;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.PlaceHot;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;

public class PlaceHotFrPresenter extends BasePresenter implements PlaceHotFrMvpPresenter {

    private PlaceHotFrMvpView getMvpView;
    private String data;

    public PlaceHotFrPresenter(PlaceHotFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void getListPlaceHot(String idCity, String idExplore, String idPlace) {
        new LoadListPlaceHotAsyncTask(idCity, idExplore, idPlace).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoadListPlaceHotAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idCity;
        private String idExplore;
        private String idPlace;

        public LoadListPlaceHotAsyncTask(String idCity, String idExplore, String idPlace) {
            this.idCity = idCity;
            this.idExplore = idExplore;
            this.idPlace = idPlace;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainApp.getInstance().getFirebaseFireStore()
                    .collection("placehot")
                    .whereEqualTo("idPlace", idPlace)
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (e != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }

                        if (queryDocumentSnapshots != null) {
                            ArrayList<PlaceHot> listPlaceHots = new ArrayList<>();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                data = getGSon().toJson(snapshot.getData());
                                if (data != null) {
                                    PlaceHot placeHot = getGSon().fromJson(data, PlaceHot.class);
                                    Timestamp timecreate = (Timestamp) snapshot.get("timecreate");
                                    placeHot.setTimeCreate(timecreate);
                                    listPlaceHots.add(placeHot);
                                }
                            }
                            if (listPlaceHots.size() != 0) {
                                Collections.sort(listPlaceHots, (o1, o2) -> String.valueOf(o2.getTimeCreate()).compareTo(String.valueOf(o1.getTimeCreate())));
                                getMvpView.successGetListPlaceHot(listPlaceHots);
                            } else {
                                getMvpView.showMessage(R.string.msg_get_all_list_explore_empty);
                            }
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getMvpView.hideLoading();
        }
    }
}
