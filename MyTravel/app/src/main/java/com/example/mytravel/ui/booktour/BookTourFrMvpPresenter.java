package com.example.mytravel.ui.booktour;

public interface BookTourFrMvpPresenter {
    void requestBookTour(String checkIn, String checkOut, String adults, String children,
                         String name, String email, String phone,
                         long miniSecondCheckIn, long miniSecondCheckOut);
}
