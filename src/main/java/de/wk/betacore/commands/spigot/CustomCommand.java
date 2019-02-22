package de.wk.betacore.commands.spigot;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class CustomCommand implements CommandExecutor, Listener {

    static Player edit = null;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("betacore.cc")){
                if(args.length == 0){
                    p.sendMessage("§bCustom Command §l| §c Syntax: /cc help");
                }if (args.length == 1){
                    if(args[0].equalsIgnoreCase("help")){
                        p.sendMessage("§b--==§lCustom Command§b==--");
                        p.sendMessage("§c/cc §ehelp §7| §aShows this Message");
                        p.sendMessage("§c/cc §ecreate §7| §aCreate a new Command");
                        p.sendMessage("§c/cc §edelete [name] §7| §aDelete a Command");
                    }else if(args[0].equalsIgnoreCase("create")){
                        edit = p;
                    }

                }
            }
        }
        return true;
    }
    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e){
        if(e.getPlayer().equals(edit)){
            e.setCancelled(true);
            String msg = e.getMessage();
            Player p = e.getPlayer();
            if(msg.startsWith("help")){
                IChatBaseComponent comp1 = IChatBaseComponent.ChatSerializer.a("{\"text\":\"[Add]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"add [code]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Add an Line to the code\",\"color\":\"gold\"}]}}},{\"text\":\" [Remove]\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"remove [line]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Remove a line from the code\",\"color\":\"gray\"}]}}},{\"text\":\" [Edit]\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"edit [line] [code]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Edit an code line\",\"color\":\"gray\"}]}}}");
                PacketPlayOutChat pack1 = new PacketPlayOutChat(comp1);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack1);
            }
        }
    }
}
