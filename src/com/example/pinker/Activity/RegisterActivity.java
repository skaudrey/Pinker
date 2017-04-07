package com.example.pinker.Activity;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.example.pinker.R;
import com.example.pinker.fileservice.FileHelper;
import com.example.pinker.usermanage.RegisterClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.*;

public class RegisterActivity extends Activity{
	//����button
	private Button regMessageButton,regSubmitButton;
	//����EditText
	private EditText regTelEditT,regUsernameEditT,regPasswordEditT,regPassword2EditT,editText1;
	String result=null;
	boolean IsRegister=false;
	int rand=0;

	String phonenumber;
	String username;
	String password;
	String password2;
	String msg;
	
	FileHelper filehelper;
	
	PkApplication pkApp;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		pkApp=(PkApplication)getApplication();
		try {
			filehelper = new FileHelper(RegisterActivity.this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		regSubmitButton=(Button)findViewById(R.id.regSubmitButton);
		regMessageButton=(Button)findViewById(R.id.regMessageButton);
		regTelEditT=(EditText)findViewById(R.id.regTelEditT);
		regUsernameEditT=(EditText)findViewById(R.id.regUsernameEditT);
		regPasswordEditT=(EditText)findViewById(R.id.regPasswordEditT);
		regPassword2EditT=(EditText)findViewById(R.id.regPassword2EditT);
		editText1=(EditText)findViewById(R.id.editText1);
		
		//�ı����¼�
		//�ֻ���
		regTelEditT.setOnFocusChangeListener(new OnFocusChangeListener() {  
		    @Override  
		    public void onFocusChange(View v, boolean hasFocus) {  
		        if(hasFocus) {	        	
		    } else {
		    	phonenumber=regTelEditT.getText().toString();
			    if(phonenumber.length()!=11){
				   Toast.makeText(RegisterActivity.this, "�ֻ�����������",
						Toast.LENGTH_SHORT).show();
				   regTelEditT.setText("");
			       }
		        }
		    }
		});
		//�û���
		regUsernameEditT.setOnFocusChangeListener(new OnFocusChangeListener() {  
				    @Override  
				    public void onFocusChange(View v, boolean hasFocus) {  
				        if(hasFocus) {	        	
				    } else {
					    username=regUsernameEditT.getText().toString();
					    if(username.equals("")){
						   Toast.makeText(RegisterActivity.this, "�û�������Ϊ�գ�",
								Toast.LENGTH_SHORT).show();
					       }
				        }
				    }
				});
		//����
		regPasswordEditT.setOnFocusChangeListener(new OnFocusChangeListener() {  
						    @Override  
						    public void onFocusChange(View v, boolean hasFocus) {  
						        if(hasFocus) {	        	
						    } else {
							    password=regPasswordEditT.getText().toString();
							    if(password.equals("")){
								   Toast.makeText(RegisterActivity.this, "���벻��Ϊ�գ�",
										Toast.LENGTH_SHORT).show();
							       }
						        }
						    }
						});
		//����2
		regPassword2EditT.setOnFocusChangeListener(new OnFocusChangeListener() {  
								    @Override  
								    public void onFocusChange(View v, boolean hasFocus) {  
								        if(hasFocus) {	        	
								    } else {
									    password2=regPassword2EditT.getText().toString();
									    if(!password2.equals(password)){
										   Toast.makeText(RegisterActivity.this, "�����������벻һ�£�",
												Toast.LENGTH_SHORT).show();
										   regPassword2EditT.setText("");
									       }
								        }
								    }
								});
		//��֤��
		editText1.setOnFocusChangeListener(new OnFocusChangeListener() {  
										    @Override  
										    public void onFocusChange(View v, boolean hasFocus) {  
										        if(hasFocus) {	        	
										    } else {
											    msg=editText1.getText().toString();
											    if(msg.equals("")){
												   Toast.makeText(RegisterActivity.this, "��֤���������",
														Toast.LENGTH_SHORT).show();
											       }
										        }
										    }
										});

		//final int randByUser=123;//Integer.parseInt(regUsernameEditT.getText().toString().trim());
         //������֤��
		regMessageButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				 phonenumber=regTelEditT.getText().toString();
				 Random random = new Random();
				 rand=random.nextInt(899999)+100000;
				 SmsManager smsManager=SmsManager.getDefault();
				 smsManager.sendTextMessage(phonenumber, null, rand+"",null,null);	
			}		
		});
		
		 regSubmitButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new Thread(runnable).start();
			}
		});
	}
	
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
		    try {
				result = RegisterClient.queryStringForPost(username,phonenumber,password,pkApp.getHTTPSERVER_IP()+"register");
				String[] results=result.split(",");
				result = results[0];
				String isRegister=results[1];//���0,1
				if(isRegister.equals("1")){IsRegister=true;}else{IsRegister=false;}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handler.sendMessage(msg);
	    }
	};
	
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);
	        if(IsRegister==true){
	        	 Toast.makeText(RegisterActivity.this, "ע��ɹ���",
							Toast.LENGTH_SHORT).show();
			    try {
					filehelper.saveUserFile("",username,password,
							phonenumber,"","","","","","");
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
				pkApp.setUSER_NAME(username);
				pkApp.setUSER_PASSWORD(password);
				pkApp.setUSER_PHONENO(phonenumber);
				startActivity(intent);
				RegisterActivity.this.finish();
	        }
	        else{	  
	        	Toast.makeText(RegisterActivity.this, result,
						Toast.LENGTH_SHORT).show();
	        }
	    }
	};
	

}
