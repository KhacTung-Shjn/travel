package com.example.mytravel.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.utils.CommonUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mytravel.utils.ConstApp.KEY_USER_INFORMATION;
import static com.example.mytravel.utils.ConstApp.REQUEST_CODE_UPDATE_PROFILE;

public class ProfileFragment extends BaseFragment implements ProfileFrMvpView {
    public static final String TAG = ProfileFragment.class.getSimpleName();
    private ProfileFrMvpPresenter presenter;

    @BindView(R.id.tvNameUser)
    TextView tvNameUser;
    @BindView(R.id.tvEmailUser)
    TextView tvEmailUser;
    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;


    private AlertDialog ratingDialog;
    private UserInformation userInformation;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProfileFrPresenter(this);
        userInformation = getAppDataManager().getUserInformation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }

        if (userInformation != null) {
            if (!TextUtils.isEmpty(userInformation.getName()) && userInformation.getName() != null) {
                tvNameUser.setText(userInformation.getName());
            } else {
                tvNameUser.setText(getString(R.string.text_user_name_default));
            }
            if (userInformation.getEmail() != null && !TextUtils.isEmpty(userInformation.getEmail())) {
                tvEmailUser.setText(userInformation.getEmail());
            } else {
                tvEmailUser.setText("");
            }
            if (!TextUtils.isEmpty(userInformation.getAvatar()) && userInformation.getAvatar() != null) {
                try {
                    Bitmap bitmap = CommonUtils.StringToBitMap(userInformation.getAvatar());
                    ivAvatar.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Toast.makeText(getContext(), getString(R.string.msg_error_unknown), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            tvNameUser.setText(getString(R.string.text_user_name_default));
            tvEmailUser.setText("");
        }
    }

    @OnClick(R.id.btnRating)
    public void onClickRatingApp() {
        if (getActivity() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View mView = getLayoutInflater().inflate(R.layout.dialog_rating, null);
            builder.setView(mView);
            ratingDialog = builder.create();
            Objects.requireNonNull(ratingDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));

            RatingBar ratingBar = mView.findViewById(R.id.rbRating);

            //TODO RATING
            mView.findViewById(R.id.btnDoneRating).setOnClickListener(v -> Toast.makeText(getContext(), "Number Star: " + ratingBar.getRating(), Toast.LENGTH_SHORT).show());

            ratingDialog.setCancelable(true);
            ratingDialog.show();
        }
    }

    @OnClick(R.id.btnSupport)
    public void onClickSupport() {
        if (getActivity() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View mView = getLayoutInflater().inflate(R.layout.dialog_support, null);
            builder.setView(mView);
            ratingDialog = builder.create();
            Objects.requireNonNull(ratingDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));

            mView.findViewById(R.id.btnCall).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0344366899"))));

            ratingDialog.setCancelable(true);
            ratingDialog.show();
        }
    }

    @OnClick(R.id.btnHistory)
    public void onClickHistory() {
        //TODO HISTORY
    }

    @OnClick(R.id.btnMoney)
    public void onClickMoney() {
        //TODO MONEY
    }

    @OnClick(R.id.btnPayment)
    public void onClickPayment() {
        //TODO PAYMENT
    }

    @OnClick(R.id.tvShowDetailUser)
    public void onClickUpdateUser() {
        startActivityForResult(FrameActivity.newIntentUpdateProfile(getContext(), userInformation), REQUEST_CODE_UPDATE_PROFILE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (ratingDialog != null) {
            ratingDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_PROFILE && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getParcelableExtra(KEY_USER_INFORMATION) != null) {
                this.userInformation = data.getParcelableExtra(KEY_USER_INFORMATION);
            }
            if (this.userInformation != null) {
                getAppDataManager().setUserInformation(this.userInformation);
                tvNameUser.setText(this.userInformation.getName());
                tvEmailUser.setText(this.userInformation.getEmail());
            }
        }
    }
}
