package com.example.mytravel.models;

import androidx.fragment.app.Fragment;

public class FragmentController {
    private Fragment fragment;
    private String tag;
    private String title;

    public FragmentController(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public FragmentController(String title, Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
