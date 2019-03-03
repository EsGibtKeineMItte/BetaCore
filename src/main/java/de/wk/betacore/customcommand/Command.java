package de.wk.betacore.customcommand;

public interface Command {

    String command();

    void execute(int index);

}
