package fr.tenebrae.PlayerLanguage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

public class SQLHelper {
	
	public SQLHelper() {}
	
	public void insertEntry(String dbname, String tableName, String[] columns, Object[] values) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = LanguageAPI.db.getmysql();
			stmt = conn.createStatement();
			String request = "INSERT INTO `"+dbname+"`.`"+tableName+"` (";
			int marker = 0;
			for (String s : columns) {
				marker++;
				request = request+s+(marker == columns.length ? ") " : ", ");
			}
			request = request+"VALUES (";
			marker = 0;
			for (Object o : values) {
				marker++;
				request = request+"'"+String.valueOf(o)+"'"+(marker == values.length ? ") " : ", ");
			}
			request = request+";";
			
			stmt.executeUpdate(request);
		} catch(SQLException e) {
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	
	public Boolean containTable(String dbname, String tableName) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = LanguageAPI.db.getmysql();
			stmt = conn.createStatement();
			String request = "SHOW TABLES LIKE '"+tableName+"'";
			
			rs = stmt.executeQuery(request);
			return rs.first();
		} catch(SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				stmt.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public Boolean containEntry(String dbname, String tableName, String condition, String conditionVal) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = LanguageAPI.db.getmysql();
			stmt = conn.createStatement();
			String request = "SELECT * FROM "+dbname+"."+tableName+" WHERE "+condition+"= '"+conditionVal+"'";
			
			rs = stmt.executeQuery(request);
			return rs.first();
		} catch(SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				stmt.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public String selectEntry(String dbname, String tableName, String column, String condition, String conditionVal) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = LanguageAPI.db.getmysql();
			stmt = conn.createStatement();
			String request = "SELECT * FROM "+dbname+"."+tableName+" WHERE "+condition+"= '"+conditionVal+"'";
			rs = stmt.executeQuery(request);
			if (rs.first()) {
				return rs.getString(column);
			} else return null;
		} catch(SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				stmt.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public void clearEntries(String dbname, String tableName) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = LanguageAPI.db.getmysql();
			stmt = conn.createStatement();
			String request = "DELETE * FROM `"+dbname+"`.`"+tableName+"`";
			
			stmt.executeUpdate(request);
		} catch(SQLException e) {
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public void updateEntry(String dbname, String tableName, String columnToSeek, String valueToSeek, String[] columnsToUpdate, Object[] valuesToUpdate) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = LanguageAPI.db.getmysql();
			stmt = conn.createStatement();
			String request = "UPDATE `"+dbname+"`.`"+tableName+"` SET ";
			int marker = 0;
			for (String s : columnsToUpdate) {
				marker++;
				request = request+"`"+s+"` = '"+valuesToUpdate[marker-1]+"'"+(marker == columnsToUpdate.length ? " WHERE " : " , ");
			}
			request = request+"`"+columnToSeek+"` = '"+valueToSeek+"'";
			
			stmt.executeUpdate(request);
		} catch(SQLException e) {
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}