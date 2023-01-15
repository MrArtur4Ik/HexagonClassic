package ru.mrartur.hexmc.packet;

import ru.mrartur.hexmc.utils.DataUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Message implements PacketSerializer {
    public byte playerID;
    public String message;
    public Message(){

    }
    public Message(byte playerID, String message){
        this.playerID = playerID;
        this.message = message;
    }
    @Override
    public int getPacketID() {
        return 0x0d;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.BOTH;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(playerID);
        DataUtils.writeString(outputStream, message);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {
        playerID = inputStream.readByte();
        message = DataUtils.readString(inputStream);
    }
}
