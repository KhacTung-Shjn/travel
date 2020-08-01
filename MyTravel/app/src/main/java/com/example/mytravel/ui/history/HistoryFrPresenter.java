package com.example.mytravel.ui.history;

import com.example.mytravel.MainApp;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.booktour.BookTour;

public class HistoryFrPresenter extends BasePresenter implements HistoryFrMvpPresenter {

    private HistoryFrMvpView getMvpView;
    private String email;

    public HistoryFrPresenter(HistoryFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
        email = getDataManager().getUserInformation().getEmail();
    }

    @Override
    public void removeHistory(BookTour bookTour) {
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
                                    .collection("booktour")
                                    .document(bookTour.getId()).delete();
                            getMvpView.succcessRemove(bookTour);
                        }
                    }
                });
    }
}
