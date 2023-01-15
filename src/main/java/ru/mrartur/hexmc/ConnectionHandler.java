package ru.mrartur.hexmc;

import ru.mrartur.hexmc.entity.Player;
import ru.mrartur.hexmc.packet.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;

public class ConnectionHandler implements Runnable {
    private ClassicServer server;
    private Socket connection;
    public ConnectionHandler(ClassicServer server, Socket connection){
        this.server = server;
        this.connection = connection;
    }
    @Override
    public void run() {
        System.out.println(connection.getInetAddress().getHostAddress() + ":" + connection.getPort() + " connected!");
        server.getConnections().put(connection, new Connection());
        Random random = new Random();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                PacketManager.sendPacket(connection, new Ping());
            } catch (IOException ignored) { }
        }, 10, 10, TimeUnit.SECONDS);
        while(connection.isConnected()) {
            try {
                PacketSerializer packetSerializer = PacketManager.readPacket(connection);
                new Thread(new PacketHandler(server, connection, packetSerializer), "PacketHandler-" + random.nextInt(100000, 999999)).start();
            }catch(IOException e){
                break;
            }
        }
        if(server.getConnections().containsKey(connection)){
            if(server.getConnections().get(connection).isAuthorized()) {
                Player player = server.getConnections().get(connection).getPlayer();
                player.destroy();
                server.broadcast(player.getName() + " left the game.");
            }
        }
        server.getConnections().remove(connection);
        executor.shutdown();
    }
}
