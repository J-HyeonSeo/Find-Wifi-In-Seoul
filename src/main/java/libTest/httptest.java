package libTest;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class httptest {
	public static String callDatafromApi() {
		String url = "http://openapi.seoul.go.kr:8088/sample/json/TbPublicWifiInfo/1/5";
		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url).build();
		
		try {
			Response response = client.newCall(request).execute();
			String msg = response.body().string();
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
