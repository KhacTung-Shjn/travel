package com.example.mytravel.ui.auth.updateprofile;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BasePresenter;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class UpdateProfileFrPresenter extends BasePresenter implements UpdateProfileFrMvpPresenter {

    private UpdateProfileFrMvpView getMvpView;

    public UpdateProfileFrPresenter(UpdateProfileFrMvpView getMvpView) {
        super(getMvpView);
        this.getMvpView = getMvpView;
    }

    @Override
    public void onClickUpdateProfile(String name, String email, boolean isMale, boolean isFeMale, String phone, String address, String birth, UserInformation userInformation, String avatar) {
        if (isValidate(name, email)) {
            FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))

            if (user != null) {
                user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        user.updateEmail(email).addOnCompleteListener(task1 -> {
//                            if (task1.isSuccessful()) {
////                                userInformation.setEmail(email);
////                                userInformation.setName(name);
////                                getMvpView.updateProfileSuccess(userInformation);
////                                getMvpView.showMessage(R.string.msg_success_update_profile);
//                            } else {
//                                getMvpView.showMessage(R.string.msg_error_update_profile);
//                            }
//                        });
                    } else {
                        getMvpView.showMessage(R.string.msg_error_update_profile);
                    }
                });

                //update profile
                HashMap<String, Object> updateProfile = new HashMap<>();
                updateProfile.put("address", address);
                updateProfile.put("avatar", avatar);
                updateProfile.put("birth", birth);
                updateProfile.put("email", email);
                updateProfile.put("gender", isMale ? 1 : 0);
                updateProfile.put("name", name);
                updateProfile.put("phone", phone);

                MainApp.getInstance().getFirebaseFireStore()
                        .collection("user")
                        .whereEqualTo("email", userInformation.getEmail())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    MainApp.getInstance().getFirebaseFireStore()
                                            .collection("user")
                                            .document(task.getResult().getDocuments().get(0).getId())
                                            .update(updateProfile)
                                            .addOnSuccessListener(aVoid -> {
                                                userInformation.setEmail(email);
                                                userInformation.setName(name);
                                                userInformation.setAddress(address);
                                                userInformation.setAvatar(avatar);
                                                userInformation.setPhone(phone);
                                                userInformation.setBirth(birth);
                                                userInformation.setGender(isMale ? 1 : 0);
                                                getMvpView.updateProfileSuccess(userInformation);
                                                getMvpView.showMessage(R.string.msg_success_update_profile);
                                            })
                                            .addOnFailureListener(e -> {
                                                getMvpView.showMessage(R.string.msg_error_update_profile);
                                            });
                                }
                            } else {
                                getMvpView.showMessage(R.string.msg_error_unknown);
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
