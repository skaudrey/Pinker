package com.example.pinker.chatinfo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MyMessage implements Serializable {

	public String userid;
	public String msg;
	public String date;
	public String from;

	public MyMessage(String userid, String msg, String date, String from) {
		this.userid = userid;
		this.msg = msg;
		this.date = date;
		this.from = from;
	}
}
