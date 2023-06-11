package repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import database.DataBaseManager;

public class HistoryRepository {
	
	private static String TABLE = "history";
	
	private DataBaseManager dataBaseManager;
	
	public HistoryRepository(DataBaseManager dataBaseManager) {
		this.dataBaseManager = dataBaseManager;
	}
	
	//최근의 위치를 가져옴.
	public double[] findLatest() {
		
		double[] outLocation = new double[2];
		
		String sql = "select LAT, LNT from " + TABLE + " order by ID desc limit 1";
		ArrayList<Integer> cols = new ArrayList<>(Arrays.asList(1, 2));
		
		ArrayList<ArrayList<String>> result = dataBaseManager.select(sql, null, cols);
		
		if(result == null || result.size() == 0) {
			
			return null;
			
		}
		
		outLocation[0] = Double.valueOf(result.get(0).get(0));
		outLocation[1] = Double.valueOf(result.get(0).get(1));
		
		return outLocation;
		
	}
	
	public ArrayList<ArrayList<String>> findAll() {
		String sql = "select * from " + TABLE;
		ArrayList<Integer> cols = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
		return dataBaseManager.select(sql, null, cols);
	}
	
	public boolean addHistory(double x, double y) {
		
		String sql = "insert into " + TABLE + "(LAT, LNT, REGISTEREDAT) values(?, ?, ?)";
		
		ArrayList<Object> args = new ArrayList<>();
		args.add(x);
		args.add(y);
		args.add(LocalDateTime.now().toString());
		
		return dataBaseManager.execute(sql, args);
	}
	
	public boolean deleteHistory(int id) {
		
		String sql = "delete from " + TABLE + " where ID=?";
		
		ArrayList<Object> args = new ArrayList<>();
		args.add(id);
		
		return dataBaseManager.execute(sql, args);
	}
	
}
