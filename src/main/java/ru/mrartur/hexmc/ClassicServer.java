package ru.mrartur.hexmc;

import ru.mrartur.hexmc.entity.Entity;
import ru.mrartur.hexmc.entity.Player;
import ru.mrartur.hexmc.world.World;
import ru.mrartur.hexmc.packet.DisconnectPlayer;
import ru.mrartur.hexmc.packet.Message;
import ru.mrartur.hexmc.packet.PacketManager;
import ru.mrartur.hexmc.world.generator.FlatGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassicServer {
    private static ClassicServer instance;
    private ServerSocket serverSocket;
    private HashMap<Socket, Connection> connections = new HashMap<>();
    private List<Entity> entities = new ArrayList<>();
    public World defaultWorld;
    public ClassicServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        instance = this;
    }
    public HashMap<Socket, Connection> getConnections() {
        return connections;
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public List<Player> getPlayersOnline(){
        /*List<Player> players = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Player player){
                players.add(player);
            }
        }*/
        return entities.stream().filter(entity -> !(entity instanceof Player)).map(entity -> (Player) entity).toList();
    }
    public byte getNewEntityID(){
        for(byte i = 0; i < 127; i++){
            byte entityID = i;
            if(entities.stream().noneMatch(entity -> entity.getEntityID() == entityID)){
                return entityID;
            }
        }
        return -1;
    }
    public void broadcast(String message, byte playerID){
        connections.keySet().forEach(socket -> {
            try {
                PacketManager.sendPacket(socket, new Message(playerID, message));
            } catch (IOException ignored) { }
        });
        System.out.println(message);
    }
    public void broadcast(String message){ broadcast(message, (byte) -1);}
    public void disconnect(Socket connection, String reason) throws IOException {
        PacketManager.sendPacket(connection, new DisconnectPlayer(reason));
        connection.close();
    }
    public void sendMessage(Socket connection, String message, byte playerID){
        try {
            PacketManager.sendPacket(connection, new Message(playerID, message));
        } catch (IOException ignored) { }
    }
    public void sendMessage(Socket connection, String message){ sendMessage(connection, message, (byte) -1);}
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (IOException ignored) { }
            List<Socket> connections = new ArrayList<>(this.connections.keySet());
            connections.forEach(socket -> {
                try {
                    disconnect(socket, "Server closed!");
                } catch (IOException ignored) {}
            });
        }));
        defaultWorld = new World((short) 256, (short) 64, (short) 256);
        defaultWorld.generate(new FlatGenerator());
        System.out.println("Server started on port " + serverSocket.getLocalPort());
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new ConnectionHandler(this, socket)).start();
            }catch(IOException e){

            }
        }
    }
    public void stop() throws IOException {
        serverSocket.close();
    }
    public static ClassicServer getServer(){
        return instance;
    }
}
