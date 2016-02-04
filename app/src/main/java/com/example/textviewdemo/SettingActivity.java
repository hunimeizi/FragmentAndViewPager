package com.example.textviewdemo;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 设置界面
 * @author Ansen
 *
 */
public class SettingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		findViewById(R.id.action_main).setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.action_main:
				EventBus.getDefault().post(MainActivity.LOGOUT);
				finish();
				break;
			}
		}
	};
}
