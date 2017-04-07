package com.example.pinker.safeProtect;

import android.telephony.SmsManager;

public class SendMessage{

	public SendMessage(String phoneNumberString){
		String text ="我正在使用PINKER拼车软件与他人拼车,但可能由于太忙忘记确认地点或遭遇危险不能确认地点,请联系我确认安全!";
		SmsManager smsManager=SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumberString, null, text,null,null);
	}
}
