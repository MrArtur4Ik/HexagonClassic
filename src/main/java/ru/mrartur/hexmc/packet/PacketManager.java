package ru.mrartur.hexmc.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PacketManager {
    public static List<Class<? extends PacketSerializer>> packets = new ArrayList<>();
    public static PacketSerializer readPacket(Socket connection) throws IOException {
        DataInputStream inputStream = new DataInputStream(connection.getInputStream());
        int packetID = inputStream.readUnsignedByte();
        for(Class<? extends PacketSerializer> packetClass : packets){
            try {
                PacketSerializer packet = packetClass.newInstance();
                if (packet.getPacketID() == packetID && packet.getDirection().isServerBound()) {
                    packet.deserialize(inputStream);
                    return packet;
                }
            }catch (InstantiationException | IllegalAccessException ignored) { }
        }
        throw new IllegalArgumentException("Unknown packet ID: " + packetID);
    }
    public static void sendPacket(Socket connection, PacketSerializer packetSerializer) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream1 = new DataOutputStream(baos);
        outputStream1.writeByte(packetSerializer.getPacketID());
        packetSerializer.serialize(outputStream1);
        outputStream.write(baos.toByteArray());
    }
}
