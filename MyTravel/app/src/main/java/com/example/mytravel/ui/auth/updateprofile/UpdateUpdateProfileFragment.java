package com.example.mytravel.ui.auth.updateprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mytravel.utils.ConstApp.KEY_USER_INFORMATION;

public class UpdateUpdateProfileFragment extends BaseFragment implements UpdateProfileFrMvpView, View.OnClickListener {
    public static final String TAG = UpdateUpdateProfileFragment.class.getSimpleName();

    private UpdateProfileFrMvpPresenter presenter;

    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tvNameUser)
    TextView tvNameUser;
    @BindView(R.id.tvEmailUser)
    TextView tvEmailUser;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFeMale)
    RadioButton rbFeMale;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    private UserInformation userInformation;

    public static UpdateUpdateProfileFragment newInstance(UserInformation userInformation) {
        UpdateUpdateProfileFragment updateProfileFragment = new UpdateUpdateProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER_INFORMATION, userInformation);
        updateProfileFragment.setArguments(bundle);
        return updateProfileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UpdateProfileFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
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
            userInformation = getArguments().getParcelable(KEY_USER_INFORMATION);
        }

        if (userInformation != null) {
            if (!TextUtils.isEmpty(userInformation.getEmail())) {
                etEmail.setText(userInformation.getEmail());
                tvEmailUser.setText(userInformation.getEmail());
            }
            if (!TextUtils.isEmpty(userInformation.getName())) {
                tvNameUser.setText(userInformation.getName());
                etName.setText(userInformation.getName());
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null) {
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @OnClick(R.id.btnUpdate)
    public void onClickUpdate() {
        presenter.onClickUpdateProfile(etName.getText().toString(), etEmail.getText().toString(),
                rbMale.isChecked(), rbFeMale.isChecked(), etPhone.getText().toString(), userInformation);
    }

    @Override
    public void focusEdit(int i) {
        if (getContext() != null) {
            switch (i) {
                case 0: {
                    CommonUtils.showSoftInput(etName, getContext());
                    break;
                }
                case 1: {
                    CommonUtils.showSoftInput(etEmail, getContext());
                    break;
                }
            }
        }
    }

    @Override
    public void updateProfileSuccess(UserInformation userInformation) {
        if (getActivity() != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_USER_INFORMATION, userInformation);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }
}
