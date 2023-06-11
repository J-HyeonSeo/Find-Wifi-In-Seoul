package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import database.DataBaseManager;
import util.Distance;

public class WifiRepository {
	
	private static String table = "wifi";
	
	private DataBaseManager dataBaseManager;
	
	public WifiRepository(DataBaseManager dataBaseManager) {
		this.dataBaseManager = dataBaseManager;
	}
	
//	public static void main(String[] args) {
//		
//		//Test Code
//		
////		ArrayList<ArrayList<String>> res = findById(28370, new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 8)));
////		
////		System.out.println(res);
//		
//		System.out.println(System.getProperty("user.dir"));
//		
//	}
	
	//wifi data insert
	public void insert(ArrayList<ArrayList<Object>> args) {
		String columns = "MGR_NO, "
				+ "WRDOFC, "
				+ "MAIN_NM, "
				+ "ADRES1, "
				+ "ADRES2, "
				+ "INSTL_FLOOR, "
				+ "INSTL_TY, "
				+ "INSTL_MBY, "
				+ "SVC_SE, "
				+ "CMCWR, "
				+ "CNSTC_YEAR, "
				+ "INOUT_DOOR, "
				+ "REMARS3, "
				+ "LAT, "
				+ "LNT, "
				+ "WORK_DTTM";
		
		String sql = "insert into "+ table +" (" + columns + ") "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		dataBaseManager.batchExcute(sql, args);
		
	}
	
	//wifi id를 기준으로 데이터 출력해보자.
	public ArrayList<ArrayList<String>> findById(int id, ArrayList<Integer> cols){
		
		String sql = "select * from " + table + " where ID=?";
		ArrayList<Object> args = new ArrayList<>(Arrays.asList(id));
		return dataBaseManager.select(sql, args, cols);
		
	}
	
	//거리를 기준으로 조회하기.
	public ArrayList<ArrayList<String>> findByDistance(int limit, double x1, double y1) {
		
		//DB에서 모든 데이터 가져오기.
		String sql = "select * from " + table;
		
		//15번 위도, 16번 경도
		ArrayList<Integer> cols = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 8, 15, 16));
		
		
		ArrayList<ArrayList<String>> resArrayList = dataBaseManager.select(sql, null, cols);
		
		//레코드 별로 순회하면서, 거리를 계산
		for(ArrayList<String> record : resArrayList) {
			
			double x2 = Double.parseDouble(record.get(7));
			double y2 = Double.parseDouble(record.get(8));
			
			double dist = Distance.haversine(x1, y1, x2, y2);
			
			record.add(String.valueOf(dist));
			
		}
		
		resArrayList.sort(Comparator.comparingDouble(a -> Double.parseDouble(a.get(9))));
		
		limit = Math.min(limit, resArrayList.size());
		
		ArrayList<ArrayList<String>> res = new ArrayList<>();
		
		for(int i = 0; i < limit; i++) {
			
			res.add(resArrayList.get(i));
			
		}
		
		return res;
		
	}
	
	//와이파이데이터 새로 가져올시, 테이블 DELETE
	public void deleteTable() {
		String sql = "delete from " + table;
		dataBaseManager.execute(sql, null);
	}
}
