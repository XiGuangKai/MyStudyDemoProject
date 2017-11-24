package com.myfragmentcommunication;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {


    private TextView tv_right;

    public RightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right, null);

        // 查找到TextView组件
        tv_right = view.findViewById(R.id.tv_right);

        return view;
    }

    public void setButtonText(String buttonText){
        // 设置Button的Text
        tv_right.setText(buttonText);
    }
}
