package ru.mrartur.hexmc.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LevelDataChunk implements PacketSerializer {
    public byte[] chunkData;
    public byte percentComplete;
    public LevelDataChunk(byte[] chunkData, byte percentComplete){
        this.chunkData = chunkData;
        this.percentComplete = percentComplete;
    }
    @Override
    public int getPacketID() {
        return 3;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        if(chunkData.length > 1024) throw new IllegalArgumentException("Chunk data size is greater than 1024 bytes.");
        byte[] data = new byte[1024];
        for(int i = 0; i < chunkData.length; i++){
            data[i] = chunkData[i];
        }
        outputStream.writeShort(chunkData.length);
        outputStream.write(data);
        outputStream.writeByte(percentComplete);
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
