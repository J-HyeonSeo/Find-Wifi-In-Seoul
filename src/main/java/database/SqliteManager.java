package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.sqlite.SQLiteConfig;

import service.OpenAPILoaderService;

public class SqliteManager implements DataBaseManager{
	
	//Sqlite 데이터베이스와 직접적인 접근을 수행하는 Manager임.
	
	private static String URL;
	private static int batchSize = 500;
	
	//application.properties 의 db.source에 적혀있는 값을 가져와 변수에 할당함.
    static {
        Properties properties = new Properties();
        try (InputStream inputStream = OpenAPILoaderService.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
            URL = properties.getProperty("db.source");
            System.out.println("nice" + URL);
        } catch (IOException e) {
            // 파일 로드 오류 처리
        	URL = null;
        }
    }
	
	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet rs = null;
	
	@Override
	public ArrayList<ArrayList<String>> select(String sql, ArrayList<Object> args, ArrayList<Integer> cols) {
		
		ArrayList<ArrayList<String>> res = new ArrayList<>();
		
		getConnection();
		
		try {
			statement = connection.prepareStatement(sql);
			
			setStatement(args);
			
			rs = statement.executeQuery();

			while(rs.next()) {
				
				ArrayList<String> now = new ArrayList<>();
				
				for(int i : cols) {
					
					now.add(rs.getString(i));
					
				}
				
				res.add(now);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			closeConnection();
			
		}
		
		return res;
		
	}
	
	@Override
	public boolean execute(String sql, ArrayList<Object> args) {
		boolean result = false;
		
		getConnection();
		
		try {
			statement = connection.prepareStatement(sql);
			setStatement(args);
			statement.executeUpdate();
			result = true;
			
		} catch (SQLException e) {
			
			System.out.println(e);
			
		}finally {
			
			closeConnection();
			return result;
		}
		
	}
	
	@Override
	public void batchExcute(String sql, ArrayList<ArrayList<Object>> args) {
		
		getConnection();
		
		try {
			
			connection.setAutoCommit(false);
		    statement = connection.prepareStatement(sql);

		    for (int i = 0; i < args.size(); i++) {
		        setStatement(args.get(i));
		        statement.addBatch();

		        // 배치 크기가 일정 수치에 도달하면 실행
		        if ((i + 1) % batchSize == 0) {
		            statement.executeBatch();
		        }
		    }

		    // 나머지 배치 실행
		    statement.executeBatch();

		    connection.setAutoCommit(true);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			closeConnection();
			
		}
		
	}
	
	private void setStatement(ArrayList<Object> args) throws SQLException {
		
		if(args == null || args.size() == 0) {
			return;
		}
		
		for(int i = 0; i < args.size(); i++) {
			
			Object object = args.get(i);
			
			if(object instanceof String) {
				statement.setString(i + 1, (String)object);
			}else if(object instanceof Integer) {
				statement.setInt(i + 1, (Integer)object);
			}else if(object instanceof Double) {
				statement.setDouble(i + 1, (Double)object);
			}
		}
		
	}
	
	private Connection getConnection() {
		if(connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				
				//foreignkey가 기본적으로 적용안됨. 따로 config설정(현업에서는 잘 사용안한다고 함.. 이런..)
		        SQLiteConfig config = new SQLiteConfig();  
		        config.enforceForeignKeys(true);
		        
				connection = DriverManager.getConnection("jdbc:sqlite:" + URL, config.toProperties());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return connection;
	}
	
	private void closeConnection(){
		
		try {
			
			if(rs != null && !rs.isClosed()) {
				
				rs.close();
				
			}
			
			if(statement != null && !statement.isClosed()) {
				
				statement.close();
				
			}
			
			if(connection != null && !connection.isClosed()) {
	
				connection.close();
	
			}
		}catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		connection = null;
		rs = null;
		statement = null;
	}
	

}
