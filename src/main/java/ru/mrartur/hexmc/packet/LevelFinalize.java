package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LevelFinalize implements PacketSerializer {
    public short x;
    public short y;
    public short z;

    public LevelFinalize(short xSize, short ySize, short zSize) {
        x = xSize;
        y = ySize;
        z = zSize;
    }

    @Override
    public int getPacketID() {
        return 4;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeShort(x);
        outputStream.writeShort(y);
        outputStream.writeShort(z);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
