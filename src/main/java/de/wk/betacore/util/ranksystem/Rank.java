package de.wk.betacore.util.ranksystem;

public enum Rank {

    ADMIN("Admin", "§4"),
    DEV("Dev", "§3"),
    MOD("Mod", "§c"),
    SUPPORTER("Supp", "§1"),
    ARCHI("Builder", "§a"),
    YOU_TUBER("YT", "§5"),
    PREMIUM("Premium", "§6"),
    USER("User", "§7");


    private String name;
    private String color;

    private Rank(String name, String color) {
        this.name = name;
        this.color = color;
    }


    public String getName() {
        return name.toUpperCase();
    }

    public String getColor() {
        return color;
    }
}
