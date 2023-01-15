package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetBlockToClient implements PacketSerializer {
    public short x;
    public short y;
    public short z;
    public int blockType;
    public SetBlockToClient(short x, short y, short z, int blockType){
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockType = blockType;
    }
    @Override
    public int getPacketID() {
        return 6;
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
        outputStream.writeByte(blockType);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
