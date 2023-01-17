package ru.mrartur.hexmc.packet;

import ru.mrartur.hexmc.utils.DataUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerIdentification implements PacketSerializer {
    public byte protocolVersion;
    public String username;
    public String key;
    public byte unused;

    /*public PlayerIdentification(byte protocolVersion, String username, String key, byte unused){

    }*/
    public PlayerIdentification() {

    }

    @Override
    public int getPacketID() {
        return 0;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_SERVER;
    }

    @Override
    public void serialize(DataOutputStream outputStream) {

    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {
        protocolVersion = inputStream.readByte();
        username = DataUtils.readString(inputStream);
        key = DataUtils.readString(inputStream);
        unused = inputStream.readByte();
    }
}
