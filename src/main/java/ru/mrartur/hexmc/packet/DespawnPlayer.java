package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DespawnPlayer implements PacketSerializer {
    public byte playerID;

    public DespawnPlayer(byte playerID) {
        this.playerID = playerID;
    }

    @Override
    public int getPacketID() {
        return 0x0c;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(playerID);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
