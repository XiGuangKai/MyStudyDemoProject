package com.myaddfragmentbyxml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //通过 inflater将一个布局文件转换成一个View对象
        View view = inflater.inflate(R.layout.fragment_left,null);
        return view;
    }
}
