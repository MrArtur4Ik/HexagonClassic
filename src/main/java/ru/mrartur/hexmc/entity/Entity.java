package ru.mrartur.hexmc.entity;

import ru.mrartur.hexmc.ClassicServer;
import ru.mrartur.hexmc.location.Location;
import ru.mrartur.hexmc.packet.DespawnPlayer;
import ru.mrartur.hexmc.world.World;

public class Entity {
    private Location location;
    private byte entityID;
    public Entity(Location location, byte entityID){
        this.location = location;
        this.entityID = entityID;
    }
    public Location getLocation() {
        return location;
    }

    public byte getEntityID() {
        return entityID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public World getWorld(){
        return location.getWorld();
    }
    public void destroy(){
        ClassicServer.getServer().getPlayersOnline().forEach(player -> player.sendPacket(new DespawnPlayer(entityID)));
        ClassicServer.getServer().getEntities().remove(this);
    }
}
