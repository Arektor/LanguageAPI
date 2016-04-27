package fr.tenebrae.PlayerLanguage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.tenebrae.PlayerLanguage.events.PlayerChangeLanguageEvent;

public class LanguageAPI extends JavaPlugin {

	public static Map<Player, TPlayer> tplayers = new HashMap<Player, TPlayer>();
	public FileConfiguration config;
	public static String nmsver;
	public static LanguageAPI plugin;
	public boolean useBanners = false;
	public MySQL sql;
	public boolean useSQL = false;
	public SQLHelper sqlHelper;
	public Logger log;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.log = this.getLogger();
		config = this.getConfig();
		plugin = this;
		nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		if (nmsver.contains("1_8")) this.useBanners = true;
		if (config.getBoolean("sql.enabled")) {
	    	if (Bukkit.getPluginManager().getPlugin("SQLibrary") == null) return;
	    	if (!Bukkit.getPluginManager().getPlugin("SQLibrary").isEnabled()) return;
			useSQL = true;
			sql = new lib.PatPeter.SQLibrary.MySQL(Logger.getLogger("Minecraft"),
				"[VampireZ Lobby] ",
				config.getString("sql.host"),
				config.getInt("sql.port"),
				config.getString("sql.database"),
				config.getString("sql.account"),
				config.getString("sql.password"));
			sql.open();
			sqlHelper = new SQLHelper(this);
			if (!sql.isTable(config.getString("sql.table"))) {
				try {
					sql.query("CREATE TABLE `"+config.getString("sql.database")+"`.`"+config.getString("sql.table")+"`("
							+"`uuid` CHAR(36) NOT NULL,"
							+"`name` VARCHAR(16) NOT NULL,"
							+"`language` VARCHAR(12) NOT NULL,"
							+ "PRIMARY KEY (`uuid`));");
				} catch (SQLException e) {
					e.printStackTrace();
					this.log.severe("Could not create the table in the SQL database.");
				}
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
		return Utils.getTPlayer(p).getLanguage();
	}
	
	public static String getStringLanguage(Player p) {
		return Utils.getTPlayer(p).getLanguage().toString();
	}
	
	public static void setLanguage(Player p, Languages language) {
		PlayerChangeLanguageEvent e = new PlayerChangeLanguageEvent(p, getLanguage(p), language);
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.callEvent(e);
		TPlayer tp = Utils.getTPlayer(p);
		tp.setLanguage(language);
	}
}
