package com.zc.app.mpos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.app.mpos.R;

public class DetailItemAdpater extends BaseAdapter {
	int index;
	Context context;
	List<DetailItem> logs = new ArrayList<DetailItem>();

	public DetailItemAdpater(Context context, List<DetailItem> logs) {
		index = -1;
		this.context = context;
		this.logs = logs;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void add(DetailItem contact) {
		this.logs.add(contact);
		this.notifyDataSetChanged();
	}
	
	public void clear(){
		this.logs.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return logs.size();
	}

	@Override
	public Object getItem(int position) {
		return logs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d("getView", "This is position:" + position);
		// if (convertView == null) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.detail_item_layout, null);

		TextView amountText = (TextView) convertView
				.findViewById(R.id.amountText);
		TextView dateText = (TextView) convertView.findViewById(R.id.dateText);
		TextView typeText = (TextView) convertView.findViewById(R.id.typeText);
		DetailItem log = logs.get(position);
		typeText.setText(log.getTradeType());

		dateText.setText(log.getTradeDate() + " " + log.getTradeTime());

		amountText.setText("-" + log.getTradeAmount() + "元");
		amountText.setTextColor(convertView.getResources().getColor(
				R.color.green));

		return convertView;
	}

}
