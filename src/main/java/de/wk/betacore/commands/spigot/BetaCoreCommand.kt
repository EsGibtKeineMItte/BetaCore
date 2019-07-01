package de.wk.betacore.commands.spigot

import com.minnymin.command.Command
import com.minnymin.command.CommandArgs

class BetaCoreCommand {



    @Command(name = "betacore", description = "Bannt einen Spieler", permission = "betacore.bann", aliases = arrayOf("bc"))
    fun onBetaCore(args:CommandArgs){
        args.sender.sendMessage("§3This is the new betacore version. It will be soon replaced by XCore/WarCore & a new WSK")
    }

}