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
 * �����յ�λ�õ�ͼActivity
 * @author Skory
 *���룺Intent
 *�ֶΣ�EndPointCity,EndPointStreets
 */

public class PkSetEndPointActivity extends Activity implements
			OnGetGeoCoderResultListener{
	@SuppressWarnings("unused")
	private static final String LTAG = 
			PkSetEndPointActivity.class.getSimpleName();
	/**
	 * MapView �ǵ�ͼ���ؼ�
	*/
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private InfoWindow mInfoWindow;
	
	
	//�����������
	GeoCoder mSearch = null;
	
	//�յ㾭γ��
	LatLng endP=null;
	//��¼��ַ
	String pointInfo;
	String epointInfoCity;
	String epointInfoStreet;
	
	//Intent
	Intent intent;
	//�û�ʰȡ�ĵ�
	private LatLng pickedP=null;
	
	PkApplication PKApp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_epoint);

		PKApp=(PkApplication)getApplication();
		//Map��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		///���������ʼ��
		mSearch = GeoCoder.newInstance();
		//ע����������
		mSearch.setOnGetGeoCodeResultListener(this);
		
		/**
		 * ��˽�˶��Ʊ�ҳ�洫intent--�յ���Ϣ
		 * �������ĵ�Ϊָ����
		 */
		intent = getIntent();
		Bundle b = intent.getExtras();
		if(PKApp.getepointInfoCity()!=null && PKApp.getepointInfoStreet()!=null){
			// Geo����
						mSearch.geocode(new GeoCodeOption().city(
								PKApp.getepointInfoCity())
								.address(
										PKApp.getepointInfoStreet()));
						//���õ�ͼ��Ŀ���Ϊ���ĵ�
						mMapView = new MapView(this,
								new BaiduMapOptions().mapStatus(new MapStatus.Builder()
										.target(endP).build()));
		} 
		else
		{
			//���õ�ͼ��Ŀ���Ϊ���ĵ�
			mMapView = new MapView(this,
					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
							.target(new LatLng(PKApp.getcurrentLat(),
									PKApp.getcurrentLng())).
									build()));
			
		}
		setContentView(mMapView);
		mBaiduMap = mMapView.getMap();
		//ע���ͼ��������
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {		
			
		    /** 
			    * ��ͼ�����¼��ص����� 
			    * @param point ����ĵ������� 
			    */  
			    public void onMapClick(LatLng point){
			    	/**
			    	 * @author Skory
			    	 * �ڷ������õ������ַ���丳��TextView
			    	 */
			    	mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(point));
			    	pickedP=point;
			    	/**
					 * �Զ���view
					 */
			    	MyView view=new MyView(getApplicationContext());
			    	
			    	view.pointInfoTv.setText(pointInfo);
			    	
			    	view.setAsSpointButton.setText("��Ϊ�յ�");
			    	
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
			    * ��ͼ�� Poi �����¼��ص����� 
			    * @param poi ����� poi ��Ϣ 
			    */  
			    public boolean onMapPoiClick(MapPoi poi) {
			    	return false;
			    	}
			});	
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity ��ͣʱͬʱ��ͣ��ͼ�ؼ�
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity �ָ�ʱͬʱ�ָ���ͼ�ؼ�
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity ����ʱͬʱ���ٵ�ͼ�ؼ�
		mMapView.onDestroy();
		mSearch.destroy();
	}
	
	//����--�������
		public void onGetGeoCodeResult(GeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(PkSetEndPointActivity.this, "��Ǹ��δ���ҵ����", 
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
				Toast.makeText(PkSetEndPointActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
						.show();
				return;
			}
			pointInfo=result.getAddress();
			epointInfoCity=result.getAddressDetail().city;	
			epointInfoStreet=result.getAddressDetail().district+result.getAddressDetail().street+result.getAddressDetail().streetNumber;
		}
		
		//���ذ�ť���� 
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
