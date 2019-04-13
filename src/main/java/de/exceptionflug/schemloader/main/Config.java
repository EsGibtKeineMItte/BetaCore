package de.exceptionflug.schemloader.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

	private File file;
	public FileConfiguration cfg;
	
	public Config() {
		this.file = new File("plugins/TheCobixSystem/config.yml");
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
	}
	
	public void initConfig() {
		this.cfg.options().copyDefaults(true);
		this.cfg.addDefault("MySQL.user", "enterHere");
		this.cfg.addDefault("MySQL.password", "enterHere");
		this.cfg.addDefault("forcespawn", false);
		try {
			this.cfg.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			this.cfg.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
