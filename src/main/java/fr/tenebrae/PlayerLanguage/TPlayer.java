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
		if (isLanguageSet())  {
			Languages lang = getStoredLanguage();
			if (lang != null) {
				this.language = lang;
				return;
			}
		}
		
		Languages localeLang = null;
		if (LanguageAPI.plugin.config.contains("locale."+locale)) {
			localeLang = Languages.valueOf(LanguageAPI.plugin.config.getString("locale."+locale));
		}
		
		if (LanguageAPI.plugin.config.getString("config.detectLanguage").equalsIgnoreCase("LOCALE") && localeLang != null) {
			setLanguage(localeLang);
			return;
		}
		try {
			String iso = LanguageAPI.ipreader.country(((CraftPlayer)p ).getAddress().getAddress()).getCountry().getIsoCode();
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
	
	private Languages getStoredLanguage() throws SQLException {
		if (LanguageAPI.plugin.useSQL) 
			return Languages.valueOf(LanguageAPI.plugin.sqlHelper.selectEntry(LanguageAPI.plugin.config.getString("sql.database"), LanguageAPI.plugin.config.getString("sql.table"), "language", "uuid", p.getUniqueId().toString()));
		else {
			Set<String> registeredUuids = null;
			try {
				registeredUuids = LanguageAPI.plugin.config.getConfigurationSection("players").getKeys(false);
			} catch (NullPointerException npe) {
				return null;
			}
			if (registeredUuids == null || registeredUuids.isEmpty()) return null;
			
			if (!registeredUuids.contains(p.getUniqueId().toString())) return null;
			
			return Languages.valueOf(LanguageAPI.plugin.config.getString("players."+p.getUniqueId().toString()));
		}
			
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
				String condition = "uuid";
				String value = p.getUniqueId().toString();
				return LanguageAPI.plugin.sqlHelper.containEntry(LanguageAPI.plugin.config.getString("sql.database"), LanguageAPI.plugin.config.getString("sql.table"), condition, value);
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
