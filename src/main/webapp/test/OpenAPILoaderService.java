package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import api.ApiLoader;
import repository.WifiRepository;

public class OpenAPILoaderService {
	
	public static String API_KEY;
	private WifiRepository wifiRepository;
	
	public OpenAPILoaderService(WifiRepository wifiRepository) {
		this.wifiRepository = wifiRepository;
	}
	
	//application.properties 의 api.key에 적혀있는 값을 가져와 변수에 할당함.
    static {
        Properties properties = new Properties();
        try (InputStream inputStream = OpenAPILoaderService.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            API_KEY = properties.getProperty("api.key");
            System.out.println(API_KEY);
        } catch (IOException e) {
            // 파일 로드 오류 처리
            API_KEY = null;
        }
    }
	
	public String loadFromApi() throws InterruptedException {
		JsonParser parser = new JsonParser(); //json parser
		
		//일단 api를 한 번 호출하여, 최대 데이터 갯수를 읽어옴.
		
		String initString = ApiLoader.apiLoad("sample", 1, 1);
		
		JsonObject initObject = (JsonObject)parser.parse(initString);
		JsonObject contents = (JsonObject) initObject.get("TbPublicWifiInfo");
		int max = contents.get("list_total_count").getAsInt();
		
		//OpenAPI를 통해 와이파이 정보를 새로 가져오면, 테이블 데이터 날려버림.
		wifiRepository.deleteTable();
		
		//속도 측정.
		Long start = System.currentTimeMillis();
		
		ArrayList<ArrayList<Object>> insertDatas = new ArrayList<>();
		
		//1000개씩 페이징하면서, 모든 와이파이 리스트를 가져옴.
		for(int i = 1; i < max; i += 1000) {
			String page = ApiLoader.apiLoad(API_KEY, i, i + 999);
			
			JsonObject jsonObject = (JsonObject)parser.parse(page);
			
			JsonArray jsonArray = (JsonArray)((JsonObject)jsonObject.get("TbPublicWifiInfo")).get("row");
			
			for(int j = 0; j < jsonArray.size(); j++) {
				
				JsonObject now = (JsonObject)jsonArray.get(j);
				
				ArrayList<Object> arguments = new ArrayList<>();
				
				arguments.add(now.get("X_SWIFI_MGR_NO").getAsString());
				arguments.add(now.get("X_SWIFI_WRDOFC").getAsString());
				arguments.add(now.get("X_SWIFI_MAIN_NM").getAsString());
				arguments.add(now.get("X_SWIFI_ADRES1").getAsString());
				arguments.add(now.get("X_SWIFI_ADRES2").getAsString());
				arguments.add(now.get("X_SWIFI_INSTL_FLOOR").getAsString());
				arguments.add(now.get("X_SWIFI_INSTL_TY").getAsString());
				arguments.add(now.get("X_SWIFI_INSTL_MBY").getAsString());
				arguments.add(now.get("X_SWIFI_SVC_SE").getAsString());
				arguments.add(now.get("X_SWIFI_CMCWR").getAsString());
				arguments.add(now.get("X_SWIFI_CNSTC_YEAR").getAsInt());
				arguments.add(now.get("X_SWIFI_INOUT_DOOR").getAsString());
				arguments.add(now.get("X_SWIFI_REMARS3").getAsString());
				
				//OpenAPI에서 위도 경도를 순서 바꿔서 보냄.. 혼동을 방지하기 위해 위치를 바꿔서 DB에 저장함.
				arguments.add(now.get("LNT").getAsDouble());
				arguments.add(now.get("LAT").getAsDouble());
				
				arguments.add(now.get("WORK_DTTM").getAsString());
				
				insertDatas.add(arguments);
				
			}
		}
		
		wifiRepository.insert(insertDatas);
		
		//속도 측정
		Long end = System.currentTimeMillis();
		
		return "총 " + max + "개의 와이파이 데이터를 가져왔습니다.  (" + String.format("%.1f", (end - start) / (double)1000) + "초 소요)";
//		System.out.println("elapsed time : " + (end - start));
	}
	
}
