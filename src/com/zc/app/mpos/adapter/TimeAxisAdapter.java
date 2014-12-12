package com.zc.app.mpos.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.utils.ZCLog;

public class TimeAxisAdapter extends BaseAdapter {

	private List<HashMap<String, Object>> list;

	private Context context;

	private static class DateViewHolder {
		private TextView tvDateTextView;
		private TextView tvCounterTextView;
	}

	private static class TimeViewHolder {
		private TextView tvtimeTextView;
		private TextView tvAmountTextView;
	}

	public TimeAxisAdapter(Context context, List<HashMap<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	public void clear() {
		this.list.clear();
		this.notifyDataSetChanged();
	}

	public void add(HashMap<String, Object> contact) {
		this.list.add(contact);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d("getView", "This is position:" + position);

		// if (convertView == null) {

		HashMap<String, Object> map = list.get(position);

		if (map.get("day") != null) {
			ZCLog.i("listviewadapter", "set date");
			convertView = LayoutInflater.from(context).inflate(
					R.layout.day_item, null);

			DateViewHolder viewHolder = new DateViewHolder();

			LogDateItem item = (LogDateItem) list.get(position).get("day");

			viewHolder.tvCounterTextView = (TextView) convertView
					.findViewById(R.id.tv_counter);

			viewHolder.tvCounterTextView.setText(item.getCounter());

			viewHolder.tvDateTextView = (TextView) convertView
					.findViewById(R.id.tv_day);

			viewHolder.tvDateTextView.setText(item.getDate());

			convertView.setTag(viewHolder);
		}

		if (map.get("content") != null) {
			ZCLog.i("listviewadapter", "set time");
			convertView = LayoutInflater.from(context).inflate(
					R.layout.time_item, null);

			TimeViewHolder viewHolder = new TimeViewHolder();

			LogItem item = (LogItem) list.get(position).get("content");

			viewHolder.tvAmountTextView = (TextView) convertView
					.findViewById(R.id.tv_content);

			viewHolder.tvAmountTextView.setText(item.getTradeAmount());

			viewHolder.tvtimeTextView = (TextView) convertView
					.findViewById(R.id.tv_time);

			viewHolder.tvtimeTextView.setText(item.getTradeTime());
			convertView.setTag(viewHolder);
		}

		// }

		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
