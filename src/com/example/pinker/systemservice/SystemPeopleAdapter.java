package com.example.pinker.systemservice;

import java.util.List;

import com.example.pinker.R;

import android.content.Context;
import android.view.*;
import android.widget.*;

public class SystemPeopleAdapter extends BaseAdapter {

	
	private Context cxt;
	private LayoutInflater inflater;
	private List<SystemPeople> listPeople;

	public SystemPeopleAdapter(Context formClient, List<SystemPeople> listMsg) {
		this.cxt = formClient;
		this.listPeople = listMsg;
	}

	@Override
	public int getCount() {
		return listPeople.size();
	}

	@Override
	public Object getItem(int position) {
		return listPeople.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.inflater = (LayoutInflater) this.cxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		TextView peoplename = (TextView) convertView
				.findViewById(R.id.peoplename);
		TextView peopleage = (TextView) convertView
				.findViewById(R.id.peopleage);

		peoplename.setText(listPeople.get(position).peoplename);
		peopleage.setText(listPeople.get(position).peopleage);
		return convertView;
	}

}
