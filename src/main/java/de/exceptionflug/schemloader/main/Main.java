package de.exceptionflug.schemloader.main;

import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.ConnectionHolder;
import lombok.Getter;
import net.thecobix.brew.main.Brew;


public class Main  {

    @Getter
    private static ConnectionHolder connectionHolder;


    public static final String SCHEM_DIR = Environment.getPathToDataFolder() + "/schematics/";
    public static String prefix = "§8[§3Schematic§8] §7";

    public static Brew brew;

    private int runnerTask;




   public static synchronized Main getInstance(){
        return new Main();
   }

    public static boolean isAllowed(int id) {
        switch (id) {
            case 166:
                return false;

            case 137:
                return false;

            case 120:
                return false;

            case 145:
                return false;

            case 116:
                return false;

            case 130:
                return false;

            case 10:
                return false;

            case 11:
                return false;

            case 122:
                return false;

            case 121:
                return false;

            case 138:
                return false;

            case 52:
                return false;

            case 79:
                return false;

            case 46:
                return false;

            case 325:
                return false;

            default:
                return true;
        }
    }

}
