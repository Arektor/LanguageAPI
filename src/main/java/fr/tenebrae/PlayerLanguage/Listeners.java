package fr.tenebrae.PlayerLanguage;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
		
	private LanguageAPI plugin;
	public Listeners(LanguageAPI plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt) {
		try {
			Utils.getTPlayer(evt.getPlayer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) {
		try {
			if (evt.getInventory().getName() == null) return;
			if (evt.getInventory().getName() != plugin.config.getString("messages.languageSelection."+LanguageAPI.getLanguage((Player)evt.getWhoClicked()).toString().toLowerCase())) return;
			int slotId = evt.getRawSlot();
			Player p = (Player) evt.getWhoClicked();
		
			switch (slotId) {
			case 2:
				LanguageAPI.setLanguage(p, Languages.FRENCH);
				p.sendMessage(plugin.config.getString("messages.languageChange.french").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 3:
				LanguageAPI.setLanguage(p, Languages.ENGLISH);
				p.sendMessage(plugin.config.getString("messages.languageChange.english").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 4:
				LanguageAPI.setLanguage(p, Languages.DUTCH);
				p.sendMessage(plugin.config.getString("messages.languageChange.dutch").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 5:
				LanguageAPI.setLanguage(p, Languages.DEUTSCH);
				p.sendMessage(plugin.config.getString("messages.languageChange.deutsch").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 6:
				LanguageAPI.setLanguage(p, Languages.SPANISH);
				p.sendMessage(plugin.config.getString("messages.languageChange.spanish").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 11:
				LanguageAPI.setLanguage(p, Languages.PORTOUGUESE);
				p.sendMessage(plugin.config.getString("messages.languageChange.portouguese").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 12:
				LanguageAPI.setLanguage(p, Languages.ITALIAN);
				p.sendMessage(plugin.config.getString("messages.languageChange.italian").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 13:
				LanguageAPI.setLanguage(p, Languages.POLISH);
				p.sendMessage(plugin.config.getString("messages.languageChange.polish").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 14:
				LanguageAPI.setLanguage(p, Languages.ARABISH);
				p.sendMessage(plugin.config.getString("messages.languageChange.arabish").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			case 15:
				LanguageAPI.setLanguage(p, Languages.CHINESE);
				p.sendMessage(plugin.config.getString("messages.languageChange.chinese").replace("&", "�"));
				evt.setCancelled(true);
				p.closeInventory();
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
