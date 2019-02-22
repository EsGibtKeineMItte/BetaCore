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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomCommand implements CommandExecutor, Listener {

    public static Player edit = null;
    static ArrayList<String> lines = new ArrayList<>();
    public static String edit1;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("betacore.cc")){
                if(args.length == 0){
                    p.sendMessage("§bCustom Command §l| §cSyntax: /cc help");
                }if (args.length == 1){
                    if(args[0].equalsIgnoreCase("help")){
                        p.sendMessage("§b--==§lCustom Command§b==--");
                        p.sendMessage("§c/cc §ehelp §7| §aShows this Message");
                        p.sendMessage("§c/cc §ecreate §7| §aCreate a new Command");
                        p.sendMessage("§c/cc §edelete [name] §7| §aDelete a Command");
                    }else if(args[0].equalsIgnoreCase("create")){
                        if(edit == null){
                            edit = p;
                        }else {
                            p.sendMessage("§bCustom Command §l| §cOnly one command can be edited at the same time!");
                        }
                    }

                }
            }
        }
        return true;
    }
    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e){
        if(e.getPlayer() == edit){
            e.setCancelled(true);
            String msg = e.getMessage();
            Player p = e.getPlayer();
            if(msg.startsWith("help")) {
                IChatBaseComponent comp1 = IChatBaseComponent.ChatSerializer.a("{\"text\":\"[Add]\",\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"add [code]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Add an Line to the code\",\"color\":\"gold\"}]}}}");
                IChatBaseComponent comp3 = IChatBaseComponent.ChatSerializer.a("{\"text\":\" [Remove]\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"remove [line]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Remove a line from the code\",\"color\":\"gray\"}]}}}");
                IChatBaseComponent comp4 = IChatBaseComponent.ChatSerializer.a("{\"text\":\" [Edit]\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"edit [line] [code]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Edit an code line\",\"color\":\"gray\"}]}}}");
                IChatBaseComponent comp5 = IChatBaseComponent.ChatSerializer.a("{\"text\":\" [Finish]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"finish [name]\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Write the code\",\"color\":\"gray\"}]}}}");
                PacketPlayOutChat pack1 = new PacketPlayOutChat(comp1);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack1);
                pack1 = new PacketPlayOutChat(comp3);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack1);
                pack1 = new PacketPlayOutChat(comp4);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack1);
                pack1 = new PacketPlayOutChat(comp5);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack1);
                int i = 0;
                for (String line :
                        lines) {
                    i++;
                    IChatBaseComponent comp2 = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + i + ". \",\"color\":\"gold\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"edit " + i + " " + line + "\"}},{\"text\":\"" + lines + "\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"edit " + i + " " + line + "\"}}");
                    PacketPlayOutChat pack2 = new PacketPlayOutChat(comp2);
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack2);
                }
            }else if(msg.startsWith("add")){
                msg.replace("add ", "");
                lines.add(msg);
            }else if(msg.startsWith("finish")){
                msg.replace("finish ", "");
                File f = new File("plugins//commands//" + msg +".command");
                f.mkdirs();
                try {
                    FileWriter fw = new FileWriter(f);
                    for (String line:
                         lines) {
                        fw.write(line);
                    }
                    fw.close();
                    edit = null;
                    lines.clear();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }else if(msg.startsWith("edit")){
                msg.replace("edit ", "");
                String[] args = msg.split(" ");
                int i = Integer.parseInt(args[0]);

                lines.remove(i+1);
                for (int i1 = 1; i1 < args.length; i1++){
                    edit1 = edit1 + args[i];
                }
                lines.set(i+1, edit1);
            }
        }
    }
}
