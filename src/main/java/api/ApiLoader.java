package api;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiLoader {
	
	private final static String baseUrl = "http://openapi.seoul.go.kr:8088/%s/json/TbPublicWifiInfo/%d/%d";
	
	public String apiLoad(String apikey, int start, int end) {
		
		String result;
		
		String url = String.format(baseUrl, apikey, start, end);
		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url).build();
		
		try {
			Response response = client.newCall(request).execute();
			
			//응답코드가 정상이 아닌경우.
			if(response.code() != 200) {
				throw new RuntimeException("Something is wrong?");
			}
			
			String msg = response.body().string();
			
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}
