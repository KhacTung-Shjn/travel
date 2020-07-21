package com.example.mytravel.ui.imagehot;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.imagehot.ImageHot;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.utils.OnClickItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;

public class ImageHotFragment extends BaseFragment implements ImageHotFrMvpView, View.OnClickListener, OnClickItem<ImageHot> {
    public static final String TAG = ImageHotFragment.class.getSimpleName();

    private ImageHotFrMvpPresenter presenter;

    @BindView(R.id.rcvListImageHot)
    RecyclerView rcvListImageHot;

    private String idExplore;
    private ImageHotAdapter imageHotAdapter;
    private ArrayList<ImageHot> listImageHots = new ArrayList<>();
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    //private GridLayoutManager gridLayoutManager;

    public static ImageHotFragment newInstance(String idExplore) {
        ImageHotFragment imageHotFragment = new ImageHotFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        imageHotFragment.setArguments(bundle);
        return imageHotFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ImageHotFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            getActivity().findViewById(R.id.ivBack).setOnClickListener(this);
        }
        if (getArguments() != null) {
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);
        }
        if (idExplore != null && !TextUtils.isEmpty(idExplore)) {
            presenter.getListImageHot(idExplore);
        }

        if (getContext() != null) {
            imageHotAdapter = new ImageHotAdapter(getContext());
            imageHotAdapter.setListUrlImageHot(listImageHots);
            imageHotAdapter.setOnClickItem(this);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            rcvListImageHot.setLayoutManager(staggeredGridLayoutManager);
            rcvListImageHot.setHasFixedSize(true);
            rcvListImageHot.setAdapter(imageHotAdapter);
        }
    }

    @Override
    public void successGetListImageHot(ArrayList<ImageHot> listImageHots) {
        if (listImageHots != null) {
            this.listImageHots = listImageHots;
            imageHotAdapter.replaceAllData(this.listImageHots);
        }
    }

    @Override
    public void onClick(View v) {
        if (getFragmentManager() != null && getActivity() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @Override
    public void onClickItem(ImageHot imageHot) {
        startActivity(FrameActivity.newIntentFullViewImage(getContext(), imageHot));
    }
}
