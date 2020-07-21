package com.example.mytravel.ui.favorite.place;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceFrPresenter extends BasePresenter implements PlaceFrMvpPresenter {

    private PlaceFrMvpView getMvpView;

    private ArrayList<FavoritesPlace> listFavoritesPlace;
    private String idCity, idExplore;
    private List<String> listIdPlace = new ArrayList<>();
    private String email;

    public PlaceFrPresenter(PlaceFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        listFavoritesPlace = getDataManager().getListFavoritesPlace();
        email = getDataManager().getUserInformation().getEmail();
        if (listFavoritesPlace.size() != 0) {
            idCity = listFavoritesPlace.get(0).getIdCity();
            idExplore = listFavoritesPlace.get(0).getIdExplore();
        }
        getListIdPlace();
    }

    private void getListIdPlace() {
        for (FavoritesPlace favoritesPlace : listFavoritesPlace) {
            listIdPlace.add(favoritesPlace.getIdPlace());
        }
    }

    @Override
    public void getListFavoritesPlace() {
        if (listIdPlace.size() != 0) {
            new LoadFavoritesPlaceAsyncTask(listIdPlace).execute();
        }
    }

    @Override
    public void setLovePlace(String idCity, String idExplore, String idPlace) {
        HashMap<String, Object> love = new HashMap<>();
        love.put("idPlace", idPlace);
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
                                    .collection("favorite_place")
                                    .document(idPlace).set(love);
                        }
                    }
                });
    }

    @Override
    public void removeLovePlace(String idPlace) {
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
                                    .collection("favorite_place")
                                    .document(idPlace).delete();
                        }
                    }
                });
    }

    @SuppressLint("StaticFieldLeak")
    class LoadFavoritesPlaceAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<String> listIdPlace;

        public LoadFavoritesPlaceAsyncTask(List<String> listIdPlace) {
            this.listIdPlace = listIdPlace;
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
                    .whereIn("idPlace", listIdPlace)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (value != null) {
                            ArrayList<Place> listPlaces = new ArrayList<>();
                            for (DocumentSnapshot snapshot : value) {
                                String json = getGSon().toJson(snapshot.getData());
                                if (json != null) {
                                    Place place = getGSon().fromJson(json, Place.class);
                                    if (place != null) {
                                        listPlaces.add(place);
                                    }
                                }
                            }
                            if (listPlaces.size() != 0) {
                                getMvpView.successGetListFavoritesPlace(listPlaces, idCity, idExplore);
                            } else {
                                getMvpView.showMessage(R.string.msg_get_all_list_explore_favorite_empty);
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
