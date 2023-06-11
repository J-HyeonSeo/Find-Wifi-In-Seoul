package database;

import java.util.ArrayList;

public interface DataBaseManager {

	public ArrayList<ArrayList<String>> select(String sql, ArrayList<Object> args, ArrayList<Integer> cols);
	
	public boolean execute(String sql, ArrayList<Object> args);
	
	public void batchExcute(String sql, ArrayList<ArrayList<Object>> args);
	
}
