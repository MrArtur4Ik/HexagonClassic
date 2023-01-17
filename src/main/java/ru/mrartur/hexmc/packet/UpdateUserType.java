package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UpdateUserType implements PacketSerializer {
    public byte userType;

    public UpdateUserType(byte userType) {
        this.userType = userType;
    }

    @Override
    public int getPacketID() {
        return 0x0f;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(userType);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
