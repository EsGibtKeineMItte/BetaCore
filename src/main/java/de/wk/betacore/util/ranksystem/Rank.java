package de.wk.betacore.util.ranksystem;


import net.md_5.bungee.api.ChatColor;

public enum Rank {

    ADMIN("&4Admin", ChatColor.DARK_RED),
    //MANAGER("&eManager", ChatColor.YELLOW),
    DEV("&3Dev", ChatColor.AQUA),
    MOD("&cMod", ChatColor.RED),
    SUPPORTER("&1Supp", ChatColor.BLUE),
    ARCHI("&aBuilder", ChatColor.GREEN),
    YOU_TUBER("&5YT", ChatColor.DARK_PURPLE),
    PREMIUM("&6Premium", ChatColor.GOLD),
    USER("&7User", ChatColor.GRAY);


    private String name;
    private ChatColor color;

    private Rank(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
