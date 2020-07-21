package com.example.mytravel.ui.favorite.explores;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.Place;
import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.example.mytravel.ui.favorite.place.PlaceFrPresenter;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploreFrPresenter extends BasePresenter implements ExploreFrMvpPresenter {

    private ExploreFrMvpView getMvpView;

    private ArrayList<FavoritesExplore> favoritesExplores;
    private String idCity, idExplore;
    private List<String> listIdExplore = new ArrayList<>();
    private String email;

    public ExploreFrPresenter(ExploreFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;

        favoritesExplores = getDataManager().getListFavoritesExplore();
        email = getDataManager().getUserInformation().getEmail();
        if (favoritesExplores.size() != 0) {
            idCity = favoritesExplores.get(0).getIdCity();
            idExplore = favoritesExplores.get(0).getIdExplore();
        }
        getListIdExplore();

    }

    private void getListIdExplore() {
        for (FavoritesExplore favoritesExplore : favoritesExplores) {
            listIdExplore.add(favoritesExplore.getIdExplore());
        }
    }

    @Override
    public void getListFavoritesExplore() {
        if (listIdExplore.size() != 0) {
            new LoadFavoritesExploreAsyncTask(listIdExplore).execute();
        }
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
    class LoadFavoritesExploreAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<String> listIdExplore;

        public LoadFavoritesExploreAsyncTask(List<String> listIdExplore) {
            this.listIdExplore = listIdExplore;
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
                    .whereIn("id_explore", listIdExplore)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (value != null) {
                            ArrayList<Explore> listExplore = new ArrayList<>();
                            for (DocumentSnapshot snapshot : value) {
                                String json = getGSon().toJson(snapshot.getData());
                                if (json != null) {
                                    Explore explore = getGSon().fromJson(json, Explore.class);
                                    if (explore != null) {
                                        listExplore.add(explore);
                                    }
                                }
                            }
                            if (listExplore.size() != 0) {
                                getMvpView.successGetListFavoritesExplore(listExplore, idCity, idExplore);
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
