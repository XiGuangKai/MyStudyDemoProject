package com.myaddfragmentbycodedemo;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerticalFragment extends Fragment {


    public VerticalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用打气筒将布局文件转换成view
        View view = inflater.inflate(R.layout.fragment_vertical, null);
        return view;
    }

}
