package repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import database.DataBaseManager;

public class BookmarkRepository {

	private static final String TABLE = "bookmark";
	
	private DataBaseManager dataBaseManager;
	
	public BookmarkRepository(DataBaseManager dataBaseManager) {
		this.dataBaseManager = dataBaseManager;
	}
	
//	public static void main(String[] args) {
//		
//		System.out.println(countByGroup(17));
//		
//	}
	
	//북마크그룹에 속한, 북마크된 와이파이 개수를 반환함.
	public String countByGroup(int id) {
		
		ArrayList<Object> args = new ArrayList<>(Arrays.asList(id));
		ArrayList<Integer> cols = new ArrayList<Integer>(Arrays.asList(1));
		
		String sql = "select count(*) from " + TABLE + " where GROUPID=?";
		
		ArrayList<ArrayList<String>> res = dataBaseManager.select(sql, args, cols);
		
		return res.get(0).get(0);
	}
	
	//북마크그룹에 속한, 북마크된 와이파이를 반환함.
	public ArrayList<ArrayList<String>> findAllByGroup(int id) {
		
		ArrayList<Object> args = new ArrayList<>(Arrays.asList(id));
		ArrayList<Integer> cols = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
		
		String sql = "select * from " + TABLE + " where GROUPID=?";
		
		ArrayList<ArrayList<String>> res = dataBaseManager.select(sql, args, cols);
		
		return res;
	}
	
	public boolean addBookmark(int groupID, int wifiID) {
		
		String sql = "insert into " + TABLE + "(GROUPID, WIFIID, REGISTEREDAT) values(?, ?, ?)";
		
		ArrayList<Object> args = new ArrayList<>();
		args.add(groupID);
		args.add(wifiID);
		args.add(LocalDateTime.now().toString());
		
		return dataBaseManager.execute(sql, args);
	}
	
	public boolean deleteBookmark(int id) {
		
		String sql = "delete from " + TABLE + " where ID=?";
		ArrayList<Object> args = new ArrayList<>();
		args.add(id);
		
		return dataBaseManager.execute(sql, args);
	}
}
