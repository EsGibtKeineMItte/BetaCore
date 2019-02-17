package de.wk.betacore.customcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class cmdParser {

    private static File f;

    public cmdParser(){

    }

    public static void Pars(Player p, String cmd){
        f = new File("plugins//commands//" + cmd +".command");
        if(!f.exists()){
            Bukkit.getConsoleSender().sendMessage("§a[Custom Command]§c Error at line: §l0 §cException: §4§lFileNotFound");
            return;
        }
        Executor ex = new Executor();
        try {
            Scanner sc = new Scanner(f);

            String[] blocks = {"permissions","greater","less","equal","select","send","teleport","money","rank","health","food","sudo"};
            ArrayList<String> lines = new ArrayList<>();

            while (sc.hasNextLine()){
                lines.add(sc.nextLine());
            }

            sc.close();

            int Line = 0;
            Player pe = p;

            for (String line:
                 lines) {
                Line++;
                if(line.startsWith("permissions")){

                }else if(line.startsWith("greater")){

                }else if(line.startsWith("less")){

                }else if(line.startsWith("equal")){

                }else if(line.startsWith("select")){

                }else if(line.startsWith("send")){

                }else if(line.startsWith("teleport")){

                }else if(line.startsWith("money")){

                }else if(line.startsWith("rank")){

                }else if(line.startsWith("health")){

                }else if(line.startsWith("food")){

                }else if(line.startsWith("sudo")){
                    String command = line.replace("sudo(", "").replace(")", "");
                    pe.performCommand(command);
                }else {
                    Bukkit.getConsoleSender().sendMessage("§a[Custom Command]§c Error at line: §l" + Line + " §cException: §4§lInvalid Block");
                }
            }
        } catch (FileNotFoundException e) {
            return;
        }




    }

}
