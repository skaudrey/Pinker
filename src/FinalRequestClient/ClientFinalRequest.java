package FinalRequestClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ClientFinalRequest {
		
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
		public static String queryStringForPost(String finalrequest,String url) throws UnsupportedEncodingException {
			// ����url���HttpPost����
			HttpPost request = ClientFinalRequest.getHttpPost(url);
			String result = null;
			//�Դ��ݲ������з�װ
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//��ֹ��������
			finalrequest=URLEncoder.encode(URLEncoder.encode(finalrequest, "UTF-8"), "UTF-8");
			params.add(new BasicNameValuePair("finalrequest",finalrequest));
			try {
				//�������������
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				// �����Ӧ����
				HttpResponse response = ClientFinalRequest.getHttpResponse(request);
				// �ж��Ƿ�����ɹ�
				if(response.getStatusLine().getStatusCode()==200){
					// �����Ӧ
					result = EntityUtils.toString(response.getEntity(),"UTF_8");
					
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			return result;
	    }
}
