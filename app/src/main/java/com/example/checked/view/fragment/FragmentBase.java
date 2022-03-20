package com.example.checked.view.fragment;

import android.widget.TextView;

import androidx.fragment.app.Fragment;

public abstract class FragmentBase extends Fragment {
    public abstract void addItem();
    public abstract void setTitle(TextView title);
}
