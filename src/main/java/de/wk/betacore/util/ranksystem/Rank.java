package de.wk.betacore.util.ranksystem;


import net.md_5.bungee.api.ChatColor;

public enum Rank {

    ADMIN("Admin", ChatColor.DARK_RED),
    MANAGER("Manager", ChatColor.YELLOW),
    DEV("Dev", ChatColor.AQUA),
    MOD("Mod", ChatColor.RED),
    SUPPORTER("Supp", ChatColor.BLUE),
    ARCHI("Builder", ChatColor.GREEN),
    YOU_TUBER("YT", ChatColor.DARK_PURPLE),
    PREMIUM("Premium", ChatColor.GOLD),
    USER("User", ChatColor.GRAY);


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
