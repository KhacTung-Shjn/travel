package com.example.mytravel.ui.favorite.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.imagehot.ImageHot;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.ui.imagehot.ImageHotAdapter;
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoFragment extends BaseFragment implements PhotoFrMvpView, OnClickItem<ImageHot> {
    public static final String TAG = PhotoFragment.class.getSimpleName();

    private PhotoFrMvpPresenter presenter;

    @BindView(R.id.rcvListFavoritesImage)
    RecyclerView rcvListFavoritesImage;

    private ImageHotAdapter imageHotAdapter;
    private ArrayList<ImageHot> listImageHots = new ArrayList<>();
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public static PhotoFragment newInstance() {
        PhotoFragment photoFragment = new PhotoFragment();
        return photoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PhotoFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }

        imageHotAdapter = new ImageHotAdapter(getContext());
        imageHotAdapter.setListUrlImageHot(listImageHots);
        imageHotAdapter.setOnClickItem(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rcvListFavoritesImage.setLayoutManager(staggeredGridLayoutManager);
        rcvListFavoritesImage.setHasFixedSize(true);
        rcvListFavoritesImage.setAdapter(imageHotAdapter);

        presenter.getListFavoritesPhoto();
    }

    @Override
    public void onClickItem(ImageHot imageHot) {
        startActivity(FrameActivity.newIntentFullViewImage(getContext(), imageHot));
    }

    @Override
    public void successGetListFavoritesPhoto(ArrayList<ImageHot> listPhotos) {
        if (listPhotos != null) {
            this.listImageHots = listPhotos;
            imageHotAdapter.replaceAllData(listPhotos);
        }
    }
}
