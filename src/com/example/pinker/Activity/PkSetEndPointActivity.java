package com.example.pinker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.pinker.R;
import com.example.pinker.mapservice.MyView;


/**
 * 设置终点位置地图Activity
 * @author Skory
 *传入：Intent
 *字段：EndPointCity,EndPointStreets
 */

public class PkSetEndPointActivity extends Activity implements
			OnGetGeoCoderResultListener{
	@SuppressWarnings("unused")
	private static final String LTAG = 
			PkSetEndPointActivity.class.getSimpleName();
	/**
	 * MapView 是地图主控件
	*/
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private InfoWindow mInfoWindow;
	
	
	//地理编码搜索
	GeoCoder mSearch = null;
	
	//终点经纬度
	LatLng endP=null;
	//记录地址
	String pointInfo;
	String epointInfoCity;
	String epointInfoStreet;
	
	//Intent
	Intent intent;
	//用户拾取的点
	private LatLng pickedP=null;
	
	PkApplication PKApp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_epoint);

		PKApp=(PkApplication)getApplication();
		//Map初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		///搜索引擎初始化
		mSearch = GeoCoder.newInstance();
		//注册搜索监听
		mSearch.setOnGetGeoCodeResultListener(this);
		
		/**
		 * 从私人订制表单页面传intent--终点信息
		 * 设置中心点为指定点
		 */
		intent = getIntent();
		Bundle b = intent.getExtras();
		if(PKApp.getepointInfoCity()!=null && PKApp.getepointInfoStreet()!=null){
			// Geo搜索
						mSearch.geocode(new GeoCodeOption().city(
								PKApp.getepointInfoCity())
								.address(
										PKApp.getepointInfoStreet()));
						//设置地图以目标点为中心点
						mMapView = new MapView(this,
								new BaiduMapOptions().mapStatus(new MapStatus.Builder()
										.target(endP).build()));
		} 
		else
		{
			//设置地图以目标点为中心点
			mMapView = new MapView(this,
					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
							.target(new LatLng(PKApp.getcurrentLat(),
									PKApp.getcurrentLng())).
									build()));
			
		}
		setContentView(mMapView);
		mBaiduMap = mMapView.getMap();
		//注册地图单击监听
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {		
			
		    /** 
			    * 地图单击事件回调函数 
			    * @param point 点击的地理坐标 
			    */  
			    public void onMapClick(LatLng point){
			    	/**
			    	 * @author Skory
			    	 * 在反向编码得到语义地址后将其赋给TextView
			    	 */
			    	mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(point));
			    	pickedP=point;
			    	/**
					 * 自定义view
					 */
			    	MyView view=new MyView(getApplicationContext());
			    	
			    	view.pointInfoTv.setText(pointInfo);
			    	
			    	view.setAsSpointButton.setText("设为终点");
			    	
			    	view.setAsSpointButton.setOnClickListener(
							new OnClickListener() {
						public void onClick(View v) {
							endP=pickedP;
							/*
							intent.putExtra("EndPointLat", 
									endP.latitude);
							intent.putExtra("EndPointLng", 
									endP.longitude);							
							intent.putExtra("epointInfoCity",
									epointInfoCity);
							intent.putExtra("epointInfoStreet",
									epointInfoStreet);*/
							PKApp.setEndPointLat(endP.latitude);
							PKApp.setEndPointLng(endP.longitude);
							PKApp.setepointInfoCity(epointInfoCity);
							PKApp.setepointInfoStreet(epointInfoStreet);
							mBaiduMap.hideInfoWindow();
							Intent it=new Intent(PkSetEndPointActivity.this,SubmitActivity.class);
							startActivity(it);
							PkSetEndPointActivity.this.finish();
							
						}
					});
			    	view.cancelButton.setOnClickListener(
							new OnClickListener() {
						public void onClick(View v) {
												
							mBaiduMap.hideInfoWindow();
						}
					});
					mInfoWindow =new InfoWindow(view, pickedP, -20);
					mBaiduMap.showInfoWindow(mInfoWindow);
			    }  
			    /** 
			    * 地图内 Poi 单击事件回调函数 
			    * @param poi 点击的 poi 信息 
			    */  
			    public boolean onMapPoiClick(MapPoi poi) {
			    	return false;
			    	}
			});	
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
		mSearch.destroy();
	}
	
	//监听--地理编码
		public void onGetGeoCodeResult(GeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(PkSetEndPointActivity.this, "抱歉，未能找到结果", 
						Toast.LENGTH_LONG).show();
				return;
			}
			mBaiduMap.clear();
			mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_gcoding)));
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
					.getLocation()));
		}

		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(PkSetEndPointActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
						.show();
				return;
			}
			pointInfo=result.getAddress();
			epointInfoCity=result.getAddressDetail().city;	
			epointInfoStreet=result.getAddressDetail().district+result.getAddressDetail().street+result.getAddressDetail().streetNumber;
		}
		
		//返回按钮重载 
		@Override
		  public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK){
		    	Intent intent=new Intent(PkSetEndPointActivity.this,SubmitActivity.class);
		    	PkSetEndPointActivity.this.finish();
		    	startActivity(intent);
		    }
		    return true;
		  }
}
