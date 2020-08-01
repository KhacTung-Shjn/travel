package com.example.mytravel.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseActivity;
import com.example.mytravel.models.FragmentController;
import com.example.mytravel.ui.favorite.FavoriteFragment;
import com.example.mytravel.ui.home.HomeFragment;
import com.example.mytravel.ui.profile.ProfileFragment;
import com.example.mytravel.ui.setting.SettingFragment;
import com.example.mytravel.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, BottomNavigationView.OnNavigationItemSelectedListener {


    public MainMvpPresenter presenter;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frMain)
    FrameLayout frMain;
    @BindView(R.id.tvTitleMain)
    TextView tvTitleMain;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    private Fragment currentFragment;
    private ArrayList<FragmentController> listFragmentController = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));
        presenter = new MainPresenter(this);

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        initListFragment();

    }

    private void initListFragment() {
        listFragmentController.add(new FragmentController(HomeFragment.newInstance(), HomeFragment.TAG));
        listFragmentController.add(new FragmentController(FavoriteFragment.newInstance(), FavoriteFragment.TAG));
        listFragmentController.add(new FragmentController(ProfileFragment.newInstance(), ProfileFragment.TAG));
        listFragmentController.add(new FragmentController(SettingFragment.newInstance(), SettingFragment.TAG));

        tvTitleMain.setText(getString(R.string.text_title_home));
        currentFragment = AppUtils.addFragmentsToActivity(getSupportFragmentManager(), listFragmentController, R.id.frMain, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int maybeActive = -1;
        String title = "";
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                maybeActive = 0;
                ivSearch.setVisibility(View.VISIBLE);
                title = getString(R.string.text_title_home);
                break;
            case R.id.menu_favorite:
                maybeActive = 1;
                ivSearch.setVisibility(View.INVISIBLE);
                title = getString(R.string.text_title_favorite);
                break;
            case R.id.menu_profile:
                maybeActive = 2;
                ivSearch.setVisibility(View.INVISIBLE);
                title = getString(R.string.text_title_profiles);
                break;
            case R.id.menu_setting:
                maybeActive = 3;
                ivSearch.setVisibility(View.INVISIBLE);
                title = getString(R.string.text_title_setting);
                break;
        }
        if (maybeActive != -1) {
            tvTitleMain.setText(title);
            currentFragment = AppUtils.switchFragmentActivity(getSupportFragmentManager(), currentFragment, listFragmentController.get(maybeActive).getFragment());
            return true;
        }
        return false;
    }
}
