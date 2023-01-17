package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface PacketSerializer {
    int getPacketID();

    PacketDirection getDirection();

    void serialize(DataOutputStream outputStream) throws IOException;

    void deserialize(DataInputStream inputStream) throws IOException;
}
