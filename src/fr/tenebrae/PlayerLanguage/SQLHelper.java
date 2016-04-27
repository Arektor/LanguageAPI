package fr.tenebrae.PlayerLanguage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHelper {
	
	private LanguageAPI plugin;
	
	public SQLHelper(LanguageAPI plugin) {
		this.plugin = plugin;
	}
	
	public void insertEntry(String dbname, String tableName, String[] columns, Object[] values) throws SQLException {
		if (!plugin.sql.isOpen()) plugin.sql.open();
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
		plugin.sql.query(request);
	}
	
	@Deprecated
	public ResultSet getSortedEntrys(String dbname, String tableName, String column, String value, int limit) throws SQLException {
		if (!plugin.sql.isOpen()) plugin.sql.open();
		String request = "SELECT *"+(limit == 0 ? " " : "("+limit+") ")+"FROM `"+dbname+"`.`"+tableName+"` WHERE `"+column+"` = '"+value+"';";
		return plugin.sql.query(request);
	}
	
	public ResultSet getSortedEntrys(String dbname, String tableName, String column, String value) throws SQLException {
		if (!plugin.sql.isOpen()) plugin.sql.open();
		String request = "SELECT * FROM `"+dbname+"`.`"+tableName+"` WHERE `"+column+"` = '"+value+"';";
		return plugin.sql.query(request);
	}
	
	public ResultSet getAllEntrys(String dbname, String tableName) throws SQLException {
		if (!plugin.sql.isOpen()) plugin.sql.open();
		String request = "SELECT * FROM `"+dbname+"`.`"+tableName+"`";
		return plugin.sql.query(request);
	}
	
	public void clearEntries(String dbname, String tableName) throws SQLException {
		if (!plugin.sql.isOpen()) plugin.sql.open();
		String request = "DELETE * FROM `"+dbname+"`.`"+tableName+"`";
		plugin.sql.query(request);
	}
	
	public void updateEntry(String dbname, String tableName, String columnToSeek, String valueToSeek, String[] columnsToUpdate, Object[] valuesToUpdate) throws SQLException {
		if (!plugin.sql.isOpen()) plugin.sql.open();
		String request = "UPDATE `"+dbname+"`.`"+tableName+"` SET ";
		int marker = 0;
		for (String s : columnsToUpdate) {
			marker++;
			request = request+"`"+s+"` = '"+valuesToUpdate[marker-1]+"'"+(marker == columnsToUpdate.length ? " WHERE " : " , ");
		}
		request = request+"`"+columnToSeek+"` = '"+valueToSeek+"'";
		plugin.sql.query(request);
	}
}