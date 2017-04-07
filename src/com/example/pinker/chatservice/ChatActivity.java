package com.example.pinker.chatservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.pinker.R;
import com.example.pinker.Activity.AccessableUserListActivity;
import com.example.pinker.Activity.FirstActivity;
import com.example.pinker.Activity.PkApplication;
import com.example.pinker.Activity.PkLocationMapActivity;
import com.example.pinker.Activity.PkRouteActivity;
import com.example.pinker.chatconnect.ClientConServer;
import com.example.pinker.chatinfo.*;
import com.example.pinker.safeProtect.Notify;

public class ChatActivity extends Activity {

	private MessageAdapter adapter;
	public List<MyMessage> listMsg = new ArrayList<MyMessage>();
	private String loginUser;
	private EditText msgText;
	public static String friendName;
	public static String friendstatus="����";
	public Button mSendMsgBtn;
	//�Ƿ�����
	public boolean bconnect;
	//�Ƿ�ƴ��
	public boolean buserpinche=false;
	public boolean bfriendpinche=false;
	public boolean bpinche=false;
	//�������
	private ImageButton faceSwitchbt;
	private LinearLayout mFaceRoot;// ���鸸����
	private boolean mIsFaceShow = false;// �Ƿ���ʾ����
	private InputMethodManager mInputMethodManager;
	private ViewPager mFaceViewPager;// ����ѡ��ViewPager
	private int mCurrentPage = 0;// ��ǰ����ҳ
	private List<String> mFaceMapKeys;// �����Ӧ���ַ�������
	
	private LocationClient mLocationClient;
	
	Context mcontext;
	
	PkApplication pkapp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat);
		pkapp=(PkApplication)getApplication();
		
		Intent intent=getIntent();
		intent.setAction("com.baidu.location.f");
		stopService(intent);
		mcontext=this;

		new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	try {
		    		loginUser=pkapp.getUSER_NAME();
		    		friendName=pkapp.getFRIEND();
		    		bconnect=ClientConServer.login(mcontext, loginUser, "123");
		    		friendstatus="����";//ClientConServer.getPresence(friendName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
        }).start();	
			Toast.makeText(ChatActivity.this, "�����ɹ�",
					Toast.LENGTH_SHORT).show();	   
		initViews();
		initFacePage();
		registerBoradcastReceiver();
	}


	
	/**
	 * ��ʼ������
	 */
	private void initFacePage() {
		// ������map��key������������
		Set<String> keySet = Myface.getFaceMap().keySet();
		mFaceMapKeys = new ArrayList<String>();
		mFaceMapKeys.addAll(keySet);
		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		// TODO Auto-generated method stub
		List<View> lv = new ArrayList<View>();
		for (int i = 0; i < Myface.NUM_PAGE; ++i)
			lv.add(getGridView(i));
		FacePageAdapter adapter = new FacePageAdapter(lv);
		mFaceViewPager.setAdapter(adapter);
		mFaceViewPager.setCurrentItem(mCurrentPage);
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mFaceViewPager);
		adapter.notifyDataSetChanged();
		mFaceRoot.setVisibility(View.GONE);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mCurrentPage = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});
	}
		
	/**
	 * ��ʼ������
	 */
	private void initViews() {
		ListView listview = (ListView) findViewById(R.id.listview);
		this.msgText = (EditText) findViewById(R.id.text);
		adapter = new MessageAdapter(getApplicationContext(), listMsg);
		listview.setAdapter(adapter);
		
		// ��ȡ�û������������Ϣ
		((TextView) findViewById(R.id.chatTitle)).setText("��" + friendName
				+ "("+friendstatus+")"+"������");
		//������ť
		//������Ϣ
		mSendMsgBtn=(Button)findViewById(R.id.btsend);
		mSendMsgBtn.setOnClickListener(sendClickListener);
		//�˳�����
		findViewById(R.id.btback).setOnClickListener(backClickListener);
		//�˽��û�
		findViewById(R.id.btuser).setOnClickListener(userClickListener);
		//ƴ�����
		//����ID�ҵ�RadioGroupʵ��
		RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
		//��һ������������
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            
         @Override   
         public void onCheckedChanged(RadioGroup group, int checkedId) {   
             // TODO Auto-generated method stub   
        	 if(bconnect){
	        	 String str = "";
	        	 if(checkedId==findViewById(R.id.radiotrue).getId())   
	             {   
	        		 buserpinche=true;
	        		 str=loginUser +"��ͬ��ƴ��"; 
	             }else   
	             {   
	            	 str=loginUser +"��ͬ��ƴ��";   
	             }  
   		         // ��ʾ��������
 				 listMsg.add(new MyMessage(loginUser, str, TimeRender
 						.getDate(), "OUT"));
 				 // ˢ�·��͵���Ϣ������
 				 adapter.notifyDataSetChanged();
 				 Chat chat = ClientConServer.getChatManager().createChat(
 						friendName + "@odile.openfire.com", null);
 				 try {
 					chat.sendMessage(str);
 				 } catch (XMPPException e) {
 					e.printStackTrace();
 				 }
	         }else{
	        	 Toast.makeText(ChatActivity.this, "���ӷ�����ʧ�ܣ���������",
							Toast.LENGTH_SHORT).show();
	         }
           }   
        });   

		//�������
		faceSwitchbt=(ImageButton)findViewById(R.id.btfaceswitch);
		faceSwitchbt.setOnClickListener(faceSwitchClickListener);
		mFaceRoot = (LinearLayout) findViewById(R.id.face_ll);
		mFaceViewPager = (ViewPager) findViewById(R.id.face_pager);
		msgText.setOnKeyListener(new OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							if (mIsFaceShow) {
								mFaceRoot.setVisibility(View.GONE);
								mIsFaceShow = false;
								// imm.showSoftInput(msgEt, 0);
								return true;
							}
						}
						return false;
					}
				});
		msgText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						// TODO Auto-generated method stub
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						if (s.length() > 0) {
							mSendMsgBtn.setEnabled(true);
						} else {
							mSendMsgBtn.setEnabled(false);
						}
					}
				});
	}

		// ��ֹ��pageview�ҹ���
		private OnTouchListener forbidenScroll() {
			return new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						return true;
					}
					return false;
				}
			};
		}
		
	private GridView getGridView(int i) {
		// TODO Auto-generated method stub
		GridView gv = new GridView(this);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// ����GridViewĬ�ϵ��Ч��
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		gv.setGravity(Gravity.CENTER);
		gv.setAdapter(new FaceAdapter(this, i));
		gv.setOnTouchListener(forbidenScroll());
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == Myface.NUM) {// ɾ������λ��
					int selection = msgText.getSelectionStart();
					String text = msgText.getText().toString();
					if (selection > 0) {
						String text2 = text.substring(selection - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = selection;
							msgText.getText().delete(start, end);
							return;
						}
						msgText.getText()
								.delete(selection - 1, selection);
					}
				} else {
					int count = mCurrentPage * Myface.NUM + arg2;
					// ע�͵Ĳ��֣���EditText����ʾ�ַ���
					// String ori = msgEt.getText().toString();
					// int index = msgEt.getSelectionStart();
					// StringBuilder stringBuilder = new StringBuilder(ori);
					// stringBuilder.insert(index, keys.get(count));
					// msgEt.setText(stringBuilder.toString());
					// msgEt.setSelection(index + keys.get(count).length());

					// �����ⲿ�֣���EditText����ʾ����
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) Myface.getFaceMap().values().toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// ������������
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// �½�������
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// ����ͼƬ����ת�Ƕ�
						// matrix.postRotate(-30);
						// ����ͼƬ����б
						// matrix.postSkew(0.1f, 0.1f);
						// ��ͼƬ��Сѹ��
						// ѹ����ͼƬ�Ŀ�͸��Լ�kB��С����仯
						Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
								rawWidth, rawHeigh, matrix, true);
						ImageSpan imageSpan = new ImageSpan(ChatActivity.this,
								newBitmap);
						String emojiStr = mFaceMapKeys.get(count);
						SpannableString spannableString = new SpannableString(
								emojiStr);
						spannableString.setSpan(imageSpan,
								emojiStr.indexOf('['),
								emojiStr.indexOf(']') + 1,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						msgText.append(spannableString);
					} else {
						String ori = msgText.getText().toString();
						int index = msgText.getSelectionStart();
						StringBuilder stringBuilder = new StringBuilder(ori);
						stringBuilder.insert(index, mFaceMapKeys.get(count));
						msgText.setText(stringBuilder.toString());
						msgText.setSelection(index
								+ mFaceMapKeys.get(count).length());
					}
				}
			}
		});
		return gv;
	}
	
	/**
	 * ������Ϣ
	 */
	OnClickListener sendClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(bconnect){
				// ��ȡ��������
				String content = msgText.getText().toString();
				
				if (content.length() > 0) {
					// ��ʾ��������
					listMsg.add(new MyMessage(loginUser, content, TimeRender
							.getDate(), "OUT"));
					//���ͱ���
					
					// ˢ�·��͵���Ϣ������
					adapter.notifyDataSetChanged();
					Chat chat = ClientConServer.getChatManager().createChat(
							friendName + "@odile.openfire.com", null);
					try {
						chat.sendMessage(content);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(ChatActivity.this, "���������ݣ�", Toast.LENGTH_SHORT).show();
				}
				// ��շ�����Ϣ�����
				msgText.setText("");
			}
			else{
	        	 Toast.makeText(ChatActivity.this, "���ӷ�����ʧ�ܣ���������",
							Toast.LENGTH_SHORT).show();
	         }
		}
	};
	
	
	/**
	 * ���鷢��
	 */
	OnClickListener faceSwitchClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!mIsFaceShow) {
				mInputMethodManager.hideSoftInputFromWindow(
						msgText.getWindowToken(), 0);
				try {
					Thread.sleep(80);// �����ʱ���һ����Ļ������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				faceSwitchbt
				.setImageResource(R.drawable.facebtn_click);
				mFaceRoot.setVisibility(View.VISIBLE);
				mIsFaceShow = true;
			} else {
				mFaceRoot.setVisibility(View.GONE);
				mInputMethodManager.showSoftInput(msgText, 0);
				faceSwitchbt
				.setImageResource(R.drawable.facebtn);
				mIsFaceShow = false;
			}
	     }
    };
    

    /**
     * �˳�����
     */
	OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String str=""; 
			if(buserpinche && bfriendpinche){
				 bpinche=true;
				 str="ף������"+friendName+"ƴ���ɹ���";
			 }else{
				 bpinche=false;
				 str="δ��"+friendName+"ƴ���ɹ�������ƴ�����԰ɣ�";
			 }
			AlertDialog.Builder dialog=new AlertDialog.Builder(ChatActivity.this);
            dialog.setTitle("ȷ��").setIcon(android.R.drawable.ic_dialog_info)
            .setMessage(str)
            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {              
              @Override
              public void onClick(DialogInterface dialog, int which) {
		            if(bpinche){
		              	 pkapp.setpinche(bpinche);
		            	 Intent it=new Intent(ChatActivity.this,PkRouteActivity.class);
		           	     startActivity(it);
		           	     finish();		           	    
		            }else{
		              	 Intent it=new Intent(ChatActivity.this,AccessableUserListActivity.class);
		           	     finish();
		           	     startActivity(it);
		            }
              }
             })
            .setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
            	@Override
            	public void onClick(DialogInterface dialog, int which) {
                 dialog.cancel();//ȡ��������
              }
             }).create().show();
		 }		
	};
	
	

	/**
     * �˽����������Ϣ
     */
	OnClickListener userClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it=new Intent(ChatActivity.this,UserFriendActivity.class);
      	    startActivity(it);
		 }		
	};

	
	/**
	 * �㲥���պ�����Ϣ
	 */
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("msg")) {
				List<MyMessage> list = (List<MyMessage>) intent
						.getSerializableExtra("data");
				if (list == null || list.size() == 0)
					return;
				if(list.get(list.size()-1).msg.equals(friendName+"��ͬ��ƴ��")){
					bfriendpinche=true;
				}
				if(list.get(list.size()-1).msg.equals(friendName+"��ͬ��ƴ��")){
					bfriendpinche=false;
				}
				//list.get(list.size())
				// ��ʾ�Ự��Ϣ
				listMsg.addAll(list);
				// ˢ�½�����ʾ��Ϣ
				if (list.get(list.size()-1).userid.equals(friendName)) {					
					adapter.notifyDataSetChanged();
				}
			}
		}
	};

	//���ذ�ť���� 
		@Override
		  public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK){
				String str=""; 
				if(buserpinche && bfriendpinche){
					 bpinche=true;
					 str="ף������"+friendName+"ƴ���ɹ���";
				 }else{
					 bpinche=false;
					 str="δ��"+friendName+"ƴ���ɹ�������ƴ�����԰ɣ�";
				 }
				AlertDialog.Builder dialog=new AlertDialog.Builder(ChatActivity.this);
	            dialog.setTitle("ȷ��").setIcon(android.R.drawable.ic_dialog_info)
	            .setMessage(str)
	            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {              
	              @Override
	              public void onClick(DialogInterface dialog, int which) {
			            if(bpinche){
			            	pkapp.setpinche(bpinche);
			            	Intent it=new Intent(ChatActivity.this,PkRouteActivity.class);
			            	finish();
			           	    startActivity(it);
			           	    
			            }else{
			              	 Intent it=new Intent(ChatActivity.this,AccessableUserListActivity.class);
			           	     finish();
			           	     startActivity(it);
			            }
	              }
	             })
	            .setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
	            	@Override
	            	public void onClick(DialogInterface dialog, int which) {
	                 dialog.cancel();//ȡ��������
	              }
	             }).create().show();
		    }
		    return true;
		  }
	
	/**
	 * ע��㲥
	 */
	public void registerBoradcastReceiver() {
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("msg");
		registerReceiver(receiver, mIntentFilter);
	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	};
	
}