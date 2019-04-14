package de.exceptionflug.schemloader.cmd;

public class WarShipTeam {


    public WarShipTeam() {

    }


    public int getId() {
        return 0;
    }

    public synchronized static WarShipTeam getWarShipTeam() {
        return new WarShipTeam();
    }

    public synchronized static WarShipTeam getWarShipTeam(final String WARSHIPTEAM) {
        return new WarShipTeam();
    }

}
