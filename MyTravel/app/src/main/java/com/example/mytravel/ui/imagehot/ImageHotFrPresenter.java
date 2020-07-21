package com.example.mytravel.ui.imagehot;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.city.City;
import com.example.mytravel.models.imagehot.ImageHot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class ImageHotFrPresenter extends BasePresenter implements ImageHotFrMvpPresenter {

    private ImageHotFrMvpView getMvpView;
    private String data;

    public ImageHotFrPresenter(ImageHotFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void getListImageHot(String idExplore) {
        new LoadListImageHotsAsyncTask(idExplore).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoadListImageHotsAsyncTask extends AsyncTask<Void, Void, Void> {
        private String idExplore;

        public LoadListImageHotsAsyncTask(String idExplore) {
            this.idExplore = idExplore;
        }

        @Override
        protected void onPreExecute() {
            getMvpView.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainApp.getInstance().getFirebaseFireStore()
                    .collection("imagehot")
                    .whereEqualTo("idExplore", idExplore)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            getMvpView.showMessage(R.string.msg_error_unknown);
                        }
                        if (value != null) {
                            ArrayList<ImageHot> listImageHots = new ArrayList<>();
                            for (DocumentSnapshot snapshot : value) {
                                data = getGSon().toJson(snapshot.getData());
                                if (data != null) {
                                    ImageHot imageHot = getGSon().fromJson(data, ImageHot.class);
                                    listImageHots.add(imageHot);
                                }
                            }
                            if (listImageHots.size() != 0) {
                                getMvpView.successGetListImageHot(listImageHots);
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
