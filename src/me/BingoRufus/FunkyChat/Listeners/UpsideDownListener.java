package me.BingoRufus.FunkyChat.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.BingoRufus.FunkyChat.FunkyChat;
import me.BingoRufus.FunkyChat.Settings;

public class UpsideDownListener implements Listener {
	FunkyChat m;
	public UpsideDownListener(FunkyChat m) {
		this.m = m;
	}

	@EventHandler
	public void onUpsideDown(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		Settings setting = m.getSetting(p);
		if (!setting.upsidedown())
			return;
		String msg = e.getMessage();
		String normal = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'";
		String split = "ɐqɔpǝɟbɥıظʞןɯuodbɹsʇnʌʍxʎz‾'؛˙¿¡/\\,";
		normal += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		split += "∀qϽᗡƎℲƃHIſʞ˥WNOԀὉᴚS⊥∩ΛMXʎZ";
		normal += "0123456789";
		split += "0ƖᄅƐㄣϛ9ㄥ86";

		String newstr = "";
		char letter;
		for (int i = 0; i < msg.length(); i++) {
			if (i > 0 && msg.charAt(i - 1) == '§') {

				newstr += msg.charAt(i);
				continue;
			}
			letter = msg.charAt(i);

			int a = normal.indexOf(letter);
			newstr += (a != -1) ? split.charAt(a) : letter;
		}
		e.setMessage(newstr);

	}

}
