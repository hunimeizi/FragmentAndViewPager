package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.textviewdemo.R;
import com.example.textviewdemo.SettingActivity;

/**
 * 个人中心
 * 
 * @author Ansen
 * @create time 2015-09-08
 */
public class PersonFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_person, null);
		rootView.findViewById(R.id.action_settings).setOnClickListener(clickListener);
		return rootView;
	}
	
	private OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.action_settings:
				Intent intent=new Intent(getActivity(), SettingActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
}
