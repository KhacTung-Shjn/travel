package com.example.mytravel.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.ui.auth.register.RegisterFragment;
import com.example.mytravel.ui.main.MainActivity;
import com.example.mytravel.utils.AppUtils;
import com.example.mytravel.utils.CommonUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginFrMvpView, FacebookCallback<LoginResult> {
    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private LoginFrMvpPresenter presenter;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivShowHidePass)
    ImageView ivShowHidePass;
    @BindView(R.id.cbLogged)
    CheckBox cbLogged;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.login_button)
    LoginButton btnLoginFacebook;

    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
        }

        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), googleSignInOptions);

        // Configure Facebook Sign In
        mCallbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setFragment(this);
        btnLoginFacebook.setReadPermissions("public_profile email");
        btnLoginFacebook.registerCallback(mCallbackManager, this);

    }

    @OnClick(R.id.btnSignIn)
    public void onClickLogin() {
        presenter.signInUser(getActivity(), etEmail.getText().toString(), etPassword.getText().toString(), cbLogged.isChecked());
    }

    @OnClick(R.id.tvRegister)
    public void onClickRegister() {
        if (getFragmentManager() != null) {
            AppUtils.replaceFragment(getFragmentManager(),
                    R.id.frAuth, RegisterFragment.newInstance(),
                    true, RegisterFragment.TAG);
        }
    }

    @OnClick(R.id.ivShowHidePass)
    public void onClickVisiblePassword() {
        ivShowHidePass.setSelected(!ivShowHidePass.isSelected());
        etPassword.setTransformationMethod(ivShowHidePass.isSelected() ? null : new PasswordTransformationMethod());
        etPassword.setSelection(etPassword.getText().toString().length());
    }

    @OnClick(R.id.ivFacebookLogin)
    public void onClickLoginWithFacebook() {
        btnLoginFacebook.performClick();
        try {
            // LoginManager.getInstance().registerCallback(mCallbackManager, this);
            LoginManager.getInstance().logInWithReadPermissions(getActivity(), Collections.singletonList("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ivGoogleLogin)
    public void onClickLoginWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void focusEdit(int i) {
        if (getContext() != null) {
            switch (i) {
                case 0: {
                    CommonUtils.showSoftInput(etEmail, getContext());
                    break;
                }
                case 1: {
                    CommonUtils.showSoftInput(etPassword, getContext());
                    break;
                }
            }
        }
    }

    @Override
    public void loginSuccess(FirebaseUser user) {
        if (getActivity() != null) {
            getAppDataManager().setPasswordCurrent(etPassword.getText().toString());
            UserInformation userInformation = CommonUtils.getUserInfo(user);
            presenter.getProfile(userInformation);
        }
    }

    @Override
    public void getFinalLogin(UserInformation userInformation) {
        getAppDataManager().setUserInformation(userInformation);
        startActivity(new Intent(getActivity(), MainActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    presenter.firebaseAuthWithGoogle(getActivity(), account.getIdToken(), cbLogged.isChecked());
                }
            } catch (ApiException e) {
                showMessage(R.string.msg_error_sign_in_google);
            }
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        //handleFacebookAccessToken(loginResult.getAccessToken());
        getAppDataManager().setCheckLogin(true);
        showMessage(R.string.msg_success_login);
        presenter.getProfile(new UserInformation("customer@gmail.com"));

//        if (getActivity() != null) {
//            getAppDataManager().setCheckLogin(true);
//            startActivity(new Intent(getActivity(), MainActivity.class));
//            getActivity().finish();
//        }
    }

    @Override
    public void onCancel() {
        showMessage(R.string.msg_cancel_facebook);
    }

    @Override
    public void onError(FacebookException error) {
        Log.d(TAG, Objects.requireNonNull(error.getMessage()));
        showMessage(R.string.msg_error_login_facebook);
    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        MainApp.getInstance().getAuth().signInWithCredential(credential)
//                .addOnCompleteListener(getBaseActivity(), task -> {
//                    if (task.isSuccessful()) {
//                        showMessage(R.string.msg_success_login);
//                        FirebaseUser user = MainApp.getInstance().getAuth().getCurrentUser();
//                        loginSuccess(user);
//                    } else {
//                        Log.d(TAG, task.getException().getMessage());
//                        showMessage(task.getException().getMessage());
//                    }
//                });
//    }
}
