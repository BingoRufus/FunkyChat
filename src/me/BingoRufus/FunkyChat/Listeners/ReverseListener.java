package me.BingoRufus.FunkyChat.Listeners;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.BingoRufus.FunkyChat.FunkyChat;
import me.BingoRufus.FunkyChat.Settings;

public class ReverseListener implements Listener {
	Pattern color = Pattern.compile("((§[1-9a-fnklor])((§[1-9a-fnklor])*))");
	String out = "";

	private FunkyChat m;
	public ReverseListener(FunkyChat m) {
		this.m = m;
	}

	@EventHandler
	public void onReverse(AsyncPlayerChatEvent e) {
		out = "";
		Player p = e.getPlayer();
		Settings setting = m.getSetting(p);
		if (!setting.reverse())
			return;
		String msg = e.getMessage();

		List<String> parts = Arrays.asList(msg.split("((?=" + color + "))"));

		Collections.reverse(parts);

		parts.forEach(item -> {
			out += ChatColor.getLastColors(msg.substring(0, msg.indexOf(item) + item.length()));
			out += new StringBuilder(ChatColor.stripColor(item)).reverse().toString();

		});

		e.setMessage(out);

	}

}
