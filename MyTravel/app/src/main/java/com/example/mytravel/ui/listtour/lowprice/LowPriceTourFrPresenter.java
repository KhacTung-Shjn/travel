package com.example.mytravel.ui.listtour.lowprice;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.TourPopular;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LowPriceTourFrPresenter extends BasePresenter implements LowPriceTourFrMvpPresenter {

    private LowPriceTourFrMvpView getMvpView;
    private FirebaseFirestore db;
    private Gson gson;

    public LowPriceTourFrPresenter(LowPriceTourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        this.db = MainApp.getInstance().getFirebaseFireStore();
        gson = new Gson();
    }

    @Override
    public void getListLowPriceTour(String idCity, String idExplore) {
        new LoadLowPriceListTourAsyncTask(idCity, idExplore).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoadLowPriceListTourAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idCity, idExplore;

        public LoadLowPriceListTourAsyncTask(String idCity, String idExplore) {
            this.idCity = idCity;
            this.idExplore = idExplore;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.collection("city")
                    .document(idCity)
                    .collection("explore")
                    .document(idExplore)
                    .collection("tour")
                    .whereLessThan("money", 100)
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (documentSnapshot != null) {
                            ArrayList<TourPopular> listTourNew = new ArrayList<>();
                            for (DocumentSnapshot snapshot : documentSnapshot) {
                                String json = gson.toJson(snapshot.getData());
                                if (json != null) {
                                    TourPopular tourPopular = gson.fromJson(json, TourPopular.class);
                                    listTourNew.add(tourPopular);
                                }
                            }
                            if (listTourNew.size() != 0) {
                                getMvpView.successListTour(listTourNew);
                            } else {
                                getMvpView.showMessage(R.string.msg_get_all_list_city_empty);
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
