package de.exceptionflug.schemorg.util;

import de.leonhard.storage.Json;
import de.wk.betacore.BetaCore;
import de.wk.betacore.datamanager.FileManager;
import de.wk.betacore.environment.EnvironmentManager;
import de.wk.betacore.util.MySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;


public class DBUtil {

    private static Json schematics = FileManager.getSchematics();

    public static void addRejection(String ownaz, UUID rejector, String schem, String reason) throws SQLException {
        //TODO CREATE REJECTION FOR BETACORE

        if(EnvironmentManager.isMysql()){
            PreparedStatement ps = MySQL.preparedStatement("INSERT INTO DeclinedSchematics (owner,schematic,reason,rejector) VALUES (?,?,?,?)");
            ps.setString(1, ownaz);
            ps.setString(2, schem);
            ps.setString(3, reason);
            ps.setString(4, rejector.toString());
            ps.executeUpdate();
        }
        BetaCore.debug("Es wurde versucht eine Schematic anzunehmen/abzulehnen, während MySQL deaktiviert ist.");

    }

}
