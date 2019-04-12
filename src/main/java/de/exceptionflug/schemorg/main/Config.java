package de.exceptionflug.schemorg.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    public FileConfiguration cfg;
    public Location autoPasteLoc;
    public Location checkSpawn;
    
    public Config() {
        this.file = new File("plugins/SchemOrg/config.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
    }
    
    public void initConfig() {
        this.cfg.options().copyDefaults(true);
        this.cfg.addDefault("Location.pasteloc", "world/0/64/0/90/0");
        this.cfg.addDefault("Location.checkSpawn", "world/0/64/0/90/0");
        try {
            this.cfg.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkSpawn = toLocation(cfg.getString("Location.checkSpawn"));
        autoPasteLoc = toLocation(cfg.getString("Location.pasteloc"));
    }
    
    public Location toLocation(String configString) {
        String[] splitted = configString.split("/");
        String wName = splitted[0];
        double x = Double.parseDouble(splitted[1]);
        double y = Double.parseDouble(splitted[2]);
        double z = Double.parseDouble(splitted[3]);
        float yaw = Float.parseFloat(splitted[4]);
        float pitch = Float.parseFloat(splitted[5]);
        if(Bukkit.getWorld(wName) == null)
            Bukkit.createWorld(new WorldCreator(wName));
        return new Location(Bukkit.getWorld(wName), x, y, z, yaw, pitch);
    }

    public void save() {
        try {
            this.cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
