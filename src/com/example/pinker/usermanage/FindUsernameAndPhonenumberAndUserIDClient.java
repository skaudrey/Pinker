package com.example.pinker.usermanage;

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

public class FindUsernameAndPhonenumberAndUserIDClient {
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
		public static String queryStringForPost(String usernameOrPhonenumber,String url) throws UnsupportedEncodingException {
			// ����url���HttpPost����
			HttpPost request = LoginClient.getHttpPost(url);
			String result = null;
			//�Դ��ݲ������з�װ
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//��ֹ��������
			usernameOrPhonenumber=URLEncoder.encode(URLEncoder.encode(usernameOrPhonenumber, "UTF-8"), "UTF-8");
			//�����û���������
			params.add(new BasicNameValuePair("usernameOrPhonenumber",usernameOrPhonenumber));
			try {
				//�������������
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				// �����Ӧ����
				HttpResponse response = LoginClient.getHttpResponse(request);
				// �ж��Ƿ�����ɹ�
				if(response.getStatusLine().getStatusCode()==200){
					// �����Ӧ
					result = EntityUtils.toString(response.getEntity(),"UTF_8");
					//result=new  String(result.getBytes("8859_1"),"GB2312"); // ����Ҫ�ɲ�Ҫ�����㲻��������Ϊ׼
					return result;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				result ="�����쳣!"+","+"0";
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				result = "�����쳣!"+","+"0";
				return result;
			}
	        return null;
	    }
}