package com.myfragmentcommunication;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {


    private Button btn_text;

    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, null);

        // 获取布局中的Button
        btn_text = view.findViewById(R.id.btn_text);

        // 设置Button的监听器
        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取到共同依附的Activity，通过Activity获取到对应的RightFragment对象
                RightFragment ll2Fragment = (RightFragment) getActivity().getFragmentManager().
                        findFragmentByTag("ll2Fragment");

                // 调用RightFragment的方法
                ll2Fragment.setButtonText("左侧Fragment设置的Text");
            }
        });
        return view;
    }



}
