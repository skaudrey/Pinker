package com.example.pinker.chatinfo;

import java.util.List;

import com.example.pinker.R;

import android.content.Context;
import android.view.*;
import android.widget.*;

public class MessageAdapter extends BaseAdapter {

	private Context cxt;
	private LayoutInflater inflater;
	private List<MyMessage> listMsg;

	public MessageAdapter(Context formClient, List<MyMessage> listMsg) {
		this.cxt = formClient;
		this.listMsg = listMsg;
	}

	@Override
	public int getCount() {
		return listMsg.size();
	}

	@Override
	public Object getItem(int position) {
		return listMsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.inflater = (LayoutInflater) this.cxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// IN,OUT
		if (listMsg.get(position).from.equals("IN")) {
			convertView = this.inflater.inflate(R.layout.chat_chat_in, null);
		} else {
			convertView = this.inflater.inflate(R.layout.chat_chat_out, null);
		}

		TextView useridView = (TextView) convertView
				.findViewById(R.id.formclient_row_userid);
		TextView dateView = (TextView) convertView
				.findViewById(R.id.formclient_row_date);
		TextView msgView = (TextView) convertView
				.findViewById(R.id.formclient_row_msg);

		useridView.setText(listMsg.get(position).userid);
		dateView.setText(listMsg.get(position).date);
		msgView.setText(listMsg.get(position).msg);

		return convertView;
	}
}
