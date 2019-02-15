package de.wk.betacore.appearance;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class Tablist {


    /**
     * Update the Tablist Header and Footer of the Player
     * (n) gets Replaced to a New line Char
     * (Name) gets Replaced to the Name of the Player
     * (Server) gets Replaced to the Server of the Player
     * (World) gets Replaced to the World of the Player
     *
     * @param header The String above the Tablist
     * @param footer The String below the Tablist
     * @param p The Player to set your Tablist changes to
     */

    public static void Tablist(String header, String footer, Player p) {
        header = header.replaceAll("\\(n\\)", "\n");
        header = header.replaceAll("\\(Name\\)", p.getName());
        header = header.replaceAll("\\(Server\\)", p.getServer().getName());
        header = header.replaceAll("\\(World\\)", p.getWorld().getName());

        footer = footer.replaceAll("\\(n\\)", "\n");
        footer = footer.replaceAll("\\(Name\\)", p.getName());
        footer = footer.replaceAll("\\(Server\\)", p.getServer().getName());
        footer = footer.replaceAll("\\(World\\)", p.getWorld().getName());

        header = Color.ConvertColor(Color.ConvertSpecial(header));
        footer = Color.ConvertColor(Color.ConvertSpecial(footer));

        if(header == null) header = "";
        if(footer == null) footer = "";

        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field field = headerPacket.getClass().getDeclaredField("a");
            field.setAccessible(true);
            field.set(headerPacket, tabHeader);
            field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFooter);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(headerPacket);
        }
    }

    /**
     * Update the Tablist Header and Footer of some Players
     * (n) gets Replaced to a New line Char
     * (Name) gets Replaced to the Name of the Player
     * (Server) gets Replaced to the Server of the Player
     * (World) gets Replaced to the World of the Player
     *
     * @param header The String above the Tablist
     * @param footer The String below the Tablist
     * @param all all Players you want to set the Tablist (Player Array)
     */

    public static void Tablist(String header, String footer, Player[] all) {
        header = header.replaceAll("\\(n\\)", "\n");
        footer = footer.replaceAll("\\(n\\)", "\n");

        header = Color.ConvertColor(Color.ConvertSpecial(header));
        footer = Color.ConvertColor(Color.ConvertSpecial(footer));

        if(header == null) header = "";
        if(footer == null) footer = "";

        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
        for (Player p : all) {
            try {
                header = header.replaceAll("\\(Name\\)", p.getName());
                header = header.replaceAll("\\(Server\\)", p.getServer().getName());
                header = header.replaceAll("\\(World\\)", p.getWorld().getName());

                footer = footer.replaceAll("\\(n\\)", "\n");
                footer = footer.replaceAll("\\(Name\\)", p.getName());
                footer = footer.replaceAll("\\(Server\\)", p.getServer().getName());
                footer = footer.replaceAll("\\(World\\)", p.getWorld().getName());

                Field field = headerPacket.getClass().getDeclaredField("a");
                field.setAccessible(true);
                field.set(headerPacket, tabHeader);
                field = headerPacket.getClass().getDeclaredField("b");
                field.setAccessible(true);
                field.set(headerPacket, tabFooter);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(headerPacket);
            }
        }
    }


}
