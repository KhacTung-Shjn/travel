package com.example.mytravel.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mytravel.models.FragmentController;

import java.util.List;

public class AppUtils {

    public AppUtils() {
    }

    public static void replaceFragment(FragmentManager fragmentManager, int frameMain, Fragment fragment, boolean isBackStack, String TAG) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameMain, fragment, TAG);
        if (isBackStack) {
            fragmentTransaction.addToBackStack(TAG);
        }
        fragmentTransaction.commit();
    }

    public static Fragment addFragmentsToActivity(@NonNull FragmentManager fragmentManager, List<FragmentController> fragmentControllers, int frameId, int activePosition) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentControllers.size(); i++) {
            if (i != activePosition) {
                fragmentTransaction.add(frameId, fragmentControllers.get(i).getFragment(), fragmentControllers.get(i).getTag())
                        .hide(fragmentControllers.get(i).getFragment());
            }
        }
        fragmentTransaction.add(frameId,
                fragmentControllers.get(activePosition).getFragment(),
                fragmentControllers.get(activePosition).getTag());
        fragmentTransaction.commit();
        return fragmentControllers.get(activePosition).getFragment();
    }

    public static Fragment switchFragmentActivity(@NonNull FragmentManager fragmentManager, Fragment currentFragment, Fragment mustActiveFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(currentFragment);
        transaction.show(mustActiveFragment);
        transaction.commit();
        return mustActiveFragment;
    }

}
