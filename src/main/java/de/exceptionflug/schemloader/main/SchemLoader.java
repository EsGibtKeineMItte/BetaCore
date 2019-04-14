package de.exceptionflug.schemloader.main;

import de.wk.betacore.environment.Environment;
import de.wk.betacore.util.ConnectionHolder;
import de.wk.betacore.util.data.Misc;
import lombok.Getter;
import net.thecobix.brew.main.Brew;


public class SchemLoader {

    @Getter
    private static ConnectionHolder connectionHolder;


    public static final String SCHEM_DIR = Environment.getPathToDataFolder() + "/schematics/";
    public static String prefix = Misc.SCHEMLOADER_PREFIX;

    public static Brew brew;





   public static synchronized SchemLoader getInstance(){
        return new SchemLoader();
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
