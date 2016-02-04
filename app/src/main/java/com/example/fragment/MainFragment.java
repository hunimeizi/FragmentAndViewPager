package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.MessageGroupFragmentAdapter;
import com.example.textviewdemo.R;

/**
 * 首页
 * @author Ansen
 * @create time 2015-09-08
 */
public class MainFragment extends Fragment {
	private ViewPager vPager;
	private List<Fragment> list = new ArrayList<Fragment>();
	private MessageGroupFragmentAdapter adapter;

	private ImageView ivShapeCircle;
	private TextView tvFollow,tvRecommend,tvLocation;
	
    private int offset=0;//偏移量216  我这边只是举例说明,不同手机值不一样
    private int currentIndex=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, null);
		

		/**
		 * 初始化三个Fragment  并且填充到ViewPager
		 */
		vPager = (ViewPager) rootView.findViewById(R.id.viewpager_home);
		DynamicFragment dynamicFragment = new DynamicFragment();
		MessageFragment messageFragment = new MessageFragment();
		DynamicFragment personFragment = new DynamicFragment();
		list.add(dynamicFragment);
		list.add(messageFragment);
		list.add(personFragment);
		adapter = new MessageGroupFragmentAdapter(getChildFragmentManager(),list);
		vPager.setAdapter(adapter);
		vPager.setOffscreenPageLimit(2);
		vPager.setCurrentItem(1);
		vPager.setOnPageChangeListener(pageChangeListener);

		
		ivShapeCircle = (ImageView) rootView.findViewById(R.id.iv_shape_circle);
		
		tvFollow=(TextView) rootView.findViewById(R.id.tv_follow);
		tvRecommend=(TextView) rootView.findViewById(R.id.tv_recommend);
		tvRecommend.setSelected(true);//推荐默认选中
		tvLocation=(TextView) rootView.findViewById(R.id.tv_location);
		
		/**
		 * 标题栏三个按钮设置点击效果
		 */
		tvFollow.setOnClickListener(clickListener);
		tvRecommend.setOnClickListener(clickListener);
		tvLocation.setOnClickListener(clickListener);

		initCursorPosition();
		return rootView;
	}
	
	private OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_follow:
				//当我们设置setCurrentItem的时候就会触发viewpager的OnPageChangeListener借口,
				//所以我们不需要去改变标题栏字体啥的
				vPager.setCurrentItem(0);
				break;
			case R.id.tv_recommend:
				vPager.setCurrentItem(1);
				break;
			case R.id.tv_location:
				vPager.setCurrentItem(2);
				break;
			}
		}
	};

	private void initCursorPosition() {
		DisplayMetrics metric = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;
		Matrix matrix = new Matrix();
		
		//标题栏我用weight设置权重  分成5份
		//(width / 5) * 2  这里表示标题栏两个控件的宽度
		//(width / 10)  标题栏一个控件的2分之一
		//7  约等于原点宽度的一半
		matrix.postTranslate((width / 5) * 2 + (width / 10)-7,0);//图片平移
		ivShapeCircle.setImageMatrix(matrix);
		
		//一个控件的宽度  我的手机宽度是1080/5=216 不同的手机宽度会不一样哦
		offset=(width / 5);
	}

	/**
	 * ViewPager滑动监听,用位移动画实现指示器效果
	 * 
	 * TranslateAnimation 强调一个地方,无论你移动了多少次,现在停留在哪里,你的起始位置从未变化过.
	 * 例如:我这个demo里面  推荐移动到了同城,指示器也停留到了同城下面,但是指示器在屏幕上的位置还是推荐下面.
	 */
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int index) {
			changeTextColor(index);
			translateAnimation(index);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	
	/**
	 * 改变标题栏字体颜色
	 * @param index
	 */
	private void changeTextColor(int index){
		tvFollow.setSelected(false);
		tvRecommend.setSelected(false);
		tvLocation.setSelected(false);
		
		switch (index) {
		case 0:
			tvFollow.setSelected(true);
			break;
		case 1:
			tvRecommend.setSelected(true);
			break;
		case 2:
			tvLocation.setSelected(true);
			break;
		}
	}
	
	/**
	 * 移动标题栏点点点...
	 * @param index
	 */
	private void translateAnimation(int index){
		TranslateAnimation animation = null;
		switch(index){
		case 0:
			if(currentIndex==1){//从推荐移动到关注   X坐标向左移动216
				animation=new TranslateAnimation(0,-offset,0,0);
			}else if (currentIndex == 2) {//从同城移动到关注   X坐标向左移动216*2  记住起始x坐标是同城那里
                animation = new TranslateAnimation(offset, -offset, 0, 0);  
            }
			break;
		case 1:
            if(currentIndex==0){//从关注移动到推荐   X坐标向右移动216
            		animation=new TranslateAnimation(-offset,0,0,0);
			}else if(currentIndex==2){//从同城移动到推荐   X坐标向左移动216
				animation=new TranslateAnimation(offset, 0,0,0);
			}
			break;
		case 2:
			if (currentIndex == 0) {//从关注移动到同城   X坐标向右移动216*2  记住起始x坐标是关注那里
                animation = new TranslateAnimation(-offset, offset, 0, 0);
            } else if(currentIndex==1){//从推荐移动到同城   X坐标向右移动216
				animation=new TranslateAnimation(0,offset,0,0);
			}
			break;
		}
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivShapeCircle.startAnimation(animation);
		
		currentIndex=index;
	}
	
	public void setCurrentItem(int index){
		vPager.setCurrentItem(index);
	}
	
}
