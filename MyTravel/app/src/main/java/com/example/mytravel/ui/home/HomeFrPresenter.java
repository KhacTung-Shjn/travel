package com.example.mytravel.ui.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.booktour.BookTour;
import com.example.mytravel.models.city.City;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
        new LoadHomeAsyncTask().execute();
    }

    @Override
    public void getListBookTour() {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("user")
                .whereEqualTo("email", getDataManager().getUserInformation().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            MainApp.getInstance().getFirebaseFireStore()
                                    .collection("user")
                                    .document(task.getResult().getDocuments().get(0).getId())
                                    .collection("booktour")
                                    .addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            getMvpView.showMessage(R.string.msg_error_unknown);
                                        }
                                        if (value != null) {
                                            ArrayList<BookTour> listBookTours = new ArrayList<>();
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                String json = getGSon().toJson(snapshot.getData());
                                                if (json != null) {
                                                    BookTour bookTour = getGSon().fromJson(json, BookTour.class);
                                                    listBookTours.add(bookTour);
                                                }
                                            }
                                            getDataManager().setListBookTour(listBookTours);
                                        }
                                    });
                        }
                    }
                });
    }


    @SuppressLint("StaticFieldLeak")
    class LoadHomeAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getMvpView.hideLoading();
        }
    }
}
