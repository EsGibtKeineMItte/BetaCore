package de.wk.betacore.customcommand;

public class CommandManager extends Executor {

    public static void setImplemented(String name, boolean value) {
        Executor.setImplemented(name, value);
    }

    public static void addCommand(Command cmd) {
        Executor.addCommand(cmd);
    }

    public static String tokenType(int Index) {
        return Executor.tokenType(Index);
    }

    public static Object tokenValue(int Index) {
        return Executor.tokenValue(Index);
    }

    public static void createVar(String name, String value, String type) {
        if (type.equals("str")) {
            Executor.addVar(name, value, type);
        } else if (type.equals("num") && value.matches("-?(0|[1-9]\\d*)(\\.([0-9]*))?")) {
            Executor.addVar(name, value, type);
        } else if (type.equals("bol") && value.matches("(true)||(false)")) {
            Executor.addVar(name, value, type);
        }
    }

    public static void deleteVar(String name) {
        Executor.deleteTempVars(name, false);
    }

    public static void setFileSuffix(String suffix) {
        Executor.setFileSuffix(suffix);
    }

}
