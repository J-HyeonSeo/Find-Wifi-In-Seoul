package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import api.ApiLoader;
import database.DataBaseManager;
import database.SqliteManager;
import model.Wifi;
import repository.WifiRepository;

public class OpenAPILoaderService {
	
	public static String API_KEY;
	private WifiRepository wifiRepository;
	private Gson gson;
	private ApiLoader apiLoader;
	
	public OpenAPILoaderService(WifiRepository wifiRepository,
								Gson gson,
								ApiLoader apiLoader) {
		this.wifiRepository = wifiRepository;
		this.gson = gson;
		this.apiLoader = apiLoader;
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
	
//    public static void main(String[] args) throws InterruptedException {
//    	DataBaseManager dataBaseManager = new SqliteManager();
//    	WifiRepository wifiRepository = new WifiRepository(dataBaseManager);
//    	ApiLoader apiLoader = new ApiLoader();
//    	Gson gson = new Gson();
//		OpenAPILoaderService openAPILoaderService = new OpenAPILoaderService(wifiRepository, gson, apiLoader);
//		System.out.println(openAPILoaderService.loadFromApi());
//	}
    
	public String loadFromApi() throws InterruptedException {
		
		//속도 측정.
		Long start = System.currentTimeMillis();
		
		//일단 api를 한 번 호출하여, 최대 데이터 갯수를 읽어옴.
		
		String initString = apiLoader.apiLoad("sample", 1, 1);
		
	    Wifi.WifiInfoResponse response = gson.fromJson(initString, Wifi.WifiInfoResponse.class);
		
		int max = response.getTbPublicWifiInfo().getListTotalCount();
		
		//OpenAPI를 통해 와이파이 정보를 새로 가져오면, 테이블 데이터 날려버림.
		wifiRepository.deleteTable();
		
		ArrayList<ArrayList<Object>> insertDatas = new ArrayList<>();
		
		//1000개씩 페이징하면서, 모든 와이파이 리스트를 가져옴.
		for(int i = 1; i < max; i += 1000) {
			String page = apiLoader.apiLoad(API_KEY, i, i + 999);
			
			List<Wifi.WifiInfo> wifisInfos = gson.fromJson(page, Wifi.WifiInfoResponse.class).getTbPublicWifiInfo().getRow();
			
			for(int j = 0; j < wifisInfos.size(); j++) {
				
				
				ArrayList<Object> arguments = new ArrayList<>();
				
				arguments.add(wifisInfos.get(j).getManagerNo());
				arguments.add(wifisInfos.get(j).getWrdOfc());
				arguments.add(wifisInfos.get(j).getMainName());
				arguments.add(wifisInfos.get(j).getAddress1());
				arguments.add(wifisInfos.get(j).getAddress2());
				arguments.add(wifisInfos.get(j).getInstallationFloor());
				arguments.add(wifisInfos.get(j).getInstallationType());
				arguments.add(wifisInfos.get(j).getInstallationMby());
				arguments.add(wifisInfos.get(j).getServiceSe());
				arguments.add(wifisInfos.get(j).getCmcwr());
				
				arguments.add(Integer.valueOf(wifisInfos.get(j).getCnstcYear()));
				arguments.add(wifisInfos.get(j).getInOutDoor());
				arguments.add(wifisInfos.get(j).getRemars3());
				
				//OpenAPI에서 위도 경도를 순서 바꿔서 보냄.. 혼동을 방지하기 위해 위치를 바꿔서 DB에 저장함.
				arguments.add(Double.valueOf(wifisInfos.get(j).getLongitude()));
				arguments.add(Double.valueOf(wifisInfos.get(j).getLatitude()));
				
				arguments.add(wifisInfos.get(j).getWorkDateTime());
				
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
