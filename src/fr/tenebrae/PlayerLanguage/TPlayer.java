package fr.tenebrae.PlayerLanguage;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

import org.bukkit.entity.Player;

public class TPlayer {
	
	private Player p;
	private Languages language;
	
	public TPlayer(Player p) {
		this.p = p;
		this.language = Languages.valueOf(LanguageAPI.plugin.config.getString("config.defaultLanguage"));
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
			Set<String> registeredUuids;
			try {
				registeredUuids = LanguageAPI.plugin.config.getConfigurationSection("players").getKeys(false);
			} catch (NullPointerException npe) {
				registeredUuids = Collections.emptySet();
			}
			if (registeredUuids == null) registeredUuids = Collections.emptySet();
			if (registeredUuids.isEmpty()) {
				LanguageAPI.plugin.config.set("players."+p.getUniqueId().toString(), this.language.toString().toUpperCase());
				LanguageAPI.plugin.saveConfig();
			}
			if (registeredUuids.contains(p.getUniqueId().toString())) {
				this.setLanguage(Languages.valueOf(LanguageAPI.plugin.config.getString("players."+p.getUniqueId().toString())));
			} else {
				LanguageAPI.plugin.config.set("players."+p.getUniqueId().toString(), this.language.toString().toUpperCase());
				LanguageAPI.plugin.saveConfig();
			}
		}
	}
	
	public Player getPlayer() {
		return p;
	}

	public Languages getLanguage() {
		return language;
	}

	public void setLanguage(final Languages nlanguage) {
		language = nlanguage;
		if (LanguageAPI.plugin.useSQL) {
			try {
				String[] columns = {"uuid","name","language"};
				Object[] values = {p.getUniqueId().toString(), p.getName(), nlanguage.toString()};
				LanguageAPI.plugin.sqlHelper.insertEntry(LanguageAPI.plugin.config.getString("sql.database"), LanguageAPI.plugin.config.getString("sql.table"), columns, values);
			} catch (SQLException e) {
				try {
					String[] columns = {"uuid","name","language"};
					Object[] values = {p.getUniqueId().toString(), p.getName(), nlanguage.toString()};
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
}
