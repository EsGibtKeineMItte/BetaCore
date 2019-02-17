package de.wk.betacore.commands.bungee;

import de.wk.betacore.util.data.Misc;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {
  public PingCommand(){
      super("ping");
  }


    @Override
    public void execute(CommandSender sender, String[] args) {
      if(!(sender instanceof ProxiedPlayer)){
          sender.sendMessage(new TextComponent(Misc.getNOTINCONSOLE()));
      }
      ProxiedPlayer player = (ProxiedPlayer) sender;
      player.sendMessage(new TextComponent("§7Dein Ping ist: §6" + player.getPing() + " §7ms."));
    }
}
