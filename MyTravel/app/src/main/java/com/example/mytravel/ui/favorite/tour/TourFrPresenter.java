package com.example.mytravel.ui.favorite.tour;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.models.favorites.FavoritesTour;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourFrPresenter extends BasePresenter implements TourFrMvpPresenter {

    private TourFrMvpView getMvpView;

    private ArrayList<FavoritesTour> favoritesTours;
    private String idCity, idExplore;
    private List<String> listIdTour = new ArrayList<>();
    private String email;

    public TourFrPresenter(TourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;

        favoritesTours = getDataManager().getListFavoritesTour();
        email = getDataManager().getUserInformation().getEmail();
        if (favoritesTours.size() != 0) {
            idCity = favoritesTours.get(0).getIdCity();
            idExplore = favoritesTours.get(0).getIdExplore();
        }
        getListIdTour();
    }

    private void getListIdTour() {
        for (FavoritesTour tour : favoritesTours) {
            listIdTour.add(tour.getIdTour());
        }
    }

    @Override
    public void getListFavoritesTour() {
        new LoadFavoritesTourAsyncTask(listIdTour).execute();
    }

    @Override
    public void setLoveTour(String idCity, String idExplore, String idTour) {
        HashMap<String, Object> love = new HashMap<>();
        love.put("idCity", idCity);
        love.put("idExplore", idExplore);
        love.put("idTour", idTour);

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
                                    .collection("favorite_tours")
                                    .document(idTour).set(love);
                        }
                    }
                });
    }

    @Override
    public void removeLoveTour(String idTour) {
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
                                    .collection("favorite_tours")
                                    .document(idTour).delete();
                        }
                    }
                });
    }

    @SuppressLint("StaticFieldLeak")
    class LoadFavoritesTourAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<String> listIdTour;

        public LoadFavoritesTourAsyncTask(List<String> listIdTour) {
            this.listIdTour = listIdTour;
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
                    .collection("tour")
                    .whereIn("id_tour", listIdTour)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (value != null) {
                            ArrayList<TourPopular> listTours = new ArrayList<>();
                            for (DocumentSnapshot snapshot : value) {
                                String json = getGSon().toJson(snapshot.getData());
                                if (json != null) {
                                    TourPopular tourPopular = getGSon().fromJson(json, TourPopular.class);
                                    if (tourPopular != null) {
                                        listTours.add(tourPopular);
                                    }
                                }
                            }
                            if (listTours.size() != 0) {
                                getMvpView.successGetListFavoritesTour(listTours, idCity, idExplore);
                            } else {
                                getMvpView.showMessage(R.string.msg_get_all_list_tour_favorite_empty);
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
