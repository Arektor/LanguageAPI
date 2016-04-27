package fr.tenebrae.PlayerLanguage;

import org.bukkit.entity.Player;

public class Utils {
	
	public static TPlayer getTPlayer(Player p) {
		TPlayer t;
		try {
			if (LanguageAPI.tplayers.containsKey(p)) {
				t = LanguageAPI.tplayers.get(p);
			} else {
				t = new TPlayer(p);
				LanguageAPI.tplayers.put(p, t);
			}
		} catch (NullPointerException npe) {
			t = new TPlayer(p);
			LanguageAPI.tplayers.put(p, t);
		}
		return t;
	}
}
