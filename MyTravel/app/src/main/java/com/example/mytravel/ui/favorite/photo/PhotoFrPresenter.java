package com.example.mytravel.ui.favorite.photo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.favorites.FavoritesExplore;
import com.example.mytravel.models.favorites.FavoritesPhoto;
import com.example.mytravel.models.imagehot.ImageHot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PhotoFrPresenter extends BasePresenter implements PhotoFrMvpPresenter {

    private PhotoFrMvpView getMvpView;

    private String email;
    private ArrayList<FavoritesPhoto> favoritesPhotos;
    private List<String> listIdPhotos = new ArrayList<>();

    public PhotoFrPresenter(PhotoFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        email = getDataManager().getUserInformation().getEmail();
        favoritesPhotos = getDataManager().getListFavoritesPhoto();
        getListIdPhoto();
    }

    private void getListIdPhoto() {
        for (FavoritesPhoto favoritesPhoto : favoritesPhotos) {
            listIdPhotos.add(favoritesPhoto.getIdImagehot());
        }
    }

    @Override
    public void getListFavoritesPhoto() {
        new LoadFavoritesPhotoAsyncTask(listIdPhotos).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoadFavoritesPhotoAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<String> listIdPhotos;

        public LoadFavoritesPhotoAsyncTask(List<String> listIdPhotos) {
            this.listIdPhotos = listIdPhotos;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainApp.getInstance().getFirebaseFireStore()
                    .collection("imagehot")
                    .whereIn("idImagehot", listIdPhotos)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (value != null) {
                            ArrayList<ImageHot> listPhotos = new ArrayList<>();
                            for (DocumentSnapshot snapshot : value) {
                                String json = getGSon().toJson(snapshot.getData());
                                if (json != null) {
                                    ImageHot imageHot = getGSon().fromJson(json, ImageHot.class);
                                    if (imageHot != null) {
                                        listPhotos.add(imageHot);
                                    }
                                }
                            }
                            if (listPhotos.size() != 0) {
                                getMvpView.successGetListFavoritesPhoto(listPhotos);
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
