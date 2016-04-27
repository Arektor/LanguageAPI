package fr.tenebrae.PlayerLanguage;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class NametagListener implements Listener {
	
	private LanguageAPI plugin;
	
	public NametagListener(LanguageAPI plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClickNametag(PlayerInteractEvent evt) {
		if (evt.getPlayer().getItemInHand() == null) return;
		if (evt.getPlayer().getItemInHand().getType() != Material.NAME_TAG) return;
		if (!evt.getPlayer().getItemInHand().hasItemMeta()) return;
		if (!evt.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) return;
		if (evt.getPlayer().getItemInHand().getItemMeta().getDisplayName().startsWith("§o")) return;
		
		Player p = evt.getPlayer();
		Inventory inv = Bukkit.createInventory(p, 2*9, plugin.config.getString("messages.languageSelection."+LanguageAPI.getLanguage(p).toString().toLowerCase()));
		
		ItemStack fr;
		ItemStack en;
		ItemStack nl;
		ItemStack de;
		ItemStack sp;
		ItemStack br;
		ItemStack it;
		ItemStack pl;
		ItemStack ar;
		ItemStack ch;
		
		if (plugin.useBanners) {
		
			fr = new ItemStack(Material.BANNER);
			BannerMeta frMeta = (BannerMeta) fr.getItemMeta();
			frMeta.setBaseColor(DyeColor.WHITE);
			frMeta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_LEFT));
			frMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_RIGHT));
			frMeta.setDisplayName("§9Fra§fnç§cais");
			fr.setItemMeta(frMeta);
			
			en = new ItemStack(Material.BANNER);
			BannerMeta enMeta = (BannerMeta) en.getItemMeta();
			enMeta.setBaseColor(DyeColor.BLUE);
			enMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
			enMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
			enMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.DIAGONAL_LEFT));
			enMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.DIAGONAL_RIGHT));
			enMeta.addPattern(new Pattern(DyeColor.RED, PatternType.CROSS));
			enMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS));
			enMeta.setDisplayName("§fEn§cgl§9ish");
			en.setItemMeta(enMeta);
			
			nl = new ItemStack(Material.BANNER);
			BannerMeta nlMeta = (BannerMeta) nl.getItemMeta();
			nlMeta.setBaseColor(DyeColor.WHITE);
			nlMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
			nlMeta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
			nlMeta.setDisplayName("§cNede§frlan§9der");
			nl.setItemMeta(nlMeta);
			
			de = new ItemStack(Material.BANNER);
			BannerMeta deMeta = (BannerMeta) de.getItemMeta();
			deMeta.setBaseColor(DyeColor.RED);
			deMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
			deMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM));
			deMeta.setDisplayName("§0De§cuts§ech");
			de.setItemMeta(deMeta);
	
			sp = new ItemStack(Material.BANNER);
			BannerMeta spMeta = (BannerMeta) sp.getItemMeta();
			spMeta.setBaseColor(DyeColor.YELLOW);
			spMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
			spMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
			spMeta.setDisplayName("§cEs§epañ§col");
			sp.setItemMeta(spMeta);
			
			br = new ItemStack(Material.BANNER);
			BannerMeta brMeta = (BannerMeta) br.getItemMeta();
			brMeta.setBaseColor(DyeColor.RED);
			brMeta.addPattern(new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP));
			brMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.FLOWER));
			brMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.CIRCLE_MIDDLE));
			brMeta.setDisplayName("§aPor§etug§cuês");
			br.setItemMeta(brMeta);
			
			it = new ItemStack(Material.BANNER);
			BannerMeta itMeta = (BannerMeta) it.getItemMeta();
			itMeta.setBaseColor(DyeColor.WHITE);
			itMeta.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_LEFT));
			itMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_RIGHT));
			itMeta.setDisplayName("§aIta§flia§cno");
			it.setItemMeta(itMeta);
			
			pl = new ItemStack(Material.BANNER);
			BannerMeta plMeta = (BannerMeta) pl.getItemMeta();
			plMeta.setBaseColor(DyeColor.WHITE);
			plMeta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
			plMeta.setDisplayName("§fPol§cski");
			pl.setItemMeta(plMeta);
			
			ar = new ItemStack(Material.BANNER);
			BannerMeta arMeta = (BannerMeta) ar.getItemMeta();
			arMeta.setBaseColor(DyeColor.GREEN);
			arMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_LEFT));
			arMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT));
			arMeta.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_TOP));
			arMeta.setDisplayName("§0Ar§fabi§2sh");
			ar.setItemMeta(arMeta);
			
			ch = new ItemStack(Material.BANNER);
			BannerMeta chMeta = (BannerMeta) ch.getItemMeta();
			chMeta.setBaseColor(DyeColor.RED);
			chMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.SQUARE_TOP_RIGHT));
			chMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_MIDDLE));
			chMeta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL_MIRROR));
			chMeta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL));
			chMeta.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_BOTTOM));
			chMeta.addPattern(new Pattern(DyeColor.RED, PatternType.BORDER));
			chMeta.setDisplayName("§cChin§eese");
			ch.setItemMeta(chMeta);
		
		} else {
			fr = new ItemStack(Material.NAME_TAG);
			ItemMeta frMeta = fr.getItemMeta();
			frMeta.setDisplayName("§9Fra§fnç§cais");
			fr.setItemMeta(frMeta);
			if (LanguageAPI.getLanguage(p) == Languages.FRENCH) fr.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			en = new ItemStack(Material.NAME_TAG);
			ItemMeta enMeta = en.getItemMeta();
			enMeta.setDisplayName("§fEn§cgl§9ish");
			en.setItemMeta(enMeta);
			if (LanguageAPI.getLanguage(p) == Languages.ENGLISH) en.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			nl = new ItemStack(Material.NAME_TAG);
			ItemMeta nlMeta = nl.getItemMeta();
			nlMeta.setDisplayName("§cNede§frlan§9der");
			nl.setItemMeta(nlMeta);
			if (LanguageAPI.getLanguage(p) == Languages.DUTCH) nl.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			de = new ItemStack(Material.NAME_TAG);
			ItemMeta deMeta = de.getItemMeta();
			deMeta.setDisplayName("§0De§cuts§ech");
			de.setItemMeta(deMeta);
			if (LanguageAPI.getLanguage(p) == Languages.DEUTSCH) de.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			sp = new ItemStack(Material.NAME_TAG);
			ItemMeta spMeta = sp.getItemMeta();
			spMeta.setDisplayName("§cEs§epañ§col");
			sp.setItemMeta(spMeta);
			if (LanguageAPI.getLanguage(p) == Languages.SPANISH) sp.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			br = new ItemStack(Material.NAME_TAG);
			ItemMeta brMeta = br.getItemMeta();
			brMeta.setDisplayName("§aPor§etug§cuês");
			br.setItemMeta(brMeta);
			if (LanguageAPI.getLanguage(p) == Languages.PORTOUGUESE) br.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			it = new ItemStack(Material.NAME_TAG);
			ItemMeta itMeta = it.getItemMeta();
			itMeta.setDisplayName("§aIta§flia§cno");
			it.setItemMeta(itMeta);
			if (LanguageAPI.getLanguage(p) == Languages.ITALIAN) it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			pl = new ItemStack(Material.NAME_TAG);
			ItemMeta plMeta = it.getItemMeta();
			plMeta.setDisplayName("§fPol§cski");
			pl.setItemMeta(plMeta);
			if (LanguageAPI.getLanguage(p) == Languages.POLISH) pl.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			ar = new ItemStack(Material.NAME_TAG);
			ItemMeta arMeta = ar.getItemMeta();
			arMeta.setDisplayName("§0Ar§fabi§2sh");
			ar.setItemMeta(arMeta);
			if (LanguageAPI.getLanguage(p) == Languages.ARABISH) ar.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

			ch = new ItemStack(Material.NAME_TAG);
			ItemMeta chMeta = ch.getItemMeta();
			chMeta.setDisplayName("§cChin§eese");
			ch.setItemMeta(chMeta);
			if (LanguageAPI.getLanguage(p) == Languages.CHINESE) ch.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
		}
		
		inv.setItem(2, fr);
		inv.setItem(3, en);
		inv.setItem(4, nl);
		inv.setItem(5, de);
		inv.setItem(6, sp);
		inv.setItem(11, br);
		inv.setItem(12, it);
		inv.setItem(13, pl);
		inv.setItem(14, ar);
		inv.setItem(15, ch);
		
		p.openInventory(inv);
		evt.setCancelled(true);
	}
}
