package com.example.mytravel.ui.favorite;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.models.favorites.FavoritesPhoto;
import com.example.mytravel.models.favorites.FavoritesPlace;
import com.example.mytravel.models.favorites.FavoritesTour;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FavoriteFrPresenter extends BasePresenter implements FavoriteFrMvpPresenter {

    private FavoriteFrMvpView getMvpView;


    public FavoriteFrPresenter(FavoriteFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;

    }

    @Override
    public void getFavorites(String email) {
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
                                    .addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            getMvpView.showMessage(R.string.msg_error_unknown);
                                        }
                                        if (value != null) {
                                            ArrayList<FavoritesPlace> favoritesPlaces = new ArrayList<>();
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                String json = getGSon().toJson(snapshot.getData());
                                                if (json != null) {
                                                    FavoritesPlace favoritesPlace = getGSon().fromJson(json, FavoritesPlace.class);
                                                    favoritesPlaces.add(favoritesPlace);
                                                }
                                            }
                                            getDataManager().setListFavoritesPlace(favoritesPlaces);
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void getFavoritesExplore(String email) {
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
                                    .addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            getMvpView.showMessage(R.string.msg_error_unknown);
                                        }
                                        if (value != null) {
                                            ArrayList<FavoritesExplore> favoritesExplores = new ArrayList<>();
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                String json = getGSon().toJson(snapshot.getData());
                                                if (json != null) {
                                                    FavoritesExplore favoritesExplore = getGSon().fromJson(json, FavoritesExplore.class);
                                                    favoritesExplores.add(favoritesExplore);
                                                }
                                            }
                                            getDataManager().setListFavoritesExplore(favoritesExplores);
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void getFavoritesPhoto(String email) {
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
                                    .collection("favorite_photo")
                                    .addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            getMvpView.showMessage(R.string.msg_error_unknown);
                                        }
                                        if (value != null) {
                                            ArrayList<FavoritesPhoto> favoritesPhotos = new ArrayList<>();
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                String json = getGSon().toJson(snapshot.getData());
                                                if (json != null) {
                                                    FavoritesPhoto favoritesPhoto = getGSon().fromJson(json, FavoritesPhoto.class);
                                                    favoritesPhotos.add(favoritesPhoto);
                                                }
                                            }
                                            getDataManager().setListFavoritesPhoto(favoritesPhotos);
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void getFavoritesTour(String email) {
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
                                    .addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            getMvpView.showMessage(R.string.msg_error_unknown);
                                        }
                                        if (value != null) {
                                            ArrayList<FavoritesTour> favoritesTours = new ArrayList<>();
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                String json = getGSon().toJson(snapshot.getData());
                                                if (json != null) {
                                                    FavoritesTour favoritesTour = getGSon().fromJson(json, FavoritesTour.class);
                                                    favoritesTours.add(favoritesTour);
                                                }
                                            }
                                            getDataManager().setListFavoritesTour(favoritesTours);
                                        }
                                    });
                        }
                    }
                });
    }
}
