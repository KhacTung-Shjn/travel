package com.example.mytravel.ui.detailexplore;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.models.city.PlaceHot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DetailExploreFrPresenter extends BasePresenter implements DetailExploreFrMvpPresenter {

    private DetailExploreFrMvpView getMvpView;
    private String data;
    private ArrayList<PlaceHot> listHotMarker = new ArrayList<>();
    private String email;

    public DetailExploreFrPresenter(DetailExploreFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        email = getDataManager().getUserInformation().getEmail();
    }

    @Override
    public void getListPlaces(String idCity, String idExplore, String nameExplore) {
        new LoadListPlaceAsyncTask(idCity, idExplore, nameExplore).execute();
    }

    @Override
    public void getAllListHotMarker() {
        new LoadListHotMarkerAsyncTask().execute();
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

    @SuppressLint("StaticFieldLeak")
    class LoadListHotMarkerAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainApp.getInstance().getFirebaseFireStore()
                    .collection("placehot")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (value != null) {
                            ArrayList<PlaceHot> listHotMarker = new ArrayList<>();
                            for (DocumentSnapshot snapshot1 : value) {
                                String json = getGSon().toJson(snapshot1.getData());
                                if (json != null) {
                                    PlaceHot placeHot = getGSon().fromJson(json, PlaceHot.class);
                                    if (placeHot != null) {
                                        listHotMarker.add(placeHot);
                                    }
                                }
                            }
                            if (listHotMarker.size() != 0) {
                                getMvpView.successGetHotMarker(listHotMarker);
                            } else {
                                getMvpView.showMessage(R.string.msg_get_all_list_hot_marker_empty);
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
