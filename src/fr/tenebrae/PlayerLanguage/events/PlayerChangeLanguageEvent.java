package fr.tenebrae.PlayerLanguage.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.tenebrae.PlayerLanguage.Languages;

public class PlayerChangeLanguageEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Languages oldLanguage;
	private Languages newLanguage;
	
	public PlayerChangeLanguageEvent(Player p, Languages oldLanguage, Languages newLanguage) {
		this.player = p;
		this.oldLanguage = oldLanguage;
		this.newLanguage = newLanguage;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Languages getOldLanguage() {
		return oldLanguage;
	}
	
	public Languages getNewLanguage() {
		return newLanguage;
	}
	
	public Player getPlayer() {
		return player;
	}
}
