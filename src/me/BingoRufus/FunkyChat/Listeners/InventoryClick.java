package me.BingoRufus.FunkyChat.Listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.BingoRufus.FunkyChat.FunkyChat;
import me.BingoRufus.FunkyChat.Settings;
import me.BingoRufus.FunkyChat.utils.ItemStackJson;
import net.md_5.bungee.api.ChatColor;

public class InventoryClick implements Listener {
	FunkyChat m;

	public InventoryClick(FunkyChat m) {
		this.m = m;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!m.settingInventory.values().contains(e.getInventory()))
			return;
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		Player edited = (Player) e.getInventory().getHolder();
		String perm = "funkychat." + (edited.equals(p) ? "self." : "other.");
		if (e.getSlot() == 20) {
			perm += "reverse";
			if (!p.hasPermission(perm)) {
				p.sendMessage(ChatColor.RED + "You do not have permission to do this ({p})".replace("{p}", perm));
				return;
			}
			Settings setting = m.getSetting(edited);
			e.getInventory().setItem(20,
					setting.reverse() ? new ItemStackJson().disabled() : new ItemStackJson().enabled());
			setting.setReverse(!setting.reverse());
			p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);



		}
		if (e.getSlot() == 24) {
			perm += "upsidedown";
			if (!p.hasPermission(perm)) {
				p.sendMessage(ChatColor.RED + "You do not have permission to do this ({p})".replace("{p}", perm));
				return;
			}
			Settings setting = m.getSetting(edited);
			e.getInventory().setItem(24,
					setting.upsidedown() ? new ItemStackJson().disabled() : new ItemStackJson().enabled());
			setting.setUpsidedown(!setting.upsidedown());
			p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);

		}

	}


}
