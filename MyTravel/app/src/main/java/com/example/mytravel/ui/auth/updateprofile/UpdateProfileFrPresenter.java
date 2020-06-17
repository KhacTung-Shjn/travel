package com.example.mytravel.ui.auth.updateprofile;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UpdateProfileFrPresenter extends BasePresenter implements UpdateProfileFrMvpPresenter {

    private UpdateProfileFrMvpView getMvpView;

    public UpdateProfileFrPresenter(UpdateProfileFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void onClickUpdateProfile(String name, String email, boolean isMale, boolean isFeMale, String phone, UserInformation userInformation) {
        if (isValidate(name, email)) {
            FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))

            if (user != null) {
                user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updateEmail(email).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                userInformation.setEmail(email);
                                userInformation.setName(name);
                                getMvpView.updateProfileSuccess(userInformation);
                                getMvpView.showMessage(R.string.msg_success_update_profile);
                            } else {
                                getMvpView.showMessage(R.string.msg_error_update_profile);
                            }
                        });
                    } else {
                        getMvpView.showMessage(R.string.msg_error_update_profile);
                    }
                });
            }

        }
    }

    private boolean isValidate(String name, String email) {
        if (TextUtils.isEmpty(name)) {
            getMvpView.showMessage(R.string.msg_error_enter_name);
            getMvpView.focusEdit(0);
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            getMvpView.showMessage(R.string.msg_error_enter_email);
            getMvpView.focusEdit(1);
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView.showMessage(R.string.msg_error_email);
            getMvpView.focusEdit(1);
            return false;
        }
        return true;
    }


}
