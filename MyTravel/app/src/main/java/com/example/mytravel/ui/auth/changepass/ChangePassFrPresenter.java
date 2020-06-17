package com.example.mytravel.ui.auth.changepass;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassFrPresenter extends BasePresenter implements ChangePassFrMvpPresenter {

    private ChangePassFrMvpView getMvpView;

    public ChangePassFrPresenter(ChangePassFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }


    @Override
    public void onClickChangePassword(String password, String newPassword, String rePassword, String currentPassWord) {
        if (isValidate(password, newPassword, rePassword, currentPassWord)) {
            FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();
            if (user != null) {
                user.updatePassword(newPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                getDataManager().setPasswordCurrent(newPassword);
                                getMvpView.showMessage(R.string.msg_success_change_pass);
                                getMvpView.changePasswordSuccess();
                            }
                        });
            }
        }
    }

    private boolean isValidate(String password, String newPassword, String rePassword, String currentPassWord) {
        if (TextUtils.isEmpty(password)) {
            getMvpView.showMessage(R.string.msg_error_enter_password);
            getMvpView.focusEdit(0);
            return false;
        }
        if (TextUtils.isEmpty(newPassword)) {
            getMvpView.showMessage(R.string.msg_error_enter_new_password);
            getMvpView.focusEdit(1);
            return false;
        }
        if (TextUtils.isEmpty(rePassword)) {
            getMvpView.showMessage(R.string.msg_error_enter_re_password);
            getMvpView.focusEdit(2);
            return false;
        }

        if (!password.equals(currentPassWord)) {
            getMvpView.showMessage(R.string.msg_error_confirm_password);
            getMvpView.focusEdit(1);
            return false;
        }

        if (!newPassword.equals(rePassword)) {
            getMvpView.showMessage(R.string.msg_error_pass_not_match_re_pass);
            getMvpView.focusEdit(2);
            return false;
        }

        if (password.equals(newPassword)) {
            getMvpView.showMessage(R.string.msg_error_pass_not_new);
            getMvpView.focusEdit(1);
            return false;
        }


        return true;
    }


}
