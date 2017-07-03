package com.qq.activity;

import com.qq.R;
import com.qq.fragment.QQconstactFragment;
import com.qq.view.TitleBarView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class QQconstactActivity extends FragmentActivity {
	private Context mContext;
	private TitleBarView mTitleBarView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_qq_constact);
		
		findView();
		init();
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
	}
	
	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.choose_constact);
		
		FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
		QQconstactFragment qQconstactFragment=new QQconstactFragment();
		ft.replace(R.id.rl_constact, qQconstactFragment);
		ft.commit();
	}

}
