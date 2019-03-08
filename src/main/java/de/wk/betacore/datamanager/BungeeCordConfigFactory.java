package de.wk.betacore.datamanager;

import de.wk.betacore.BetaCoreBungee;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeCordConfigFactory {
    static File file;

    public static Configuration getConfig() {
        file = new File(BetaCoreBungee.getInstance().getProxy().getPluginsFolder() + "config.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("The config file could not be created and therefore does not exist.");
    }

    public static void saveConfig() {
        try {
            if (!file.exists()) {
            }
            ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .save(getConfig(), file);
        } catch (IOException e) {

        }
    }

}



