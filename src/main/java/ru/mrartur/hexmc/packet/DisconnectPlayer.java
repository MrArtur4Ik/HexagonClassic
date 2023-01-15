package ru.mrartur.hexmc.packet;

import ru.mrartur.hexmc.utils.DataUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DisconnectPlayer implements PacketSerializer{
    public String reason;
    public DisconnectPlayer(String reason){
        this.reason = reason;
    }
    @Override
    public int getPacketID() {
        return 0x0e;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        DataUtils.writeString(outputStream, reason);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
