package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Ping implements PacketSerializer{
    @Override
    public int getPacketID() {
        return 1;
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
