package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetBlockToServer implements PacketSerializer {
    public short x;
    public short y;
    public short z;
    public byte mode;
    public int blockType;

    public SetBlockToServer() {

    }

    public SetBlockToServer(short x, short y, short z, byte mode, int blockType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mode = mode;
        this.blockType = blockType;
    }

    @Override
    public int getPacketID() {
        return 0x05;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_SERVER;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {

    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {
        x = inputStream.readShort();
        y = inputStream.readShort();
        z = inputStream.readShort();
        mode = inputStream.readByte();
        blockType = inputStream.readByte();
    }
}
