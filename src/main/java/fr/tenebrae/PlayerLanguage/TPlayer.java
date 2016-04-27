package fr.tenebrae.PlayerLanguage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TPlayer {
	
	private Player p;
	private Languages language;
	
	public TPlayer(Player p) throws SQLException {
		this.p = p;
		this.language = Languages.valueOf(LanguageAPI.plugin.config.getString("config.defaultLanguage"));
		String locale = ((CraftPlayer) p).getHandle().locale;
		if (isLanguageSet()) return;
		
		Languages localeLang = null;
		if (LanguageAPI.plugin.config.contains("locale."+locale)) {
			localeLang = Languages.valueOf(LanguageAPI.plugin.config.getString("locale."+locale));
		}
		
		if (LanguageAPI.plugin.config.getString("config.defaultLanguage").equalsIgnoreCase("detectLanguage") && localeLang != null) {
			setLanguage(localeLang);
			return;
		}
		try {
			String iso = LanguageAPI.ipreader.city(((CraftPlayer)p ).getAddress().getAddress()).getCountry().getIsoCode();
			if (LanguageAPI.plugin.config.contains("iso."+iso))
				setLanguage(Languages.valueOf(LanguageAPI.plugin.config.getString("iso."+iso)));
			else if (localeLang != null)
				setLanguage(localeLang);
			else
				setLanguage(this.language);
		} catch (Exception e) {
			if (localeLang != null)
				setLanguage(localeLang);
			else 
				setLanguage(this.language);
		} 
		
	}
	
	public Player getPlayer() {
		return p;
	}

	public Languages getLanguage() {
		return language;
	}

	public void setLanguage(final Languages language) {
		this.language = language;
		if (LanguageAPI.plugin.useSQL) {
			try {
				String[] columns = {"uuid","name","language"};
				Object[] values = {p.getUniqueId().toString(), p.getName(), language.toString()};
				LanguageAPI.plugin.sqlHelper.insertEntry(LanguageAPI.plugin.config.getString("sql.database"), LanguageAPI.plugin.config.getString("sql.table"), columns, values);
			} catch (SQLException e) {
				try {
					String[] columns = {"uuid","name","language"};
					Object[] values = {p.getUniqueId().toString(), p.getName(), language.toString()};
					LanguageAPI.plugin.sqlHelper.updateEntry(LanguageAPI.plugin.config.getString("sql.database"), LanguageAPI.plugin.config.getString("sql.table"), "uuid", p.getUniqueId().toString(), columns, values);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			LanguageAPI.plugin.config.set("players."+p.getUniqueId().toString(), language.toString().toUpperCase());
			LanguageAPI.plugin.saveConfig();
		}
	}
	
	public Boolean isLanguageSet() throws SQLException {
		if (LanguageAPI.plugin.useSQL) {
			ResultSet rs = null;
			try {
				String columns = "uuid";
				String values = p.getUniqueId().toString();
				rs = LanguageAPI.plugin.sqlHelper.getSortedEntrys(LanguageAPI.plugin.config.getString("sql.database"), LanguageAPI.plugin.config.getString("sql.table"), columns, values);
				return rs.first();
			} catch(SQLException exc) {
				throw exc;
			} finally {
				if (rs != null) rs.close();
			}
		} else {
			Set<String> registeredUuids = null;
			try {
				registeredUuids = LanguageAPI.plugin.config.getConfigurationSection("players").getKeys(false);
			} catch (NullPointerException npe) {
				return false;
			}
			if (registeredUuids == null || registeredUuids.isEmpty()) return false;
			
			return registeredUuids.contains(p.getUniqueId().toString());
		}
	}
}
