package de.wk.betacore.util.ranksystem;

public enum Rank {

    ADMIN("Admin", "§4" , 0),
    DEV("Dev", "§3", 1),
    MOD("Mod", "§c" , 2),
    SUPPORTER("Supp", "§1", 3),
    ARCHI("Builder", "§a", 4),
    YOU_TUBER("YT", "§5", 5),
    PREMIUM("Premium", "§6", 6),
    USER("User", "§7", 7);


    private String name;
    private String color;


    private int priority;

    private Rank(String name, String color, int priority) {
        this.name = name;
        this.color = color;
        this.priority = priority;
    }


    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

}
