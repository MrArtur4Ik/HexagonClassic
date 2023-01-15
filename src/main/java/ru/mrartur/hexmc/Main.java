package ru.mrartur.hexmc;

import ru.mrartur.hexmc.packet.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PacketManager.packets.add(Message.class);
        PacketManager.packets.add(PlayerIdentification.class);
        PacketManager.packets.add(SetBlockToServer.class);
        PacketManager.packets.add(SetPosition.class);
        ClassicServer server = new ClassicServer(25565);
        server.start();
    }
}