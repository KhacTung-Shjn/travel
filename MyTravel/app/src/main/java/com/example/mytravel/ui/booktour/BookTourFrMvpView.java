package com.example.mytravel.ui.booktour;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.booktour.BookTour;

public interface BookTourFrMvpView extends MvpView {
    void successRequestBookTour(BookTour bookTour);
}
