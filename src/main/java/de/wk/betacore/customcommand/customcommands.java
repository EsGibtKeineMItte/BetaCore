package de.wk.betacore.customcommand;

public class customcommands {

    public static void initCommands() {
        setSuffix();
        setImplemented();
        addCommands();
    }

    private static void setSuffix() {
        CommandManager.setFileSuffix(".command");
    }

    private static void addCommands() {
        CommandManager.addCommand(new Command() {
            @Override
            public String command() {
                return "help";
            }

            @Override
            public void execute(int index) {
                System.out.println("help");
            }
        });
    }

    private static void setImplemented() {
        CommandManager.setImplemented("file", false);
        CommandManager.setImplemented("console", false);
        CommandManager.setImplemented("start", false);
        CommandManager.setImplemented("dict", false);
    }
}
