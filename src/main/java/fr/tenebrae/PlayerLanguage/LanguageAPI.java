package fr.tenebrae.PlayerLanguage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.maxmind.geoip2.DatabaseReader;

import fr.tenebrae.PlayerLanguage.events.PlayerChangeLanguageEvent;

public class LanguageAPI extends JavaPlugin {

	public static Map<Player, TPlayer> tplayers = new HashMap<Player, TPlayer>();
	public FileConfiguration config;
	public static String nmsver;
	public static LanguageAPI plugin;
	public boolean useBanners = false;
	public static DbManager db;
	public File ipdb;
	public static DatabaseReader ipreader;
	public boolean useSQL = false;
	public SQLHelper sqlHelper;
	public Logger log;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.log = this.getLogger();
		config = this.getConfig();
		plugin = this;
		File dest = new File(plugin.getDataFolder()+"/GeoLite2-Country.mmdb");
		if (!dest.exists()) {
		     try {
			    InputStream link = (getClass().getResourceAsStream("/GeoLite2-Country.mmdb"));
				Files.copy(link, dest.getAbsoluteFile().toPath());
			} catch (IOException e) {
				e.printStackTrace();
				this.log.severe("Could not extract geoip data.");
			}
		}
		this.ipdb = new File(plugin.getDataFolder()+"/GeoLite2-Country.mmdb");
		try {
			LanguageAPI.ipreader = new DatabaseReader.Builder(this.ipdb).build();
		} catch (IOException e1) {
			e1.printStackTrace();
			this.log.severe("Could not load geoip data.");
		}
		nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		if (nmsver.contains("1_8") || nmsver.contains("1_9")) this.useBanners = true;
		if (config.getBoolean("sql.enabled")) {
			
			LanguageAPI.db = new DbManager();
			LanguageAPI.db.initmysql();
			useSQL = true;
			sqlHelper = new SQLHelper();
			
			try {
				if (!sqlHelper.containTable(config.getString("sql.database"), config.getString("sql.table"))) {
					Connection conn = null;
					Statement stmt = null;
					try {
						conn = LanguageAPI.db.getmysql();
						stmt = conn.createStatement();
						stmt.executeUpdate("CREATE TABLE `"+config.getString("sql.database")+"`.`"+config.getString("sql.table")+"`("
								+"`uuid` CHAR(36) NOT NULL,"
								+"`name` VARCHAR(16) NOT NULL,"
								+"`language` VARCHAR(12) NOT NULL,"
								+ "PRIMARY KEY (`uuid`));");
					} catch(Exception e) {
						
					} finally {
						if (stmt != null)
							stmt.close();
						if (conn != null)
							conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				this.log.severe("Could not create the table in the SQL database.");
			}
		} else {
			this.log.warning("Database usage disabled. Languages will be stored in the config file.");
			//this.log.warning("Every information in this file will be sent to the database if you activate it.");
		}
		this.getServer().getPluginManager().registerEvents(new Listeners(this), this);
		if (config.getBoolean("config.enableNameTag")) this.getServer().getPluginManager().registerEvents(new NametagListener(this), this);
	    if (config.getBoolean("config.enableCommand")) getCommand("languages").setExecutor(new Commands(this));
	}
	
	public static Languages getLanguage(Player p) {
		try {
			return Utils.getTPlayer(p).getLanguage();
		} catch (Exception e) {
			e.printStackTrace();
			return Languages.valueOf(plugin.config.getString("config.defaultLanguage"));
		}
	}
	
	public static String getStringLanguage(Player p) {
		try {
			return Utils.getTPlayer(p).getLanguage().toString();
		} catch(Exception e) {
			e.printStackTrace();
			return plugin.config.getString("config.defaultLanguage");
		}
	}
	
	public static void setLanguage(Player p, Languages language) {
		try {
			PlayerChangeLanguageEvent e = new PlayerChangeLanguageEvent(p, getLanguage(p), language);
			PluginManager pm = Bukkit.getServer().getPluginManager();
			pm.callEvent(e);
			TPlayer tp = Utils.getTPlayer(p);
			tp.setLanguage(language);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
