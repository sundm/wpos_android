package com.zc.app.mpos.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.purchaselLogItem;
import com.zc.app.utils.ZCLog;

public class QueryLogAdapter extends BaseAdapter {

	private final static String TAG = "queryLogAdapter";
	private List<purchaselLogItem> list;

	private Context context;

	private static class logViewHolder {
		private TextView tvDateTextView;
		private TextView tvAmountTextView;
	}

	public QueryLogAdapter(Context context, List<purchaselLogItem> list) {
		this.context = context;
		this.list = list;
	}

	public void clear() {
		this.list.clear();
		this.notifyDataSetChanged();
	}

	public void add(purchaselLogItem item) {
		this.list.add(item);
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

		ZCLog.i(TAG, "This is position:" + position);

		purchaselLogItem item = list.get(position);

		convertView = LayoutInflater.from(context).inflate(
				R.layout.query_log_item, null);

		logViewHolder viewHolder = new logViewHolder();

		viewHolder.tvDateTextView = (TextView) convertView
				.findViewById(R.id.log_date);

		viewHolder.tvAmountTextView = (TextView) convertView
				.findViewById(R.id.log_amount);

		viewHolder.tvDateTextView.setText(item.getCreateTime());

		viewHolder.tvAmountTextView.setText(item.getAmount() + " 元");

		convertView.setTag(viewHolder);

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
