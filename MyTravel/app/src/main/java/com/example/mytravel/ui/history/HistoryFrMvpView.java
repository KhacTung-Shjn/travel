package com.example.mytravel.ui.history;

import com.example.mytravel.base.MvpView;
import com.example.mytravel.models.booktour.BookTour;

public interface HistoryFrMvpView extends MvpView {
    void succcessRemove(BookTour bookTour);
}
