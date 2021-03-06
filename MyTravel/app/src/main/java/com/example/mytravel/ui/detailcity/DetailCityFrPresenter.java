package com.example.mytravel.ui.detailcity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailCityFrPresenter extends BasePresenter implements DetailCityFrMvpPresenter {

    private DetailCityFrMvpView getMvpView;

    private String data;
    private String email;

    public DetailCityFrPresenter(DetailCityFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        email = getDataManager().getUserInformation().getEmail();
    }

    @Override
    public void getListExplore(String idCity) {
        new LoadListExploreAsyncTask(idCity).execute();
    }

    @Override
    public void getListTourPopular(String idCity) {
        new LoadListTourPopularAsyncTask(idCity).execute();
    }

    @Override
    public void setLoveExplore(String idCity, String idExplore) {
        HashMap<String, Object> love = new HashMap<>();
        love.put("idCity", idCity);
        love.put("idExplore", idExplore);

        MainApp.getInstance().getFirebaseFireStore()
                .collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            MainApp.getInstance().getFirebaseFireStore()
                                    .collection("user")
                                    .document(task.getResult().getDocuments().get(0).getId())
                                    .collection("favorite_explore")
                                    .document(idExplore).set(love);
                        }
                    }
                });
    }

    @Override
    public void removeLoveExplore(String idExplore) {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            MainApp.getInstance().getFirebaseFireStore()
                                    .collection("user")
                                    .document(task.getResult().getDocuments().get(0).getId())
                                    .collection("favorite_explore")
                                    .document(idExplore).delete();
                        }
                    }
                });
    }


    @SuppressLint("StaticFieldLeak")
    class LoadListExploreAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idCity;

        public LoadListExploreAsyncTask(String idCity) {
            this.idCity = idCity;
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getMvpView.hideLoading();
        }
    }


    @SuppressLint("StaticFieldLeak")
    class LoadListTourPopularAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idCity;

        public LoadListTourPopularAsyncTask(String idCity) {
            this.idCity = idCity;
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getMvpView.hideLoading();
        }
    }
}
