package com.zc.app.mpos.activity;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.zc.app.bootstrap.BootstrapCircleThumbnail;
import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.MenuArrayAdapter;
import com.zc.app.mpos.util.imageUtil;
import com.zc.app.mpos.view.DragLayout;
import com.zc.app.mpos.view.DragLayout.DragListener;

public class MainActivity extends Activity {

	private DragLayout dl;
	private GridView gv_img;
	private ListView lv;
	private BootstrapCircleThumbnail iv_icon;

	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initDragLayout();
		initView();
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose() {
			}

			@Override
			public void onDrag(float percent) {
				animate(percent);
			}
		});
	}

	private void animate(float percent) {
		ViewGroup vg_left = dl.getVg_left();
		ViewGroup vg_main = dl.getVg_main();

		float f1 = 1 - percent * 0.3f;
		ViewHelper.setScaleX(vg_main, f1);
		ViewHelper.setScaleY(vg_main, f1);
		ViewHelper.setTranslationX(vg_left, -vg_left.getWidth() / 2.2f
				+ vg_left.getWidth() / 2.2f * percent);
		ViewHelper.setScaleX(vg_left, 0.5f + 0.5f * percent);
		ViewHelper.setScaleY(vg_left, 0.5f + 0.5f * percent);
		ViewHelper.setAlpha(vg_left, percent);
		ViewHelper.setAlpha(iv_icon, 1 - percent);

		int color = (Integer) imageUtil.evaluate(percent,
				Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
		dl.getBackground().setColorFilter(color, Mode.SRC_OVER);
	}

	private void initView() {
		iv_icon = (BootstrapCircleThumbnail) findViewById(R.id.iv_icon);
		gv_img = (GridView) findViewById(R.id.gv_img);

		ll = (LinearLayout) findViewById(R.id.ll1);

		gv_img.setFastScrollEnabled(true);

		gv_img.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new MenuArrayAdapter(this, new String[] { "收款交易",
				"交易查询", "龙行卡查询", "设置", "关于" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Toast.makeText(getApplicationContext(), "click " + position,
						Toast.LENGTH_LONG).show();
				Log.i("click item", arg0.toString());
				Log.i("click item", arg1.toString());
				Log.i("click item", String.valueOf(arg3));

				dl.close();
			}
		});
		
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
		
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dl.close();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
