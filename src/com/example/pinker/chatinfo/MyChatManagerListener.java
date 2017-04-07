package com.example.pinker.chatinfo;

import java.util.ArrayList;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import com.example.pinker.chatservice.ChatActivity;

import android.content.*;

public class MyChatManagerListener implements ChatManagerListener {

	private Context context;
	private ArrayList<MyMessage> list;

	public MyChatManagerListener(Context context) {
		this.context = context;
		list = new ArrayList<MyMessage>();
	}

	@Override
	public void chatCreated(Chat chat, boolean arg1) {
		chat.addMessageListener(new MessageListener() {
			@Override
			public void processMessage(Chat newchat, Message message) {

				String from = message.getFrom().substring(0,
						message.getFrom().indexOf("@"));
				
				String[] msg = new String[] { from, message.getBody(),
						TimeRender.getDate(), "IN" };
				String friendName = ChatActivity.friendName;

				if (friendName.equals(from)) {
					list.clear();
					list.add(new MyMessage(msg[0], msg[1], msg[2], msg[3]));
					Intent intent = new Intent("msg");
					intent.putExtra("data", list);			
					context.sendBroadcast(intent);
				}
			}
		});
	}
}
