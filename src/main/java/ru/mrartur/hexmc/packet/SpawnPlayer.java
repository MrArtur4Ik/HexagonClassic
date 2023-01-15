package ru.mrartur.hexmc.packet;

import ru.mrartur.hexmc.location.Location;
import ru.mrartur.hexmc.utils.DataUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SpawnPlayer implements PacketSerializer {
    public byte playerID;
    public String nickname;
    public float x;
    public float y;
    public float z;
    public float yaw;
    public float pitch;
    public SpawnPlayer(byte playerID, String nickname, float x, float y, float z, float yaw, float pitch){
        this.playerID = playerID;
        this.nickname = nickname;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public SpawnPlayer(byte playerID, String nickname, Location location){
        this.playerID = playerID;
        this.nickname = nickname;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }
    @Override
    public int getPacketID() {
        return 7;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.TO_CLIENT;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(playerID);
        DataUtils.writeString(outputStream, nickname);
        outputStream.writeShort(DataUtils.floatToShort(x));
        outputStream.writeShort(DataUtils.floatToShort(y));
        outputStream.writeShort(DataUtils.floatToShort(z));
        outputStream.writeByte(DataUtils.degreesToByte(yaw));
        outputStream.writeByte(DataUtils.degreesToByte(pitch));
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {

    }
}
