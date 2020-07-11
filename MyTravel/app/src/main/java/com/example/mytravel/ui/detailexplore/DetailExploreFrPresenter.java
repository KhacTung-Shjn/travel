package com.example.mytravel.ui.detailexplore;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.Place;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class DetailExploreFrPresenter extends BasePresenter implements DetailExploreFrMvpPresenter {

    private DetailExploreFrMvpView getMvpView;
    private String data;

    public DetailExploreFrPresenter(DetailExploreFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void getListPlaces(String idCity, String idExplore, String nameExplore) {
        new LoadListPlaceAsyncTask(idCity,idExplore,nameExplore).execute();
    }


    @SuppressLint("StaticFieldLeak")
    class LoadListPlaceAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idCity;
        private String idExplore;
        private String nameExplore;

        public LoadListPlaceAsyncTask(String idCity, String idExplore, String nameExplore) {
            this.idCity = idCity;
            this.idExplore = idExplore;
            this.nameExplore = nameExplore;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainApp.getInstance().getFirebaseFireStore()
                    .collection("city")
                    .document(idCity)
                    .collection("explore")
                    .document(idExplore)
                    .collection("place")
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (e != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }

                        if (queryDocumentSnapshots != null) {
                            ArrayList<Place> listPlaces = new ArrayList<>();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                data = getGSon().toJson(snapshot.getData());
                                if (data != null) {
                                    Place place = getGSon().fromJson(data, Place.class);
                                    place.setNameExplore(nameExplore);
                                    listPlaces.add(place);
                                }
                            }
                            if (listPlaces.size() != 0) {
                                getMvpView.successGetListPlace(listPlaces);
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
