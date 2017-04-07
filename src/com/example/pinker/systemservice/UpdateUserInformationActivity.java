package com.example.pinker.systemservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import com.example.pinker.R;
import com.example.pinker.Activity.PkApplication;
import com.example.pinker.Activity.PkLocationMapActivity;
import com.example.pinker.Activity.SubmitActivity;
import com.example.pinker.fileservice.FileHelper;
import com.example.pinker.usermanage.FindUserInformationClient;
import com.example.pinker.usermanage.UpdateUserInformationClient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class UpdateUserInformationActivity extends Activity{
	//����button
	private Button buttonSave,setBirth;
	//����EditText
	private EditText editTextUsername,editTextPhonenumber,editTextContactphonenumber,editTextSignature;
	public String username,phonenumber,birth,gender,contactphonenumber,signature,result,resultFind;
	//��������
	private TextView textViewbirth;
	public int year,month,day;
	private String yearString,monthString,dayString;
	//public boolean isUpdate;
	//�Ա�
	private RadioButton radioButtonMan,radioButtonWoman;
	private RadioGroup radioGroup;
	private PkApplication PKApp;
	
	private FileHelper fileHelper;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_userinformation);
		
		Intent intent=getIntent();
		intent.setAction("com.baidu.location.f");
		stopService(intent);
		
		try {
			fileHelper=new FileHelper(this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PKApp = (PkApplication) getApplication(); 
		buttonSave=(Button)findViewById(R.id.buttonSave);
		setBirth=(Button)findViewById(R.id.buttonSetBirth);
		editTextUsername=(EditText)findViewById(R.id.editTextUsername);
		editTextPhonenumber=(EditText)findViewById(R.id.editTextPhonenumber);
		editTextContactphonenumber=(EditText)findViewById(R.id.editTextContactphonenumber);
		editTextSignature=(EditText)findViewById(R.id.editTextSignature);
		radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
		radioButtonMan=(RadioButton)findViewById(R.id.radioButtonMan);
		radioButtonWoman=(RadioButton)findViewById(R.id.radioButtonWoman);
		textViewbirth=(TextView)findViewById(R.id.textViewbirth);
		
		//test
		try {
			username=PKApp.getUSER_NAME();
			phonenumber=PKApp.getUSER_PHONENO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editTextUsername.setText(username);
		editTextPhonenumber.setText(phonenumber);
		//�����ݿ��ж��Ա𡢳������ڡ���ϵ�˺���͸���ǩ��
		new Thread(runnableFind).start();
		
		//birth
		//��ȡ��ǰʱ��
		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
		year = c.get(Calendar.YEAR); 
		month = c.get(Calendar.MONTH); 
		day = c.get(Calendar.DATE); 
		
		
		textViewbirth.setText(year+"-"+(int)(month+1)+"-"+day);
		
		setBirth.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(runnablebirth).start();
			}
		});	
		
		
		//�Ա�radioGroup
		//����ID�ҵ�RadioGroupʵ��
				//��һ������������
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            
		         @Override   
		         public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {   
		        	 if(checkedId==radioButtonMan.getId())   
		             {   
		        		 gender="��";
		             }else   
		             {   
		            	 gender="Ů";
		             }  
		           }   
		        }); 
		//���水ť�¼�
		buttonSave.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
			new Thread(runnable).start();						
			}		
		});
		editTextContactphonenumber.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  String phonerno=editTextContactphonenumber.getText().toString();
			    	  if(phonerno.length()!=11){
			    		  Toast.makeText(UpdateUserInformationActivity.this, "�ֻ�������������������룡",
									Toast.LENGTH_SHORT).show();
			    		  editTextContactphonenumber.setText("");
			    	  }
			      }
			    }
			  });
		}
	//�����ݿ��ж��Ա����䡢��ϵ�˺��롢����ǩ��
	Runnable runnableFind = new Runnable(){
	    @Override
	    public void run() {
		    try {
				resultFind = FindUserInformationClient.queryStringForPost(username,phonenumber,PKApp.getHTTPSERVER_IP()+"findUserInformation");
				if(!resultFind.equals(null)){
					String[] results=resultFind.split(",");//��","����
					gender = results[0];
					birth=results[1];
					contactphonenumber=results[2];
					signature=results[3];
					PKApp.setUSER_GENDER(gender);
					PKApp.setUSER_CONTACTPHONENO(contactphonenumber);
					PKApp.setUSER_SIGNATURE(signature);
					}else{}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handlerFind.sendMessage(msg);	      
	    }
	};
	
	Handler handlerFind = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);
	        if(gender.equals("��")){
	        	radioGroup.check(radioButtonMan.getId()); 
	        	}else if (gender.equals("Ů")) {
	        		radioGroup.check(radioButtonWoman.getId()); 
				}
	        	else{}
	        editTextContactphonenumber.setText(contactphonenumber);
	        textViewbirth.setText(birth);
			editTextSignature.setText(signature);			
	    }
	};
	
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
			contactphonenumber=editTextContactphonenumber.getText().toString();
			if(contactphonenumber.length()!=11){
				 Toast.makeText(UpdateUserInformationActivity.this, "�ֻ�������������������룡",
							Toast.LENGTH_SHORT).show();
			}
			birth="19940825";
			signature=editTextSignature.getText().toString();
			PKApp.setUSER_GENDER(gender);
			PKApp.setUSER_CONTACTPHONENO(contactphonenumber);
			PKApp.USER_CONTACTPHONENO=contactphonenumber;
			PKApp.setUSER_SIGNATURE(signature);
			try {
				fileHelper.saveUserFile(PKApp.getUSER_ID(), PKApp.getUSER_NAME(),PKApp.getUSER_PASSWORD(), PKApp.getUSER_PHONENO()
						, PKApp.getUSER_GENDER(), "",PKApp.getUSER_AGE(), PKApp.getUSER_CONTACTPHONENO(),PKApp.getUSER_SIGNATURE(),"");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
				result = UpdateUserInformationClient.queryStringForPost(username,phonenumber, gender,birth,contactphonenumber,signature,PKApp.getHTTPSERVER_IP()+"updateUserInformation");
				String[] results=result.split(",");//��","����
				result = results[0];
				//String isupdate=results[1];//���0,1
				//if(isupdate.equals("1")){isUpdate=true;}else{isUpdate=false;}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
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
	        Toast.makeText(UpdateUserInformationActivity.this, result, Toast.LENGTH_LONG).show();
	    }
	};
	 DatePickerDialog.OnDateSetListener datelistener=new DatePickerDialog.OnDateSetListener()    {
		 @Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
	       year=myyear;
           month=monthOfYear;
           day=dayOfMonth;
           //��������
           updateDate();
		}
		//��DatePickerDialog�ر�ʱ������������ʾ
       private void updateDate()
       {
   		textViewbirth.setText(year+"-"+(int)(month+1)+"-"+day);
       }
   };
   
 //ʱ�����ڼ����߳�
   Runnable runnablebirth=new Runnable() {
		public void run() {
			Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handlerbirth.sendMessage(msg);
	        yearString=year+"";
			if(month<10){monthString="0"+month;}else{monthString=month+"";}
			if(day<10){dayString="0"+day;}else{dayString=day+"";}
			PKApp.setyear1(yearString);
			PKApp.setmonth1(monthString);
			PKApp.setday1(dayString);
		}
	};
	Handler handlerbirth = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);        
	        DatePickerDialog dpd=new DatePickerDialog(UpdateUserInformationActivity.this,datelistener,year,month,day);
            dpd.show();//��ʾDatePickerDialog���          
	    }
	};
	//���ذ�ť���� 
		@Override
		  public boolean onKeyDown(int keyCode, KeyEvent event) {
		   // TODO Auto-generated method stub
		   
		    if(keyCode == KeyEvent.KEYCODE_BACK){
			   Intent it=new Intent(UpdateUserInformationActivity.this,PkLocationMapActivity.class);
			   startActivity(it);
			   finish();
		    }
		    return true;
		  }
}
