package com.example.pinker.safeProtect;

import com.example.pinker.R;
import com.example.pinker.Activity.PkApplication;
import com.example.pinker.Activity.PkRouteActivity;
import com.example.pinker.Activity.ResetPasswordActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartProtect extends Activity{
	 private Button buttonOK,buttonCancel;
	 private EditText editTextContactphonenumber,editTextInterval,editTextSafePSW;
	 private String SafePSW,ContactPhonenumber,timeString;
	 private int time;
	 
	 PkApplication PkApp;
	 protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.safe_startprotect);

	        PkApp = (PkApplication) getApplication(); 
	        setContentView(R.layout.safe_startprotect);
	        buttonOK=(Button)findViewById(R.id.buttonOK);
	        buttonCancel=(Button)findViewById(R.id.buttonCancel);
	        editTextContactphonenumber=(EditText)findViewById(R.id.editTextContactphonenumber);
	        editTextInterval=(EditText)findViewById(R.id.editTextInterval);
	        editTextSafePSW=(EditText)findViewById(R.id.editTextSafePSW);
	        //读联系人手机号
	        ContactPhonenumber=PkApp.getUSER_CONTACTPHONENO();
	        editTextContactphonenumber.setText(ContactPhonenumber);

	        buttonOK.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					ContactPhonenumber=editTextContactphonenumber.getText().toString();
					SafePSW=editTextSafePSW.getText().toString();
			        timeString=editTextInterval.getText().toString();
			        if(timeString!=null){
				        time=Integer.parseInt(timeString);
			        }else{
			        	time=30;
			        }
			        PkApp.setUSER_CONTACTPHONENO(ContactPhonenumber);
			        PkApp.setsafe_pwd(SafePSW);
			        PkApp.settimeinterval(time);
			        
			        Intent intent=new Intent(StartProtect.this,TimeTask.class);
					startActivity(intent);
					StartProtect.this.finish();
				}		
			});	
	        
	        buttonCancel.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					Intent intent=new Intent(StartProtect.this,PkRouteActivity.class);
					startActivity(intent);
					StartProtect.this.finish();
				}		
			});
	        }
}
