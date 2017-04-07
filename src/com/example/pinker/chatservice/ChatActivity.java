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
	public static String friendstatus="在线";
	public Button mSendMsgBtn;
	//是否联网
	public boolean bconnect;
	//是否拼车
	public boolean buserpinche=false;
	public boolean bfriendpinche=false;
	public boolean bpinche=false;
	//表情相关
	private ImageButton faceSwitchbt;
	private LinearLayout mFaceRoot;// 表情父容器
	private boolean mIsFaceShow = false;// 是否显示表情
	private InputMethodManager mInputMethodManager;
	private ViewPager mFaceViewPager;// 表情选择ViewPager
	private int mCurrentPage = 0;// 当前表情页
	private List<String> mFaceMapKeys;// 表情对应的字符串数组
	
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
		    		friendstatus="在线";//ClientConServer.getPresence(friendName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
        }).start();	
			Toast.makeText(ChatActivity.this, "联网成功",
					Toast.LENGTH_SHORT).show();	   
		initViews();
		initFacePage();
		registerBoradcastReceiver();
	}


	
	/**
	 * 初始化表情
	 */
	private void initFacePage() {
		// 将表情map的key保存在数组中
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
	 * 初始化界面
	 */
	private void initViews() {
		ListView listview = (ListView) findViewById(R.id.listview);
		this.msgText = (EditText) findViewById(R.id.text);
		adapter = new MessageAdapter(getApplicationContext(), listMsg);
		listview.setAdapter(adapter);
		
		// 获取用户输入的聊天信息
		((TextView) findViewById(R.id.chatTitle)).setText("与" + friendName
				+ "("+friendstatus+")"+"聊天中");
		//监听按钮
		//发送消息
		mSendMsgBtn=(Button)findViewById(R.id.btsend);
		mSendMsgBtn.setOnClickListener(sendClickListener);
		//退出聊天
		findViewById(R.id.btback).setOnClickListener(backClickListener);
		//了解用户
		findViewById(R.id.btuser).setOnClickListener(userClickListener);
		//拼车与否
		//根据ID找到RadioGroup实例
		RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
		//绑定一个匿名监听器
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            
         @Override   
         public void onCheckedChanged(RadioGroup group, int checkedId) {   
             // TODO Auto-generated method stub   
        	 if(bconnect){
	        	 String str = "";
	        	 if(checkedId==findViewById(R.id.radiotrue).getId())   
	             {   
	        		 buserpinche=true;
	        		 str=loginUser +"已同意拼车"; 
	             }else   
	             {   
	            	 str=loginUser +"不同意拼车";   
	             }  
   		         // 显示发送内容
 				 listMsg.add(new MyMessage(loginUser, str, TimeRender
 						.getDate(), "OUT"));
 				 // 刷新发送的消息到界面
 				 adapter.notifyDataSetChanged();
 				 Chat chat = ClientConServer.getChatManager().createChat(
 						friendName + "@odile.openfire.com", null);
 				 try {
 					chat.sendMessage(str);
 				 } catch (XMPPException e) {
 					e.printStackTrace();
 				 }
	         }else{
	        	 Toast.makeText(ChatActivity.this, "连接服务器失败！请检查网络",
							Toast.LENGTH_SHORT).show();
	         }
           }   
        });   

		//点击表情
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

		// 防止乱pageview乱滚动
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
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
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
				if (arg2 == Myface.NUM) {// 删除键的位置
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
					// 注释的部分，在EditText中显示字符串
					// String ori = msgEt.getText().toString();
					// int index = msgEt.getSelectionStart();
					// StringBuilder stringBuilder = new StringBuilder(ori);
					// stringBuilder.insert(index, keys.get(count));
					// msgEt.setText(stringBuilder.toString());
					// msgEt.setSelection(index + keys.get(count).length());

					// 下面这部分，在EditText中显示表情
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) Myface.getFaceMap().values().toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// 计算缩放因子
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// 新建立矩阵
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// 设置图片的旋转角度
						// matrix.postRotate(-30);
						// 设置图片的倾斜
						// matrix.postSkew(0.1f, 0.1f);
						// 将图片大小压缩
						// 压缩后图片的宽和高以及kB大小均会变化
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
	 * 发送消息
	 */
	OnClickListener sendClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(bconnect){
				// 获取发送内容
				String content = msgText.getText().toString();
				
				if (content.length() > 0) {
					// 显示发送内容
					listMsg.add(new MyMessage(loginUser, content, TimeRender
							.getDate(), "OUT"));
					//发送表情
					
					// 刷新发送的消息到界面
					adapter.notifyDataSetChanged();
					Chat chat = ClientConServer.getChatManager().createChat(
							friendName + "@odile.openfire.com", null);
					try {
						chat.sendMessage(content);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(ChatActivity.this, "请输入内容！", Toast.LENGTH_SHORT).show();
				}
				// 清空发送消息输入框
				msgText.setText("");
			}
			else{
	        	 Toast.makeText(ChatActivity.this, "连接服务器失败！请检查网络",
							Toast.LENGTH_SHORT).show();
	         }
		}
	};
	
	
	/**
	 * 表情发送
	 */
	OnClickListener faceSwitchClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!mIsFaceShow) {
				mInputMethodManager.hideSoftInputFromWindow(
						msgText.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
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
     * 退出聊天
     */
	OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String str=""; 
			if(buserpinche && bfriendpinche){
				 bpinche=true;
				 str="祝贺你与"+friendName+"拼车成功！";
			 }else{
				 bpinche=false;
				 str="未与"+friendName+"拼车成功。换个拼友试试吧！";
			 }
			AlertDialog.Builder dialog=new AlertDialog.Builder(ChatActivity.this);
            dialog.setTitle("确认").setIcon(android.R.drawable.ic_dialog_info)
            .setMessage(str)
            .setNegativeButton("确定", new DialogInterface.OnClickListener() {              
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
            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
            	@Override
            	public void onClick(DialogInterface dialog, int which) {
                 dialog.cancel();//取消弹出框
              }
             }).create().show();
		 }		
	};
	
	

	/**
     * 了解聊天对象信息
     */
	OnClickListener userClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it=new Intent(ChatActivity.this,UserFriendActivity.class);
      	    startActivity(it);
		 }		
	};

	
	/**
	 * 广播接收好友消息
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
				if(list.get(list.size()-1).msg.equals(friendName+"已同意拼车")){
					bfriendpinche=true;
				}
				if(list.get(list.size()-1).msg.equals(friendName+"不同意拼车")){
					bfriendpinche=false;
				}
				//list.get(list.size())
				// 显示会话消息
				listMsg.addAll(list);
				// 刷新界面显示消息
				if (list.get(list.size()-1).userid.equals(friendName)) {					
					adapter.notifyDataSetChanged();
				}
			}
		}
	};

	//返回按钮重载 
		@Override
		  public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK){
				String str=""; 
				if(buserpinche && bfriendpinche){
					 bpinche=true;
					 str="祝贺你与"+friendName+"拼车成功！";
				 }else{
					 bpinche=false;
					 str="未与"+friendName+"拼车成功。换个拼友试试吧！";
				 }
				AlertDialog.Builder dialog=new AlertDialog.Builder(ChatActivity.this);
	            dialog.setTitle("确认").setIcon(android.R.drawable.ic_dialog_info)
	            .setMessage(str)
	            .setNegativeButton("确定", new DialogInterface.OnClickListener() {              
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
	            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
	            	@Override
	            	public void onClick(DialogInterface dialog, int which) {
	                 dialog.cancel();//取消弹出框
	              }
	             }).create().show();
		    }
		    return true;
		  }
	
	/**
	 * 注册广播
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