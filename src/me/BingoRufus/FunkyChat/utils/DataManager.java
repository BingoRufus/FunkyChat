package me.bingorufus.funkychat.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.bingorufus.funkychat.FunkyChat;

public class DataManager { // DataManager Class taken from CodedRed. https://youtu.be/-ZrIjYXOkn0

	private FunkyChat m;
	private FileConfiguration dataCfg = null;
	private File cfgFile = null;

	public DataManager(FunkyChat m) {
		this.m = m;
		saveDefaultConfig();

	}

	public void reloadConfig() {
		if (this.cfgFile == null)
			this.cfgFile = new File(this.m.getDataFolder(), "settings.yml");
		new YamlConfiguration();
		this.dataCfg = YamlConfiguration.loadConfiguration(this.cfgFile);

		InputStream defaultStream = this.m.getResource("settings.yml");
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataCfg.setDefaults(defaultConfig);

		}
	}

	public FileConfiguration getConfig() {
		if (this.dataCfg == null)
			reloadConfig();
		return this.dataCfg;
	}

	public void saveConfig() {
		if (this.dataCfg == null || this.cfgFile == null)
			return;
		try {
			this.getConfig().save(this.cfgFile);
		} catch (IOException e) {
			m.getLogger().log(Level.SEVERE, "Could not save config to " + this.cfgFile, e);
		}
	}

	public void saveDefaultConfig() {
		if (this.cfgFile == null)
			this.cfgFile = new File(this.m.getDataFolder(), "settings.yml");

		if (this.cfgFile.exists())
			this.m.saveResource("settings.yml", false);
	}
}
