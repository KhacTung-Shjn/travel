package com.example.mytravel.ui.fearture;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_NAME_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;

public class MoreFragment extends BaseFragment implements MoreFrMvpView, View.OnClickListener {
    public static final String TAG = MoreFragment.class.getSimpleName();

    private MoreFrMvpPresenter presenter;

    @BindView(R.id.tvNameExplore)
    TextView tvNameExplore;

    private String idCity;
    private String idExplore;
    private String nameExplore;

    public static MoreFragment newInstance(String idCity, String idExplore, String nameExplore) {
        MoreFragment moreFragment = new MoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        bundle.putString(KEY_NAME_EXPLORE, nameExplore);
        moreFragment.setArguments(bundle);
        return moreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MoreFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fearture_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            ImageView ivBack = getActivity().findViewById(R.id.ivBack);
            ivBack.setOnClickListener(this);
        }
        if (getArguments() != null) {
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);
            nameExplore = getArguments().getString(KEY_NAME_EXPLORE);
        }

        if (!TextUtils.isEmpty(nameExplore)) {
            tvNameExplore.setText(nameExplore);
        }

    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null && getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }
}
