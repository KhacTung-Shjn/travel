package com.example.mytravel.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mytravel.R;
import com.example.mytravel.utils.ViewDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.Unbinder;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment implements DialogMvpView {

    private BaseActivity mActivity;
    private Unbinder mUnBinder;
    private ViewDialog viewDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity mActivity = (BaseActivity) context;
            this.mActivity = mActivity;
            viewDialog = new ViewDialog(mActivity);
        }
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int message) {
        showMessage(getString(message));
    }

    @Override
    public void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                hideLoading();
                viewDialog.showDialog();
            });
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (viewDialog != null) {
                    viewDialog.hideDialog();
                }
            });
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected abstract void setUp(View view);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.getContext() != null) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext(),
                    R.style.BottomSheetDialog);
            bottomSheetDialog.setCanceledOnTouchOutside(false);
            return bottomSheetDialog;
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void dismissDialog(String tag) {
        dismiss();
    }
}
