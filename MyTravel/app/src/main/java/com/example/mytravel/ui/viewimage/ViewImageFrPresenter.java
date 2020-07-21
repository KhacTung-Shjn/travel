package com.example.mytravel.ui.viewimage;

import com.example.mytravel.MainApp;
import com.example.mytravel.base.BasePresenter;

import java.util.HashMap;

public class ViewImageFrPresenter extends BasePresenter implements ViewImageFrMvpPresenter {

    private ViewImageFrMvpView getMvpView;
    private String email;

    public ViewImageFrPresenter(ViewImageFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        email = getDataManager().getUserInformation().getEmail();
    }

    @Override
    public void setLovePhoto(String idImageHot, String idExplore) {
        HashMap<String, Object> love = new HashMap<>();
        love.put("idImageHot", idImageHot);
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
                                    .collection("favorite_photo")
                                    .document(idImageHot).set(love);
                        }
                    }
                });
    }

    @Override
    public void removeLovePhoto(String idImageHot) {
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
                                    .document(idImageHot).delete();
                        }
                    }
                });
    }
}
