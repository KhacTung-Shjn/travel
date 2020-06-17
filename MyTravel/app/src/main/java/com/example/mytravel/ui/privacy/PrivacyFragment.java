package com.example.mytravel.ui.privacy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chaos.view.PinView;
import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.ui.main.MainActivity;
import com.example.mytravel.ui.sample.SampleFragment;
import com.example.mytravel.utils.CommonUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.KEY_CHECK_FROM_SPLASH;
import static com.example.mytravel.utils.ConstApp.KEY_CONFIRM_PASS_CODE;

public class PrivacyFragment extends BaseFragment implements PrivacyFrMvpView, View.OnClickListener {
    public static final String TAG = PrivacyFragment.class.getSimpleName();
    private PrivacyFrMvpPresenter presenter;

    @BindView(R.id.pinView)
    PinView pinView;
    @BindView(R.id.pinViewConfirm)
    PinView pinViewConfirm;
    @BindView(R.id.tvTitlePrivacy)
    TextView tvTitlePrivacy;

    private String password;
    private String passwordConfirm;
    private ImageView ivBack;
    private boolean isFromSplash = false;

    public static PrivacyFragment newInstance(boolean isFromSplash) {
        PrivacyFragment privacyFragment = new PrivacyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_CHECK_FROM_SPLASH, isFromSplash);
        privacyFragment.setArguments(bundle);
        return privacyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PrivacyFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_privacy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }
        if (getArguments() != null) {
            isFromSplash = getArguments().getBoolean(KEY_CHECK_FROM_SPLASH);
        }

        if (!isFromSplash) {
            ivBack = Objects.requireNonNull(getActivity()).findViewById(R.id.ivBack);
            ivBack.setOnClickListener(this);
        }

        new Handler().postDelayed(() -> {
            CommonUtils.showSoftInput(pinView, view.getContext());
        }, 300);
        pinView.setItemRadius(getResources().getDimensionPixelSize(R.dimen.size_24dp));
        pinView.setAnimationEnable(true);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    if (!isFromSplash) {
                        password = s.toString();
                        pinView.setVisibility(View.GONE);
                        pinViewConfirm.setVisibility(View.VISIBLE);
                        if (getContext() != null) {
                            tvTitlePrivacy.setText(getContext().getString(R.string.confirm_password));
                            CommonUtils.showSoftInput(pinViewConfirm, getContext());
                        }
                    } else {
                        if (s.toString().equals(MainApp.getInstance().getAppDataManager().getPassCode())) {
                            if (getActivity() != null) {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.msg_error_pass_not_match_re_pass), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pinViewConfirm.setItemRadius(getResources().getDimensionPixelSize(R.dimen.size_24dp));
        pinViewConfirm.setAnimationEnable(true);
        pinViewConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    passwordConfirm = s.toString();
                    if (passwordConfirm.equals(password)) {
                        MainApp.getInstance().getAppDataManager().setPassCode(passwordConfirm);
                        backToMainActivity(true);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.msg_error_pass_not_match_re_pass), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void backToMainActivity(boolean isSuccess) {
        if (getActivity() != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_CONFIRM_PASS_CODE, isSuccess);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View v) {
        backToMainActivity(false);
    }
}
