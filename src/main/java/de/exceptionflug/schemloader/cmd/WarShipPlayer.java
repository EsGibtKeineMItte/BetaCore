package de.exceptionflug.schemloader.cmd;

import exceptionflug.schemloader.cmd.WarShipTeam;
import org.apache.commons.lang.ObjectUtils;

import java.util.UUID;

public class WarShipPlayer {



    public WarShipTeam getTeam(){
        return new WarShipTeam();
    }

    public boolean isAdmin(){
        return false;
    }



    public static WarShipPlayer getByUniqueId(String uuid){
        return  new WarShipPlayer();
    }

    public static WarShipPlayer getByUniqueId(UUID uuid){
        return  new WarShipPlayer();
    }

    public static WarShipPlayer getByUniqueId(Integer uuid){//Damit das Ding zufrieden ist ^^
        return  new WarShipPlayer();
    }


    public static WarShipPlayer getByUniqueId(ObjectUtils.Null uuid){//Damit das Ding zufrieden ist ^^
        return  new WarShipPlayer();
    }

    public static int getNull(){
        System.out.print("[LIXFEL]: NULL EIN WERT!");
        return 0;
    }
}
