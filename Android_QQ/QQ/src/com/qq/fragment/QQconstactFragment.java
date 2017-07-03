package com.qq.fragment;

import java.util.ArrayList;
import java.util.List;

import com.qq.AsyncTaskBase;
import com.qq.R;
import com.qq.adapter.QQconstactAdapter;
import com.qq.bean.RecentChat;
import com.qq.test.TestData;
import com.qq.view.CustomListView;
import com.qq.view.LoadingView;
import com.qq.view.TitleBarView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class QQconstactFragment extends Fragment {

	private Context mContext;
	private View mBaseView;
	private CustomListView mCustomListView;
	private View mSearchView;
	private RelativeLayout mPhone;
	private LoadingView mLoadingView;
	private QQconstactAdapter mAdapter;
	private List<RecentChat> users=new ArrayList<RecentChat>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_qq_constact, null);
		findView();
		init();
		return mBaseView;
	}
	
	private void findView(){
		mSearchView=mBaseView.findViewById(R.id.search);
		mCustomListView=(CustomListView) mBaseView.findViewById(R.id.listview);
		mPhone=(RelativeLayout) mSearchView.findViewById(R.id.rl_tonxunru);
		mLoadingView=(LoadingView) mBaseView.findViewById(R.id.loading);
	}
	
	
	private void init(){
		mAdapter=new QQconstactAdapter(mContext, users, mCustomListView);
		mCustomListView.setAdapter(mAdapter);
		mCustomListView.setCanLoadMore(false);
		mCustomListView.setCanRefresh(false);
		
		mPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				FragmentTransaction ft=getFragmentManager().beginTransaction();
				PhoneConstactFragment phoneConstactFragment=new PhoneConstactFragment();
				ft.replace(R.id.rl_constact, phoneConstactFragment);
				ft.setCustomAnimations(R.anim.activity_up, R.anim.activity_down);
				ft.commit();
			}
		});
		
		new AsyncTaskLoad(mLoadingView).execute(0);
	}
	
	private class AsyncTaskLoad extends AsyncTaskBase{
		List<RecentChat> rcs=new ArrayList<RecentChat>();
		public AsyncTaskLoad(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result=-1;
			rcs=TestData.getRecentChats();
			result=1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result==1){
				users.addAll(rcs);
				mAdapter.notifyDataSetChanged();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}

}
