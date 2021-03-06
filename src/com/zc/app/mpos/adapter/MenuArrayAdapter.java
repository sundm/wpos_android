package com.zc.app.mpos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.app.mpos.R;

public class MenuArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MenuArrayAdapter(Context context, String[] values) {
		super(context, R.layout.list_menu, values);
		this.context = context;
		this.values = values;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_menu, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (position == 0) {
			imageView.setImageResource(R.drawable.login_password_highlight);
		} else if (position == 1) {
			imageView.setImageResource(R.drawable.icon_search);
		}

		return rowView;
	}

}
