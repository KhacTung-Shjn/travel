package com.example.mytravel.ui.viewimage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.favorites.FavoritesPhoto;
import com.example.mytravel.models.imagehot.ImageHot;
import com.example.mytravel.utils.CommonUtils;
import com.example.mytravel.utils.TouchImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mytravel.utils.ConstApp.KEY_ITEM_IMAGE_HOT;

public class ViewImageFragment extends BaseFragment implements ViewImageFrMvpView {
    public static final String TAG = ViewImageFragment.class.getSimpleName();

    private ViewImageFrMvpPresenter presenter;

    @BindView(R.id.tivFullImage)
    TouchImageView tivFullImage;
    @BindView(R.id.cvExit)
    CardView cvExit;
    @BindView(R.id.isLoveImageHot)
    TextView tvLoveImageHot;

    private ImageHot imageHot;
    private ArrayList<FavoritesPhoto> favoritesPhotos;
    private ArrayList<String> listIdPhoto = new ArrayList<>();

    public static ViewImageFragment newInstance(ImageHot imageHot) {
        ViewImageFragment viewImageFragment = new ViewImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ITEM_IMAGE_HOT, imageHot);
        viewImageFragment.setArguments(bundle);
        return viewImageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ViewImageFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            RelativeLayout rlActionBar = getActivity().findViewById(R.id.rlActionBar);
            View viewActionBar = getActivity().findViewById(R.id.viewActionBar);
            if (rlActionBar != null && viewActionBar != null) {
                rlActionBar.setVisibility(View.GONE);
                viewActionBar.setVisibility(View.GONE);
            }
        }
        favoritesPhotos = getAppDataManager().getListFavoritesPhoto();
        for (FavoritesPhoto photo : favoritesPhotos) {
            listIdPhoto.add(photo.getIdImagehot());
        }
        if (getArguments() != null) {
            imageHot = getArguments().getParcelable(KEY_ITEM_IMAGE_HOT);
        }
        if (imageHot != null) {
            if (!TextUtils.isEmpty(imageHot.getUrlImageHot())) {
                CommonUtils.loadImage(getContext(), imageHot.getUrlImageHot(), tivFullImage);
            }
            tvLoveImageHot.setSelected(listIdPhoto.contains(imageHot.getIdImageHot()));
        }
    }

    @OnClick(R.id.isLoveImageHot)
    public void onClickLoveImage() {
        tvLoveImageHot.setSelected(!tvLoveImageHot.isSelected());
        if (tvLoveImageHot.isSelected()) {
            presenter.setLovePhoto(imageHot.getIdImageHot(), imageHot.getIdExplore());
        } else {
            presenter.removeLovePhoto(imageHot.getIdImageHot());
        }
    }

    @OnClick(R.id.cvExit)
    public void onClickExitImage() {
        if (getFragmentManager() != null && getActivity() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }
}
