package de.wk.betacore.warpass;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;

public class WarFiles {
    File f = new File("plugins//warpass");
    File cfgf = new File("plugins//warpass//config.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public WarFiles(){
        if(!f.exists()){
            f.mkdirs();
            for (int i = 0; i < cfg.getInt("levels"); i++){
                File fc = new File("plugins//warpass//level" + i++);
                fc.mkdir();
                File fd = new File("plugins//warpass//level" + i++ + "//money.money");
                File fe = new File("plugins//warpass//level" + i++ + "//readme.txt");
                try {
                    fd.createNewFile();
                    fe.createNewFile();
                } catch (IOException e) {
                }
                try {
                    FileWriter fw = new FileWriter(fe);
                    fw.write("Tutorial:\n" +
                            "add a schematic to the player:\n" +
                            "rename the schematic file to: \"schematic.schematic\"\n" +
                            "add money to a player:\n" +
                            "write the amount of money in the money.money file!");
                    fw.close();
                } catch (IOException e) {
                }
            }
        }
    }
    private void initCFG(){
        cfg.set("levels", 100);
    }
    public int getMoney(int level){
        File fd = new File("plugins//warpass//level" + level + "//money.money");
        try{
            FileInputStream fis = new FileInputStream(fd);
            return  fis.read();

        } catch (IOException e) {
            return 0;
        }
    }
    public File getSchem(int level){
        File fd = new File("plugins//warpass//level" + level + "//schematic.schematic");
        if(fd.exists()){
            return fd;
        }else{
            return null;
        }
    }
}
