package com.example.mytravel.ui.frame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;
import com.example.mytravel.models.FragmentController;
import com.example.mytravel.models.city.City;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.ui.auth.changepass.ChangePassFragment;
import com.example.mytravel.ui.auth.updateprofile.UpdateUpdateProfileFragment;
import com.example.mytravel.ui.detailcity.DetailCityFragment;
import com.example.mytravel.ui.privacy.PrivacyFragment;
import com.example.mytravel.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.DIRECT_CHANGE_PASS;
import static com.example.mytravel.utils.ConstApp.DIRECT_DETAIL_CITY;
import static com.example.mytravel.utils.ConstApp.DIRECT_PRIVACY;
import static com.example.mytravel.utils.ConstApp.DIRECT_UPDATE_PROFILE;
import static com.example.mytravel.utils.ConstApp.DIRECT_VERIFY;
import static com.example.mytravel.utils.ConstApp.KEY_ITEM_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_USER_INFORMATION;

public class FrameActivity extends BaseActivity implements FrameMvpView {
    public static final String TAG = FrameActivity.class.getSimpleName();

    public FrameMvpPresenter presenter;

    @BindView(R.id.frFrame)
    FrameLayout frFrame;
    @BindView(R.id.tvTitleFrame)
    TextView tvTitleFrame;


    private String direct;
    private AsyncTaskExecutor asyncTaskExecutor;
    private UserInformation userInformation;
    private City city;

    public static Intent newIntentPrivacy(Context context) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_PRIVACY);
        return intent;
    }

    public static Intent newIntentChangePass(Context context) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_CHANGE_PASS);
        return intent;
    }

    public static Intent newIntentUpdateProfile(Context context, UserInformation userInformation) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_UPDATE_PROFILE);
        intent.putExtra(KEY_USER_INFORMATION, userInformation);
        return intent;
    }

    public static Intent newIntentDetailCity(Context context, City city) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_DETAIL_CITY);
        intent.putExtra(KEY_ITEM_CITY, city);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        setUnbinder(ButterKnife.bind(this));
        presenter = new FramePresenter(this);

        Intent intent = getIntent();
        if (intent != null) {
            direct = intent.getStringExtra(DIRECT_VERIFY);
            userInformation = intent.getParcelableExtra(KEY_USER_INFORMATION);
            city = intent.getParcelableExtra(KEY_ITEM_CITY);
        }

        asyncTaskExecutor = new AsyncTaskExecutor();
        asyncTaskExecutor.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskExecutor extends AsyncTask<Void, Void, FragmentController> {

        @Override
        protected FragmentController doInBackground(Void... voids) {
            String title = "";
            Fragment fragment = null;
            String TAG = "";

            switch (direct) {
                case DIRECT_VERIFY: {
                    break;
                }
                case DIRECT_PRIVACY: {
                    title = getString(R.string.text_privacy);
                    fragment = PrivacyFragment.newInstance(false);
                    TAG = PrivacyFragment.TAG;
                    break;
                }
                case DIRECT_CHANGE_PASS: {
                    fragment = ChangePassFragment.newInstance();
                    TAG = ChangePassFragment.TAG;
                    break;
                }
                case DIRECT_UPDATE_PROFILE: {
                    title = getString(R.string.text_title_information_profile);
                    fragment = UpdateUpdateProfileFragment.newInstance(userInformation);
                    TAG = UpdateUpdateProfileFragment.TAG;
                    break;
                }
                case DIRECT_DETAIL_CITY: {
                    fragment = DetailCityFragment.newInstance(city);
                    TAG = DetailCityFragment.TAG;
                    break;
                }

            }

            if (fragment != null) {
                return new FragmentController(title, fragment, TAG);
            }
            return null;
        }

        @Override
        protected void onPostExecute(FragmentController fragmentController) {
            super.onPostExecute(fragmentController);
            if (fragmentController != null) {
                tvTitleFrame.setText(fragmentController.getTitle());
                AppUtils.replaceFragment(getSupportFragmentManager(), R.id.frFrame, fragmentController.getFragment(), false, fragmentController.getTag());
            }
        }
    }

//    @OnClick(R.id.ivBack)
//    public void onClickBack() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStackImmediate();
//        } else {
//            finish();
//        }
//    }

    @Override
    protected void onDestroy() {
        if (asyncTaskExecutor != null) asyncTaskExecutor.cancel(true);
        clearActivity(this, R.id.rootFrame);
        super.onDestroy();
    }
}
