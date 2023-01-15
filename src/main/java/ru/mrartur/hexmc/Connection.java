package ru.mrartur.hexmc;

import ru.mrartur.hexmc.entity.Player;

public class Connection {
    private boolean authorized = false;
    private Player player;
    public Connection(){

    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
