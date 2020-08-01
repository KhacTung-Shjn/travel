package com.example.mytravel.ui.booktour;

import android.text.TextUtils;

import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.booktour.BookTour;

public class BookTourFrPresenter extends BasePresenter implements BookTourFrMvpPresenter {

    private BookTourFrMvpView getMvpView;

    public BookTourFrPresenter(BookTourFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void requestBookTour(String checkIn, String checkOut, String adults, String children, String name, String email, String phone, long miniSecondCheckIn, long miniSecondCheckOut) {
        if (isValidate(checkIn, checkOut, adults, children, name, email, phone, miniSecondCheckIn, miniSecondCheckOut)) {
            BookTour bookTour = new BookTour(checkIn, checkOut, adults, children, name, email, phone, miniSecondCheckIn, miniSecondCheckOut);
            getMvpView.successRequestBookTour(bookTour);
        }
    }

    private boolean isValidate(String checkIn, String checkOut, String adults, String children, String name, String email, String phone, long miniSecondCheckIn, long miniSecondCheckOut) {
        if (checkIn.equals("dd/mm/yyyy") || miniSecondCheckIn == 0) {
            getMvpView.showMessage(R.string.msg_error_choose_date_check_in);
            return false;
        }
        if (checkOut.equals("dd/mm/yyyy") || miniSecondCheckOut == 0) {
            getMvpView.showMessage(R.string.msg_error_choose_date_check_out);
            return false;
        }

        if (adults.equals("0")) {
            getMvpView.showMessage(R.string.msg_error_choose_number_adults);
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            getMvpView.showMessage(R.string.msg_error_enter_name);
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            getMvpView.showMessage(R.string.msg_error_enter_name);
            return false;
        }

        return true;
    }
}
