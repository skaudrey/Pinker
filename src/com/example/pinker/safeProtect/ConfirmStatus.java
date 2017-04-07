package com.example.pinker.safeProtect;

import com.example.pinker.R;
import com.example.pinker.Activity.PkApplication;
import com.example.pinker.Activity.PkNotificationActivity;
import com.example.pinker.Activity.ResetPasswordActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmStatus extends Activity{
	public EditText editTextSafePSW;
	private Button buttonConfirm;
	//static ConfirmStatus confirmstatus;
	PkApplication pkApp;
	protected void onCreate(Bundle savedInstanceState) {
		//confirmstatus=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safe_confirmstatus);
		pkApp=(PkApplication)getApplication();
		buttonConfirm=(Button)findViewById(R.id.buttonConfirm);
		editTextSafePSW=(EditText)findViewById(R.id.editTextSafePSW);
		buttonConfirm.setOnClickListener(new OnClickListener(){
				public void onClick(View v){					
					String SafePSW=editTextSafePSW.getText().toString();
					if(SafePSW.equals(pkApp.getsafe_pwd())){
						  Intent intentSafeStatus = new Intent();  
					      intentSafeStatus.setAction("com.example.pinker.safeProtect.SendMessageService");
					      stopService(intentSafeStatus);
					      //Ã¯µΩµÿÕº∞°µÿÕº∞°°≠°≠
					      Intent intent=new Intent(ConfirmStatus.this,PkNotificationActivity.class);
						  startActivity(intent);
						  ConfirmStatus.this.finish();
					}else {
						Toast.makeText(ConfirmStatus.this, "√‹¬Î¥ÌŒÛ£¨«Î÷ÿ–¬ ‰»Î!", Toast.LENGTH_LONG).show();
						editTextSafePSW.setText("");
					}
				}
			});	
	}
}
	
