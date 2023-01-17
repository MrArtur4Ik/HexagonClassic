package ru.mrartur.hexmc.packet;

import ru.mrartur.hexmc.utils.DataUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerIdentification implements PacketSerializer {
    public byte protocolVersion;
    public String name;
    public String motd;
    public byte userType;

    public ServerIdentification(byte protocolVersion, String name, String motd, byte userType) {
        this.protocolVersion = protocolVersion;
        this.name = name;
        this.motd = motd;
        this.userType = userType;
    }

    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.write(protocolVersion);
        DataUtils.writeString(outputStream, name);
        DataUtils.writeString(outputStream, motd);
        outputStream.write(userType);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
