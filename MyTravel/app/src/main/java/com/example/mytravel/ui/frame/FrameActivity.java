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
import com.example.mytravel.models.city.Explore;
import com.example.mytravel.models.city.PlaceHot;
import com.example.mytravel.models.city.TourPopular;
import com.example.mytravel.models.imagehot.ImageHot;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.ui.auth.changepass.ChangePassFragment;
import com.example.mytravel.ui.auth.updateprofile.UpdateUpdateProfileFragment;
import com.example.mytravel.ui.booktour.BookTourFragment;
import com.example.mytravel.ui.detailcity.DetailCityFragment;
import com.example.mytravel.ui.detailexplore.DetailExploreFragment;
import com.example.mytravel.ui.detailexplore.map.MapFragment;
import com.example.mytravel.ui.detailexplore.placehot.PlaceHotFragment;
import com.example.mytravel.ui.detailtour.DetailTourFragment;
import com.example.mytravel.ui.fearture.MoreFragment;
import com.example.mytravel.ui.imagehot.ImageHotFragment;
import com.example.mytravel.ui.listtour.ListTourFragment;
import com.example.mytravel.ui.privacy.PrivacyFragment;
import com.example.mytravel.ui.viewimage.ViewImageFragment;
import com.example.mytravel.utils.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mytravel.utils.ConstApp.DIRECT_BOOK_TOUR;
import static com.example.mytravel.utils.ConstApp.DIRECT_CHANGE_PASS;
import static com.example.mytravel.utils.ConstApp.DIRECT_DETAIL_CITY;
import static com.example.mytravel.utils.ConstApp.DIRECT_DETAIL_EXPLORE;
import static com.example.mytravel.utils.ConstApp.DIRECT_DETAIL_TOUR;
import static com.example.mytravel.utils.ConstApp.DIRECT_EXPLORE_MORE;
import static com.example.mytravel.utils.ConstApp.DIRECT_HOT_MARKER;
import static com.example.mytravel.utils.ConstApp.DIRECT_IMAGE_HOT;
import static com.example.mytravel.utils.ConstApp.DIRECT_LIST_TOUR;
import static com.example.mytravel.utils.ConstApp.DIRECT_MAP_PLACE_HOT;
import static com.example.mytravel.utils.ConstApp.DIRECT_PLACE_HOT;
import static com.example.mytravel.utils.ConstApp.DIRECT_PRIVACY;
import static com.example.mytravel.utils.ConstApp.DIRECT_UPDATE_PROFILE;
import static com.example.mytravel.utils.ConstApp.DIRECT_VERIFY;
import static com.example.mytravel.utils.ConstApp.DIRECT_VIEW_FULL_IMAGE_HOT;
import static com.example.mytravel.utils.ConstApp.KEY_HOT_PLACE;
import static com.example.mytravel.utils.ConstApp.KEY_HOT_PLACE_LAT;
import static com.example.mytravel.utils.ConstApp.KEY_HOT_PLACE_LNG;
import static com.example.mytravel.utils.ConstApp.KEY_ITEM_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_ITEM_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_ITEM_IMAGE_HOT;
import static com.example.mytravel.utils.ConstApp.KEY_ITEM_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_LIST_HOT_PLACE;
import static com.example.mytravel.utils.ConstApp.KEY_NAME_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_NAME_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_PLACE;
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
    private TourPopular tourPopular;
    private Explore explore;
    private String idCity;
    private String idExplore;
    private String nameExplore;
    private String nameTour;
    private String idPlace;
    private String lat;
    private String lng;
    private PlaceHot placeHot;
    private ArrayList<PlaceHot> listHotMarker;
    private ImageHot imageHot;

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

    public static Intent newIntentDetailTour(Context context, TourPopular tourPopular) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_DETAIL_TOUR);
        intent.putExtra(KEY_ITEM_TOUR, tourPopular);
        return intent;
    }

    public static Intent newIntentDetailExplore(Context context, Explore explore, String idCity) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_DETAIL_EXPLORE);
        intent.putExtra(KEY_ITEM_EXPLORE, explore);
        intent.putExtra(KEY_TYPE_ID_CITY, idCity);
        return intent;
    }

    public static Intent newIntentListTour(Context context, String idCity, String idExplore) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_LIST_TOUR);
        intent.putExtra(KEY_TYPE_ID_CITY, idCity);
        intent.putExtra(KEY_TYPE_ID_EXPLORE, idExplore);
        return intent;
    }

    public static Intent newIntentExploreMore(Context context, String idCity, String idExplore, String nameExplore) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_EXPLORE_MORE);
        intent.putExtra(KEY_TYPE_ID_CITY, idCity);
        intent.putExtra(KEY_TYPE_ID_EXPLORE, idExplore);
        intent.putExtra(KEY_NAME_EXPLORE, nameExplore);
        return intent;
    }

    public static Intent newIntentBookTour(Context context, String nameTour) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_BOOK_TOUR);
        intent.putExtra(KEY_NAME_TOUR, nameTour);
        return intent;
    }

    public static Intent newIntentPlaceHot(Context context, String idCity, String idExplore, String idPlace) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_PLACE_HOT);
        intent.putExtra(KEY_TYPE_ID_CITY, idCity);
        intent.putExtra(KEY_TYPE_ID_EXPLORE, idExplore);
        intent.putExtra(KEY_TYPE_ID_PLACE, idPlace);
        return intent;
    }

    public static Intent newIntentMapPlaceHot(Context context, String lat, String lng, PlaceHot placeHot) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_MAP_PLACE_HOT);
        intent.putExtra(KEY_HOT_PLACE_LAT, lat);
        intent.putExtra(KEY_HOT_PLACE_LNG, lng);
        intent.putExtra(KEY_HOT_PLACE, placeHot);
        return intent;
    }

    public static Intent newIntentHotMarker(Context context, ArrayList<PlaceHot> listPlaceHots) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_HOT_MARKER);
        intent.putExtra(KEY_LIST_HOT_PLACE, listPlaceHots);
        return intent;
    }

    public static Intent newIntentImageHotFragment(Context context, String idExplore) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_IMAGE_HOT);
        intent.putExtra(KEY_TYPE_ID_EXPLORE, idExplore);
        return intent;
    }

    public static Intent newIntentFullViewImage(Context context, ImageHot imageHot) {
        Intent intent = new Intent(context, FrameActivity.class);
        intent.putExtra(DIRECT_VERIFY, DIRECT_VIEW_FULL_IMAGE_HOT);
        intent.putExtra(KEY_ITEM_IMAGE_HOT, imageHot);
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
            tourPopular = intent.getParcelableExtra(KEY_ITEM_TOUR);
            explore = intent.getParcelableExtra(KEY_ITEM_EXPLORE);
            idCity = intent.getStringExtra(KEY_TYPE_ID_CITY);
            idExplore = intent.getStringExtra(KEY_TYPE_ID_EXPLORE);
            nameExplore = intent.getStringExtra(KEY_NAME_EXPLORE);
            nameTour = intent.getStringExtra(KEY_NAME_TOUR);
            idPlace = intent.getStringExtra(KEY_TYPE_ID_PLACE);
            lat = intent.getStringExtra(KEY_HOT_PLACE_LAT);
            lng = intent.getStringExtra(KEY_HOT_PLACE_LNG);
            placeHot = intent.getParcelableExtra(KEY_HOT_PLACE);
            listHotMarker = intent.getParcelableArrayListExtra(KEY_LIST_HOT_PLACE);
            imageHot = intent.getParcelableExtra(KEY_ITEM_IMAGE_HOT);
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
                case DIRECT_DETAIL_TOUR: {
                    title = getString(R.string.text_title_detail_tour);
                    fragment = DetailTourFragment.newInstance(tourPopular);
                    TAG = DetailTourFragment.TAG;
                    break;
                }
                case DIRECT_DETAIL_EXPLORE: {
                    fragment = DetailExploreFragment.newInstance(explore, idCity);
                    TAG = DetailExploreFragment.TAG;
                    break;
                }
                case DIRECT_LIST_TOUR: {
                    title = getString(R.string.text_tours);
                    fragment = ListTourFragment.newInstance(idCity, idExplore);
                    TAG = ListTourFragment.TAG;
                    break;
                }
                case DIRECT_EXPLORE_MORE: {
                    fragment = MoreFragment.newInstance(idCity, idExplore, nameExplore);
                    TAG = MoreFragment.TAG;
                    break;
                }
                case DIRECT_BOOK_TOUR: {
                    fragment = BookTourFragment.newInstance(nameTour);
                    TAG = BookTourFragment.TAG;
                    break;
                }
                case DIRECT_PLACE_HOT: {
                    title = getString(R.string.text_place_hot);
                    fragment = PlaceHotFragment.newInstance(idCity, idExplore, idPlace);
                    TAG = PlaceHotFragment.TAG;
                    break;
                }
                case DIRECT_MAP_PLACE_HOT: {
                    fragment = MapFragment.newInstance(lat, lng, placeHot);
                    TAG = MapFragment.TAG;
                    break;
                }
                case DIRECT_HOT_MARKER: {
                    fragment = MapFragment.newInstanceFromExplore(listHotMarker);
                    TAG = MapFragment.TAG;
                    break;
                }
                case DIRECT_IMAGE_HOT: {
                    title = getString(R.string.text_image_hot);
                    fragment = ImageHotFragment.newInstance(idExplore);
                    TAG = ImageHotFragment.TAG;
                    break;
                }
                case DIRECT_VIEW_FULL_IMAGE_HOT: {
                    fragment = ViewImageFragment.newInstance(imageHot);
                    TAG = ViewImageFragment.TAG;
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
