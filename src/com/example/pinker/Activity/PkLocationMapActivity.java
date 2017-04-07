package com.example.pinker.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.pinker.R;
import com.example.pinker.chatconnect.ClientConServer;
import com.example.pinker.chatservice.ChatActivity;
import com.example.pinker.systemservice.SystemAdviceActivity;
import com.example.pinker.systemservice.SystemPeopleActivity;
import com.example.pinker.systemservice.UpdateUserInformationActivity;

/**
 * ��activity������λ����ʹ��MyLocationOverlay���ƶ�λλ�� 
 * ���ڸ�����⵽���˵�λ�ý��л��ƣ��ڵ����־�����ʾ��ϸ��Ϣ
 * 
 */
public class PkLocationMapActivity extends Activity implements
OnGetGeoCoderResultListener{
		
		// ��λ���
		LocationClient mLocClient;
		public MyLocationListenner myListener = new MyLocationListenner();
		
		// ��¼�û���ǰλ��
		LatLng currentLatLng;
		String currentLocCity;
		String currentLocProvince;
		String currentLocDistrict;
		String currentLocStreet;
		String currentLocStreetNumber;
		
		MapView mMapView;
		BaiduMap mBaiduMap;

		// UI���
		boolean isFirstLoc = true;// �Ƿ��״ζ�λ
		Button individualSetting=null;
		ImageButton btsystem;
		//intent
		Intent intent;
		PkApplication PKApp;
		//�����������
		GeoCoder mSearch = null;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_location);
			PKApp=(PkApplication)getApplication();
			//UI
			individualSetting=(Button)findViewById(R.id.individualSetting);	
			btsystem=(ImageButton)findViewById(R.id.btsystem);
			//intent--һ��Ҫд����Ȼ���׳�
			intent = getIntent();
			// ��ͼ��ʼ��
			mMapView = (MapView) findViewById(R.id.bmapView);
			mBaiduMap = mMapView.getMap();
			// ������λͼ��
			mBaiduMap.setMyLocationEnabled(true);
			// ��λ��ʼ��
			mLocClient = new LocationClient(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// ��gps
			option.setCoorType("bd09ll"); // ������������
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();
			//���������ʼ��
			mSearch = GeoCoder.newInstance();
			//ע����������
			mSearch.setOnGetGeoCodeResultListener(this);
			
			//˽�˶��ư�ťע��
			individualSetting.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
							Intent intent=new Intent(PkLocationMapActivity.this,SubmitActivity.class);
							PKApp.setcurrentLocCity(currentLocCity);
							PKApp.setcurrentLocStreet(currentLocStreet);
				        	PKApp.setcurrentLat(currentLatLng.latitude);
				        	PKApp.setcurrentLng(currentLatLng.longitude);
							/*intent.putExtra(AllInfo.currentLocCity, currentLocCity);
							intent.putExtra(AllInfo.currentLocStreet, currentLocStreet);
							intent.putExtra(AllInfo.currentLat, currentLatLng.latitude);
							intent.putExtra(AllInfo.currentLng, currentLatLng.longitude);*/
							startActivity(intent);
					}
				});
			//����ϵͳ��ť�¼�
			btsystem.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		showPopupWindow(v);
		    	}
		    });
		}

	    private void showPopupWindow(View view) {

	        // һ���Զ���Ĳ��֣���Ϊ��ʾ������
	        View contentView = LayoutInflater.from(this).inflate(
	                R.layout.system_items, null);
	        PopupWindow mPop = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
	        	                    LayoutParams.WRAP_CONTENT, true);
	        mPop.setWidth(250);
	        mPop.setHeight(210);//���õ������С
	        mPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.systembg));
	        mPop.setOutsideTouchable(true);
	        mPop.showAsDropDown(view);
	        // ���ð�ť�ĵ���¼�
	        //���˹���
	        ((Button)contentView.findViewById(R.id.bt_user)).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent it=new Intent(PkLocationMapActivity.this,UpdateUserInformationActivity.class);		
	            	startActivity(it);
	            }
	        });
	        //�ղ�·��
	        /*((Button)contentView.findViewById(R.id.bt_routes)).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
						
	            }
	        });
	        //���ѹ���
	        ((Button)contentView.findViewById(R.id.bt_people)).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent it=new Intent(PkLocationMapActivity.this,SystemPeopleActivity.class);		
	            	startActivity(it); 
	            }
	        });*/
	        //�����뷴��
	        ((Button)contentView.findViewById(R.id.bt_advice)).setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	Intent it=new Intent(PkLocationMapActivity.this,SystemAdviceActivity.class);
					startActivity(it); 
	            }
	        });
	        //�˳�
	        ((Button)contentView.findViewById(R.id.bt_exit)).setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	android.os.Process.killProcess(android.os.Process.myPid()); //��ȡPID
	            	System.exit(0);
	            }
	        });
	    }  
		/**
		 * ��λSDK��������
		 * ��ǰλ����ӱ��
		 * ��ǰλ�÷������
		 */
		public class MyLocationListenner implements BDLocationListener {

			
			public void onReceiveLocation(BDLocation location) {
				// map view ���ٺ��ڴ����½��յ�λ��
				if (location == null || mMapView == null)
					return;
				MyLocationData locData = new MyLocationData.Builder()
						.accuracy(location.getRadius())
						// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
						.direction(100).latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();
				mBaiduMap.setMyLocationData(locData);
				/**
				 * @author Skory
				 * ��¼�û���ǰλ��
				 */
				currentLatLng=new LatLng(locData.latitude, locData.longitude);
				
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(),
							location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					/**
					 * @author Skory
					 * ��¼�û���ǰλ��
					 */
					currentLatLng=ll;
					// ���ö�λͼ������ã���λģʽ���Ƿ���������Ϣ���û��Զ��嶨λͼ�꣩  
					BitmapDescriptor mCurrentMarker = 
							BitmapDescriptorFactory  
					    .fromResource(R.drawable.icon_gcoding);
					LocationMode mCurrentMode=LocationMode.NORMAL;
					MyLocationConfiguration config = 
							new MyLocationConfiguration(
									mCurrentMode, false, mCurrentMarker);  
					mBaiduMap.setMyLocationConfigeration(config);
					
					mBaiduMap.animateMapStatus(u);

				}
				//�Ե�ǰ������з������
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(currentLatLng));
			}
			
			public void onReceivePoi(BDLocation poiLocation) {}
		}
			
		@Override
		protected void onPause() {
			mMapView.onPause();
			super.onPause();
		}

		@Override
		protected void onResume() {
			mMapView.onResume();
			super.onResume();
		}

		@Override
		protected void onDestroy() {			
			// �˳�ʱ���ٶ�λ
			mLocClient.stop();
			// �رն�λͼ��
			mBaiduMap.setMyLocationEnabled(false);
			mBaiduMap.clear();
			mMapView.onDestroy();
			mMapView = null;
			//������������
			mSearch.destroy();
			super.onDestroy();
		}
		
		public void onGetGeoCodeResult(GeoCodeResult result) {}


		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(PkLocationMapActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
						.show();
				return;
			}
				/**
				 * @author Skory
				 * intent��¼��ǰλ��
				 */
				currentLocProvince=result.getAddressDetail().province;
				currentLocCity=result.getAddressDetail().city;
				currentLocDistrict=result.getAddressDetail().district;
				currentLocStreet=result.getAddressDetail().street;
				currentLocStreetNumber=result.getAddressDetail().streetNumber;
				
		}

}
