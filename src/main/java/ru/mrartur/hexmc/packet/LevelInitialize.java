package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LevelInitialize implements PacketSerializer {
    @Override
    public int getPacketID() {
        return 2;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        //none
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
