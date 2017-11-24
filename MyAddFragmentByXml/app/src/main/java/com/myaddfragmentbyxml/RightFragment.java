package com.myaddfragmentbyxml;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {


    public RightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //通过 inflater将一个布局文件转换成一个View对象
        View view = inflater.inflate(R.layout.fragment_right,null);
        return view;
    }

}
