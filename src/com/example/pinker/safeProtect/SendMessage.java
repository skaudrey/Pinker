package com.example.pinker.safeProtect;

import android.telephony.SmsManager;

public class SendMessage{

	public SendMessage(String phoneNumberString){
		String text ="������ʹ��PINKERƴ�����������ƴ��,����������̫æ����ȷ�ϵص������Σ�ղ���ȷ�ϵص�,����ϵ��ȷ�ϰ�ȫ!";
		SmsManager smsManager=SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumberString, null, text,null,null);
	}
}
