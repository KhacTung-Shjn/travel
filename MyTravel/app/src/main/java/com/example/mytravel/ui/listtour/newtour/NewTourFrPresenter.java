package com.example.mytravel.ui.listtour.newtour;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.City;
import com.example.mytravel.models.city.TourPopular;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class NewTourFrPresenter extends BasePresenter implements NewTourFrMvpPresenter {

    private NewTourFrMvpView getMvpView;
    private FirebaseFirestore db;
    private Gson gson;
    private String email;

    public NewTourFrPresenter(NewTourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        this.db = MainApp.getInstance().getFirebaseFireStore();
        gson = new Gson();
        email = getDataManager().getUserInformation().getEmail();
    }

    @Override
    public void getListTour(String idCity, String idExplore) {
        new LoadNewListTourAsyncTask(idCity, idExplore).execute();
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
    class LoadNewListTourAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idCity, idExplore;

        public LoadNewListTourAsyncTask(String idCity, String idExplore) {
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
                    .orderBy("time_create", Query.Direction.DESCENDING)
                    .limit(5)
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
