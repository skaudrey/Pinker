package com.example.pinker.UprequestClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ClientUprequest {
		
		// ���Post�������request
		public static HttpPost getHttpPost(String url){
			 HttpPost request = new HttpPost(url);
			 return request;
		}
		
		// ������������Ӧ����response
		public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
			HttpResponse response = new DefaultHttpClient().execute(request);
			return response;
		}
		
		// ����Post���󣬻����Ӧ��ѯ���
		
		@SuppressWarnings("deprecation")
		//���ӣ�(1,1,2,"201401010200",30,"201401010600",30,"Ů",15,20,"Ư��",url);
		public static String queryStringForPost(String up_request,String up_requestpoints, String url) throws UnsupportedEncodingException {
			// ����url���HttpPost����
			HttpPost request = ClientUprequest.getHttpPost(url);
			String result = null;
			//�Դ��ݲ������з�װ
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			up_request=URLEncoder.encode(URLEncoder.encode(up_request, "UTF-8"), "UTF-8");
			params.add(new BasicNameValuePair("up_request",up_request));
			up_requestpoints=URLEncoder.encode(URLEncoder.encode(up_requestpoints, "UTF-8"), "UTF-8");
			params.add(new BasicNameValuePair("up_requestpoints",up_requestpoints));
			try {
				//�������������
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				// �����Ӧ����
				HttpResponse response = ClientUprequest.getHttpResponse(request);
				// �ж��Ƿ�����ɹ�
				if(response.getStatusLine().getStatusCode()==200){
					// �����Ӧ
					result = EntityUtils.toString(response.getEntity(),"UTF_8");
				}
			}catch (ClientProtocolException e) {
				e.printStackTrace();
				result = "�����쳣!"+","+"0";
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				result = "�����쳣!"+","+"0";
				return result;
			}
	        return result;
	    }
		
		//��string��,.�ֿ�ת��ΪResultUprequestList
		public static List<ResultUprequestList> getresultlist(String str){
			List <ResultUprequestList> resultlist = new ArrayList<ResultUprequestList>();  
			String[] string=str.split("\\.");
			for(int i=0;i<string.length;i++){
				String[] st=string[i].split(",");
				ResultUprequestList result=new ResultUprequestList();
            	result.rID=Integer.parseInt(st[0]);
            	result.rusername=st[1];
            	result.rgender=st[2];
            	result.rage=Integer.parseInt(st[3]);
            	result.rsignature=st[4];
            	result.rcreditdegree=Integer.parseInt(st[5]);
            	
            	result.rstartpointid=Integer.parseInt(st[6]);
            	result.rstartcity=st[7];
            	result.rstartstreet=st[8];
            	
                result.rendpointid=Integer.parseInt(st[9]);
                result.rendcity=st[10];
            	result.rendstreet=st[11];
                
                result.rsttime1=st[12];
                result.rsttime2=st[13];
                result.redtime1=st[14];
                result.redtime2=st[15];
                resultlist.add(result);
			}
			return resultlist;
		}
}
