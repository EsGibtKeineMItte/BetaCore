package de.wk.betacore.threads;

import de.wk.betacore.listener.Spigot.JoinHandler;

public class Update extends Thread {

    public static boolean isStarted = false;
    JoinHandler joinHandler = new JoinHandler();

    @Override
    public void run() {
        isStarted = true;
        joinHandler.updateLists();

        try {
            sleep(30000);
        } catch (InterruptedException e) {

        }
    }

}
