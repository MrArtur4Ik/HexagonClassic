package ru.mrartur.hexmc;

import ru.mrartur.hexmc.entity.Player;
import ru.mrartur.hexmc.location.Location;
import ru.mrartur.hexmc.packet.*;
import ru.mrartur.hexmc.profile.GameProfile;
import ru.mrartur.hexmc.world.World;

import java.io.IOException;
import java.net.Socket;

public class PacketHandler implements Runnable {
    private final ClassicServer server;
    private final Socket connection;
    private final PacketSerializer packetSerializer;

    public PacketHandler(ClassicServer server, Socket connection, PacketSerializer packetSerializer) {
        this.server = server;
        this.connection = connection;
        this.packetSerializer = packetSerializer;
    }

    @Override
    public void run() {
        try {
            if (packetSerializer instanceof PlayerIdentification packet) {
                System.out.println("Authorizing " + packet.username);
                if (server.getConnections().values().stream().filter(Connection::isAuthorized).anyMatch(c -> c.getPlayer().getName().equals(packet.username))) {
                    server.disconnect(connection, "This player already on server!");
                    return;
                }
                if (packet.username.equals("")) {
                    server.disconnect(connection, "Illegal nickname!");
                    return;
                }
                byte newEntityID = server.getNewEntityID();
                if (newEntityID == -1) {
                    server.disconnect(connection, "Technically too many players.");
                    return;
                }
                PacketManager.sendPacket(connection, new ServerIdentification(packet.protocolVersion, "suka", "blyat", (byte) 0x64));
                World world = server.defaultWorld;
                world.sendWorld(connection);
                Location location = new Location(world.getWidth() / 2.f, world.getHeight() / 2.f + 2, world.getDepth() / 2.f, 0.f, 0.f, server.defaultWorld);
                Player newPlayer = new Player(location,
                        newEntityID, new GameProfile(packet.username), connection);
                server.getEntities().add(newPlayer);
                newPlayer.spawn();
                Connection c = server.getConnections().get(connection);
                c.setAuthorized(true);
                c.setPlayer(newPlayer);
                System.out.println("Authorized " + packet.username + "!");
                server.broadcast(packet.username + " joined the game.");
                server.sendMessage(connection, "&aWelcome to sussy baka server written on Java maven");
            } else if (packetSerializer instanceof Message packet) {
                if (packet.message.equals("/version")) {
                    server.sendMessage(connection, "&fPaper version git-Paper-371 (MC: Classic)");
                    server.sendMessage(connection, "&f(Implementing API version: 0.30)");
                    return;
                }
                Player player = server.getConnections().get(connection).getPlayer();
                server.broadcast(player.getName() + ": " + packet.message.replace("%", "&"), player.getEntityID());
            } else if (packetSerializer instanceof SetBlockToServer packet) {
                byte blockType = packet.mode == 0 ? (byte) 0 : (byte) packet.blockType;
                if (server.defaultWorld.getBlock(packet.x, packet.y, packet.z) != blockType) {
                    server.defaultWorld.setBlock(packet.x, packet.y, packet.z, blockType);
                }
            } else if (packetSerializer instanceof SetPosition packet) {
                Player player = server.getConnections().get(connection).getPlayer();
                Location location = player.getLocation();
                location.setX(packet.x);
                location.setY(packet.y);
                location.setZ(packet.z);
                location.setYaw(packet.yaw);
                location.setPitch(packet.pitch);
                player.getWorld().getPlayers().stream()
                        .filter(p -> !p.equals(player))
                        .forEach(p -> p.sendPacket(new SetPosition(player.getEntityID(), location)));
            }
        } catch (IOException ignored) {

        }
    }
}
