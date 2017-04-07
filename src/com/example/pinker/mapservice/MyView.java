package com.example.pinker.mapservice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyView extends LinearLayout implements OnClickListener{

	public Button setAsSpointButton;
	public Button cancelButton;
	public TextView pointInfoTv;

	public MyView(Context context){
			super(context);
			setupUI(context);}

	public MyView(Context context, AttributeSet attrSet){
			super(context, attrSet);
			setupUI(context);}

	private void setupUI(Context context){
		if (setAsSpointButton != null || this.isInEditMode()) 
			return;
		
		
		//UI��ʼ��
		pointInfoTv = new TextView(context);
		pointInfoTv.setText("");
		

		setAsSpointButton = new Button(context);
		setAsSpointButton.setText("��Ϊ���");
		
		cancelButton=new Button(context);
		cancelButton.setText("ȡ��");
		addView(pointInfoTv);
		addView(setAsSpointButton);
		addView(cancelButton);
				
	}
	public void onClick(View v){
				
//		setVisibility(View.GONE);
		}
	
	}