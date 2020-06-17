package com.example.mytravel.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mytravel.MainApp;
import com.example.mytravel.data.AppDataManager;
import com.example.mytravel.utils.CommonUtils;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private Unbinder unbinder;
    private Dialog progressDialog;
    private MvpPresenter presenter;
    private AppDataManager appDataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BasePresenter(this);
        appDataManager = MainApp.getInstance().getAppDataManager();
    }

    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @Override
    protected void onDestroy() {

        if (unbinder != null) {
            unbinder.unbind();
        }

        clearCurrentActivities();
        super.onDestroy();
    }

    private void clearCurrentActivities() {
        Activity currActivity = ((MainApp) getApplication()).getCurrentActivity();
        if (this.equals(currActivity)) {
            ((MainApp) getApplication()).setCurrentActivity(null);
        }
    }

    @Override
    protected void onResume() {
        ((MainApp) getApplication()).setCurrentActivity(this);
        super.onResume();
    }

    @Override
    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }


    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            hideLoading();
            progressDialog = CommonUtils.showLoadingDialog(this);
        });

    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        });

    }


    public synchronized void clearActivity(Activity activity,
                                           int rootLayout) {
        try {
            View view = activity.findViewById(rootLayout);
            if (view != null) {
                unbindViewReferences(view);
                if (view instanceof ViewGroup)
                    unbindViewGroupReferences((ViewGroup) view);
            }
            System.gc();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected void unbindViewReferences(View view) {
        try {
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }

        try {
            view.setOnCreateContextMenuListener(null);
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }

        try {
            view.setOnFocusChangeListener(null);
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }

        try {
            view.setOnKeyListener(null);
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }

        try {
            view.setOnLongClickListener(null);
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }

        try {
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }
        setNullView(view);
        if (view instanceof ImageView) {
            setNullImageView((ImageView) view);
        }

        if (view instanceof WebView) {
            ((WebView) view).destroy();
        }

        if (view instanceof Button) {
            Button bt = (Button) view;
            bt.setBackgroundResource(0);
        }
        if (view instanceof RelativeLayout) {
            RelativeLayout rl = (RelativeLayout) view;
            rl.destroyDrawingCache();
            rl.setBackgroundResource(0);
        }
        if (view instanceof LinearLayout) {
            LinearLayout ln = (LinearLayout) view;
            ln.destroyDrawingCache();
            ln.setBackgroundResource(0);
        }
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            listView.destroyDrawingCache();
        }

        if (view instanceof ConstraintLayout) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            constraintLayout.destroyDrawingCache();
            constraintLayout.setBackgroundResource(0);
        }

    }

    @SuppressWarnings("deprecation")
    protected void setNullView(View view) {
        try {
            if (view != null) {
                view.setBackgroundDrawable(null);
                view.destroyDrawingCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void unbindViewGroupReferences(ViewGroup viewGroup) {
        int nrOfChildren = viewGroup.getChildCount();
        for (int i = 0; i < nrOfChildren; i++) {
            View view = viewGroup.getChildAt(i);
            unbindViewReferences(view);
            if (view instanceof ViewGroup)
                unbindViewGroupReferences((ViewGroup) view);
        }
        try {
            viewGroup.removeAllViews();
        } catch (Throwable mayHappen) {
            mayHappen.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    protected void setNullImageView(ImageView imgView) {
        try {
            if (imgView != null) {
                if (imgView.getDrawingCache() != null
                        && !imgView.getDrawingCache().isRecycled()) {
                    imgView.getDrawingCache().recycle();
                }
                imgView.destroyDrawingCache();
                imgView.setBackgroundDrawable(null);
                imgView.setImageBitmap(null);
                imgView.setImageDrawable(null);
                imgView.setBackgroundResource(0);
                imgView.setImageResource(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
