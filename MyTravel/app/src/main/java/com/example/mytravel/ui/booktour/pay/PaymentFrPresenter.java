package com.example.mytravel.ui.booktour.pay;

import com.example.mytravel.MainApp;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.booktour.BookTour;

import java.util.HashMap;

public class PaymentFrPresenter extends BasePresenter implements PaymentFrMvpPresenter {

    private PaymentFrMvpView getMvpView;

    public PaymentFrPresenter(PaymentFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void pushBookTour(BookTour bookTour) {
        HashMap<String, Object> book = new HashMap<>();
        book.put("idBookTour", bookTour.getId());
        book.put("checkIn", bookTour.getCheckIn());
        book.put("checkOut", bookTour.getCheckOut());
        book.put("numberAdults", bookTour.getNumberAdults());
        book.put("numberChildren", bookTour.getNumberChildren());
        book.put("nameUser", bookTour.getNameUser());
        book.put("emailUser", bookTour.getEmailUser());
        book.put("phoneUser", bookTour.getPhoneUser());
        book.put("miniSecondCheckIn", bookTour.getMiniSecondCheckIn());
        book.put("miniSecondCheckOut", bookTour.getMiniSecondCheckOut());
        book.put("nameTour", bookTour.getNameTour());
        book.put("priceTour", bookTour.getPriceTour());
        book.put("urlImageTour", bookTour.getUrlImageTour());


        MainApp.getInstance().getFirebaseFireStore()
                .collection("user")
                .whereEqualTo("email", bookTour.getEmailUser())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            MainApp.getInstance().getFirebaseFireStore()
                                    .collection("user")
                                    .document(task.getResult().getDocuments().get(0).getId())
                                    .collection("booktour")
                                    .document(bookTour.getId()).set(book);

                            getMvpView.successPush();
                        }

                    }
                });
    }
}
