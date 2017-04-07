package com.example.pinker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
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
 * �������λ�õ�ͼActivity
 * @author Skory
 *���룺Intent
 *�ֶΣ�StartPointCity,StartPointStreets
 */

public class PkSetStartPointActivity extends Activity implements
			OnGetGeoCoderResultListener{
	@SuppressWarnings("unused")
	private static final String LTAG = PkSetStartPointActivity.class.getSimpleName();
	/**
	 * MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private InfoWindow mInfoWindow;
	
	//�����������
	GeoCoder mSearch = null;
	
	//��¼��ַ
	String pointInfo;
	String spointInfoCity;
	String spointInfoStreet;
	//��㾭γ��
	LatLng startP=null;
	
	
	//Intent
	Intent intent;

	PkApplication PKApp;
	
	//�û�ʰȡ�ĵ�
	private LatLng pickedP=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_spoint);
		PKApp=(PkApplication)getApplication();
		//Map��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		
		//���������ʼ��
		mSearch = GeoCoder.newInstance();
		//ע����������
		mSearch.setOnGetGeoCodeResultListener(this);
		
		/**
		 * ��˽�˶��Ʊ�ҳ�洫intent--�����Ϣ
		 */
		intent = getIntent();	
		if(PKApp.getspointInfoCity()!=null && PKApp.getspointInfoStreet()!=null){
			// Geo����
						mSearch.geocode(new GeoCodeOption().city(
								PKApp.getspointInfoCity())
								.address(
										PKApp.getspointInfoStreet()));
						//���õ�ͼ��Ŀ���Ϊ���ĵ�
						mMapView = new MapView(this,
								new BaiduMapOptions().mapStatus(new MapStatus.Builder()
										.target(startP).build()));
		} else 
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
			    	
			    	view.setAsSpointButton.setOnClickListener(
							new OnClickListener() {
						public void onClick(View v) {
							startP=pickedP;
							PKApp.setStartPointLat(startP.latitude);
							PKApp.setStartPointLng(startP.longitude);
							PKApp.setspointInfoCity(spointInfoCity);
							PKApp.setspointInfoStreet(spointInfoStreet);
							mBaiduMap.hideInfoWindow();
							
							Intent it=new Intent(PkSetStartPointActivity.this,SubmitActivity.class);
							startActivity(it);
							PkSetStartPointActivity.this.finish();
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
		mSearch.destroy();
		mMapView.onDestroy();
	}
	//����--�������
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PkSetStartPointActivity.this, "��Ǹ��δ���ҵ����", 
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
	//����--����������
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PkSetStartPointActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
					.show();
			return;
		}
		
		pointInfo=result.getAddress();
		spointInfoCity=result.getAddressDetail().city;	
		spointInfoStreet=result.getAddressDetail().district+result.getAddressDetail().street+result.getAddressDetail().streetNumber;
	}
	
	//���ذ�ť���� 
	@Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK){
	    	Intent intent=new Intent(PkSetStartPointActivity.this,SubmitActivity.class);
	    	PkSetStartPointActivity.this.finish();
	    	startActivity(intent);
	    }
	    return true;
	  }
}



