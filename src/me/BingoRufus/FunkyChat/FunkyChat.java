package me.bingorufus.funkychat;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.bingorufus.funkychat.listeners.CommandGui;
import me.bingorufus.funkychat.listeners.ListenerRegister;
import me.bingorufus.funkychat.utils.DataManager;
import me.bingorufus.funkychat.utils.Metrics;

public class FunkyChat extends JavaPlugin {
	public HashMap<Player, Inventory> settingInventory = new HashMap<>();
	private HashMap<UUID, Settings> settings = new HashMap<UUID, Settings>();
	ListenerRegister register;
	DataManager dm;

	@Override
	public void onEnable() {
		new Metrics(this, 8349);
		dm = new DataManager(this);
		this.saveDefaultConfig();
		try {
		dm.getConfig().getConfigurationSection("settings").getKeys(true).forEach(key -> {
			Settings setting = new Settings();
			setting.setReverse(dm.getConfig().getConfigurationSection("settings").getConfigurationSection(key)
					.getBoolean("reverse"));
			setting.setUpsidedown(dm.getConfig().getConfigurationSection("settings").getConfigurationSection(key)
					.getBoolean("upsidedown"));
			settings.put(UUID.fromString(key), setting);

		});
		} catch (Exception e) {
		}
		getCommand("funkychat").setExecutor(new CommandGui(this));
		register = new ListenerRegister(this);

	}

	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p ->{
			if (settingInventory.containsValue(p.getOpenInventory().getTopInventory())) {
				p.closeInventory();
			}
		});

		settings.keySet().forEach(key -> {
			if (!dm.getConfig().isConfigurationSection("settings"))
				dm.getConfig().createSection("settings");
			ConfigurationSection con = dm.getConfig().getConfigurationSection("settings").createSection(key + "");
			con.set("reverse", settings.get(key).reverse());
			con.set("upsidedown", settings.get(key).upsidedown());

		});
		dm.saveConfig();
	}


	public Settings getSetting(Player p) {
		if (!settings.containsKey(p.getUniqueId()))
			defaultSettings(p);
		return settings.get(p.getUniqueId());

	}
	public void defaultSettings(Player p) {
		Settings dSetting = new Settings();
		dSetting.setReverse(false);
		settings.put(p.getUniqueId(), dSetting);
	}

}
