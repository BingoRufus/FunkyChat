package me.BingoRufus.FunkyChat.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import me.BingoRufus.FunkyChat.FunkyChat;

public class ListenerRegister {
	private List<Listener> listeners = new ArrayList<Listener>();

	public ListenerRegister(FunkyChat m) {
		register(m);

	}

	public void unregister() {
		listeners.forEach(listener -> {
			HandlerList.unregisterAll(listener);
		});
		listeners.clear();
	}

	public void register(FunkyChat m) {
		PluginManager pm = m.getServer().getPluginManager();
		listeners.add(new UpsideDownListener(m));

		listeners.add(new ReverseListener(m));
		listeners.add(new InventoryClick(m));
		listeners.forEach(listener -> {
			pm.registerEvents(listener, m);

		});
		

	}

}
