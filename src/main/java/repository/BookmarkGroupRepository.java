package repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import database.DataBaseManager;

public class BookmarkGroupRepository {

	public static final String TABLE = "bookmarkGroup";
	private DataBaseManager dataBaseManager;
	
	public BookmarkGroupRepository(DataBaseManager dataBaseManager) {
		this.dataBaseManager = dataBaseManager;
	}
	
//	//Test Code
//	public static void main(String[] args) {
//		
//		System.out.println(updateBookmarkGroup(20, "새로운걸로!", 1));
//		
//	}
	
	public ArrayList<ArrayList<String>> findById(int id, ArrayList<Integer> cols){
		
		//예약어 order와 칼럼명이 겹치므로 "ORDER"로 감싸야함.
		String sql = "select * from " + TABLE + " where ID=?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(id);
		
		ArrayList<ArrayList<String>> res = dataBaseManager.select(sql, args, cols);
		return res;
	}
	
	//모든 북마크그룹 가져옴.
	public ArrayList<ArrayList<String>> findAll() {
		
		ArrayList<Integer> cols = new ArrayList<Integer>();
		IntStream.rangeClosed(1, 5).forEach(cols::add);
		
		//예약어 order와 칼럼명이 겹치므로 "ORDER"로 감싸야함.
		String sql = "select * from " + TABLE + " order by \"ORDER\"";
		
		ArrayList<ArrayList<String>> res = dataBaseManager.select(sql, null, cols);
		return res;
	}
	
	public boolean addBookmarkGroup(String name, int order) {
		
		String sql = "insert into " + TABLE + "(NAME, \"ORDER\", REGISTEREDAT) values(?, ?, ?)";
		
		ArrayList<Object> args = new ArrayList<>();
		args.add(name);
		args.add(order);
		args.add(LocalDateTime.now().toString());
		
		return dataBaseManager.execute(sql, args);
	}
	
	public boolean updateBookmarkGroup(int id, String name, int order) {
		
		String sql = "update "+ TABLE + " set NAME=?, \"ORDER\"=?, EDITEDAT=? where ID=?";
		
		ArrayList<Object> args = new ArrayList<>();
		args.add(name);
		args.add(order);
		args.add(LocalDateTime.now().toString());
		args.add(id);
		
		return dataBaseManager.execute(sql, args);
	}
	
	public boolean deleteBoolmarkGroup(int id) {
		
		String sql = "delete from " + TABLE + " where ID=?";
		
		ArrayList<Object> args = new ArrayList<>();
		args.add(id);
		
		return dataBaseManager.execute(sql, args);
	}

	
}
