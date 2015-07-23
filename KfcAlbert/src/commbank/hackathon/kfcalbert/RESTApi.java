package commbank.hackathon.kfcalbert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

public class RESTApi {
	
	private static String tag = ".RESTApi";
	
	public static String GET(String getUrl){
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = client.execute(new HttpGet(getUrl));
			InputStream in = response.getEntity().getContent();
			
			if(in!=null){
	        	BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(in));
	            String line = "";
	            String result = "";
	            while((line = bufferedReader.readLine()) != null)
	                result += line;
	     
	            in.close();
	            return result;
	        }
		} catch (Exception e){
			e.printStackTrace();
		}
        
		return null;
	}
	
	public static boolean POST(JSONObject data, String postUrl){
		int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpClient client = new DefaultHttpClient(httpParams);

		HttpPost request = new HttpPost(postUrl);
		try {
			request.setEntity(new ByteArrayEntity(
			    data.toString().getBytes("UTF8")));
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				return true;
			};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean PUT(JSONObject data, String url){
		return false;
	}
	
	public static boolean DELETE(String url){
		return false;
	}
}
