package ru.mrartur.hexmc.packet;

import ru.mrartur.hexmc.location.Location;
import ru.mrartur.hexmc.utils.DataUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetPosition implements PacketSerializer {
    public byte playerID;
    public float x;
    public float y;
    public float z;
    public float yaw;
    public float pitch;
    public SetPosition(){

    }
    public SetPosition(byte playerID, float x, float y, float z, float yaw, float pitch){
        this.playerID = playerID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public SetPosition(byte playerID, Location location){
        this.playerID = playerID;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }
    @Override
    public int getPacketID() {
        return 8;
    }

    @Override
    public PacketDirection getDirection() {
        return PacketDirection.BOTH;
    }

    @Override
    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(playerID);
        outputStream.writeShort(DataUtils.floatToShort(x));
        outputStream.writeShort(DataUtils.floatToShort(y));
        outputStream.writeShort(DataUtils.floatToShort(z));
        outputStream.writeByte(DataUtils.degreesToByte(yaw));
        outputStream.writeByte(DataUtils.degreesToByte(pitch));
    }

    @Override
    public void deserialize(DataInputStream inputStream) throws IOException {
        playerID = inputStream.readByte();
        x = DataUtils.shortToFloat(inputStream.readShort());
        y = DataUtils.shortToFloat(inputStream.readShort());
        z = DataUtils.shortToFloat(inputStream.readShort());
        yaw = DataUtils.byteToDegrees(inputStream.readByte());
        pitch = DataUtils.byteToDegrees(inputStream.readByte());
    }
}
