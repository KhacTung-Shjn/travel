package com.example.mytravel.ui.detailexplore.placelist;

import com.example.mytravel.MainApp;
import com.example.mytravel.base.BasePresenter;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class PlaceListBSFrPresenter extends BasePresenter implements PlaceListBSFrMvpPresenter {

    private PlaceListBSFrMvpView getMvpView;
    private String email;

    public PlaceListBSFrPresenter(PlaceListBSFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        email = getDataManager().getUserInformation().getEmail();
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
}
