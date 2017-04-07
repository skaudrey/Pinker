package com.example.pinker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.pinker.R;

public class PkNotificationActivity extends Activity implements
OnGetGeoCoderResultListener,OnGetRoutePlanResultListener{
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
			
			OverlayManager routeOverlay = null;
		    boolean useDefaultIcon = false;
		    
		  //�������
		    RoutePlanSearch mSearch = null; 
			
			MapView mMapView;
			BaiduMap mBaiduMap;

			// UI���
			Button confirmArrived;
			
			//intent
			Intent intent;
			
			boolean isFirstLoc=true;
			
			//�����������
			GeoCoder mSearchGeo = null;
			
			PkApplication Pkapp;
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_notifymap);
				//intent--һ��Ҫд����Ȼ���׳�
				intent = getIntent();
				//Pkapp��ʼ��
				Pkapp=(PkApplication)getApplication();
				// ��ͼ��ʼ��
				mMapView = (MapView) findViewById(R.id.bmapView);
				mBaiduMap = mMapView.getMap();
				//UI��ʼ��
				confirmArrived=(Button)findViewById(R.id.confirmArrived);
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
				mSearchGeo = GeoCoder.newInstance();
				//ע����������--�������
				mSearchGeo.setOnGetGeoCodeResultListener(this);
				
				//ע����������--·���滮
				mSearch = RoutePlanSearch.newInstance();
		        mSearch.setOnGetRoutePlanResultListener(this);
		        PlanNode stNode=null;
		        PlanNode enNode=null;
		        
//		        //��ȡ���յ�
//		        if(intent.hasExtra("StartPointLat") &&
//		        		intent.hasExtra("StartPointLng")&&
//		        		intent.hasExtra("EndPointLat") && 
//		        		intent.hasExtra("EndPointLng")){
//		        	stNode = PlanNode.withLocation
//		        			(new LatLng(Pkapp.StartPointLat,Pkapp.StartPointLng));
//		            enNode = PlanNode.withLocation
//		        			(new LatLng(Pkapp.EndPointLat,Pkapp.EndPointLng));
//		        	
//		          //��������--·���滮
//			        mSearch.drivingSearch((new DrivingRoutePlanOption())
//		                    .from(stNode)
//		                    .to(enNode));
//		        }
//		        else if (intent.hasExtra("StartPointCity") &&
//		        		intent.hasExtra("StartPointStreets")&&
//		        		intent.hasExtra("EndPointCity") && 
//		        		intent.hasExtra("EndPointStreets")) {
//					// ������յ���Ϣ
//		        	stNode = PlanNode.withCityNameAndPlaceName(
//		            		Pkapp.spointInfoCity, 
//		            		Pkapp.spointInfoStreet);
//		            enNode = PlanNode.withCityNameAndPlaceName(
//		            		Pkapp.epointInfoCity, 
//		            		Pkapp.epointInfoStreet);
//		        	
//		          //��������--·���滮
//			        mSearch.drivingSearch((new DrivingRoutePlanOption())
//		                    .from(stNode)
//		                    .to(enNode));
//		        }
		        
		        //test
		        if(Pkapp.USER_NAME.equals("������")){
		        	stNode = PlanNode.withCityNameAndPlaceName(
		            		"�人", 
		            		"�人��ѧ");
		            enNode = PlanNode.withCityNameAndPlaceName(
		            		"�人", 
		            		"���");
		        }
		        else if(Pkapp.USER_NAME.equals("���")){
		        	stNode = PlanNode.withCityNameAndPlaceName(
		            		"�人", 
		            		"�人��ѧ");
		            enNode = PlanNode.withCityNameAndPlaceName(
		            		"�人", 
		            		"����ׯ");
		        }
		        
		            	
	          //��������--·���滮
		        mSearch.drivingSearch((new DrivingRoutePlanOption())
	                    .from(stNode)
	                    .to(enNode));
		        
		        confirmArrived.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent=new Intent(PkNotificationActivity.this,PkLocationMapActivity.class);
						
						Pkapp.currentLat=currentLatLng.latitude;
						Pkapp.currentLng=currentLatLng.longitude;
						Pkapp.currentLocCity=currentLocCity;
						Pkapp.currentLocStreet=currentLocDistrict+
								currentLocStreet+currentLocStreetNumber;
						
						Pkapp.setisarrive(true);
						startActivity(intent);
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
					mSearchGeo.reverseGeoCode(new ReverseGeoCodeOption()
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
				mSearchGeo.destroy();
				mSearch.destroy();
				super.onDestroy();
			}
			
			public void onGetGeoCodeResult(GeoCodeResult result) {}


			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
					if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(PkNotificationActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
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
			
			@Override
		    protected void onRestoreInstanceState(Bundle savedInstanceState) {
		        super.onRestoreInstanceState(savedInstanceState);
		    }

		    
		    public void onGetWalkingRouteResult(WalkingRouteResult result) {}

		    
		    public void onGetTransitRouteResult(TransitRouteResult result) {}

		  
		    public void onGetDrivingRouteResult(DrivingRouteResult result) {
		        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
		            Toast.makeText(PkNotificationActivity.this,
		            		"��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
		        }
		        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
		            //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ
		            return;
		        }
		        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
		            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
		            routeOverlay = overlay;
		            mBaiduMap.setOnMarkerClickListener(overlay);
		            overlay.setData(result.getRouteLines().get(0));
		            
		            overlay.addToMap();
		            overlay.zoomToSpan();     
		            
		            
		        }
		    }

		    //����RouteOverly
		    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
		            super(baiduMap);
		        }

		        @Override
		        public BitmapDescriptor getStartMarker() {
		            if (useDefaultIcon) {
		                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
		            }
		            return null;
		        }

		        @Override
		        public BitmapDescriptor getTerminalMarker() {
		            if (useDefaultIcon) {
		                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
		            }
		            return null;
		        }
		    }

}
