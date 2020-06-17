package com.example.mytravel.ui.auth.register;

import android.app.Activity;
import android.text.TextUtils;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFrPresenter extends BasePresenter implements RegisterFrMvpPresenter {

    private RegisterFrMvpView getMvpView;

    public RegisterFrPresenter(RegisterFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void signUpNewUser(Activity activity, String nameUser, String password, String rePassword, String email) {
        if (isValidate(nameUser, password, rePassword, email)) {
            MainApp.getInstance().getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, (OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameUser).build();
//                                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                            if (task.getResult() != null) {
                                if (task.getResult().getUser() != null) {
                                    task.getResult().getUser().updateProfile(profileUpdates);
                                }
                            }
                            getMvpView.showMessage(R.string.msg_success_sign_up);
                            getMvpView.registerSuccess();
                        } else {
                            getMvpView.showMessage(R.string.msg_error_sign_up);
                        }
                    });
        }
    }

    private boolean isValidate(String nameUser, String password, String rePassword, String
            email) {
        if (TextUtils.isEmpty(nameUser)) {
            getMvpView.showMessage(R.string.msg_error_enter_name);
            getMvpView.focusEdit(0);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView.showMessage(R.string.msg_error_enter_password);
            getMvpView.focusEdit(1);
            return false;
        }

        if (password.length() < 6) {
            getMvpView.showMessage(R.string.msg_error_length_password);
            getMvpView.focusEdit(1);
            return false;
        }

        if (TextUtils.isEmpty(rePassword)) {
            getMvpView.showMessage(R.string.msg_error_enter_re_password);
            getMvpView.focusEdit(2);
            return false;
        }

        if (!rePassword.equals(password)) {
            getMvpView.showMessage(R.string.msg_error_pass_not_match_re_pass);
            getMvpView.focusEdit(2);
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            getMvpView.showMessage(R.string.msg_error_enter_email);
            getMvpView.focusEdit(3);
            return false;
        }

        if (!CommonUtils.isEmailValid(email)) {
            getMvpView.showMessage(R.string.msg_error_email);
            getMvpView.focusEdit(3);
            return false;
        }

        return true;
    }

}
