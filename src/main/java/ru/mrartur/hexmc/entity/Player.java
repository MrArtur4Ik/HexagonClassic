package ru.mrartur.hexmc.entity;

import ru.mrartur.hexmc.ClassicServer;
import ru.mrartur.hexmc.location.Location;
import ru.mrartur.hexmc.packet.PacketManager;
import ru.mrartur.hexmc.packet.PacketSerializer;
import ru.mrartur.hexmc.packet.SpawnPlayer;
import ru.mrartur.hexmc.profile.GameProfile;

import java.io.IOException;
import java.net.Socket;

public class Player extends Entity {
    private GameProfile profile;
    private Socket connection;
    public Player(Location location, byte entityID, GameProfile profile, Socket connection) {
        super(location, entityID);
        this.profile = profile;
        this.connection = connection;
    }
    public String getName(){
        return profile.getNickname();
    }
    public GameProfile getProfile() {
        return profile;
    }
    public boolean sendPacket(PacketSerializer packet) {
        try {
            PacketManager.sendPacket(connection, packet);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void spawn(){
        getWorld().getPlayers().stream()
                .filter(player -> !player.equals(this))
                .forEach(player -> player.sendPacket(new SpawnPlayer(getEntityID(), getName(), getLocation())));
        sendPacket(new SpawnPlayer((byte) -1, getName(), getLocation()));
        getWorld().getPlayers().stream()
                .filter(player -> !player.equals(this))
                .forEach(player -> sendPacket(new SpawnPlayer(player.getEntityID(), player.getName(), player.getLocation())));
    }
}
