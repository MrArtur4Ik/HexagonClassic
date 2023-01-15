package ru.mrartur.hexmc.world;

import ru.mrartur.hexmc.ClassicServer;
import ru.mrartur.hexmc.entity.Player;
import ru.mrartur.hexmc.packet.*;
import ru.mrartur.hexmc.utils.DataUtils;
import ru.mrartur.hexmc.world.generator.WorldGenerator;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class World {
    private byte[] map;
    private short width, height, depth;
    public World(short width, short height, short depth){
        this.width = width;
        this.height = height;
        this.depth = depth;
        map = new byte[width*height*depth];
    }
    public short getWidth(){
        return width;
    }
    public short getHeight() {
        return height;
    }
    public short getDepth() {
        return depth;
    }
    public int getBlock(short x, short y, short z){
        return map[x+depth*(z+width*y)];
    }
    public void setBlock(short x, short y, short z, byte type, boolean sendPacket){
        map[x+depth*(z+width*y)] = type;
        if(sendPacket) ClassicServer.getServer().getPlayersOnline().forEach(player ->
                player.sendPacket(new SetBlockToClient(x, y, z, type)));
    }
    public void setBlock(short x, short y, short z, byte type){
        setBlock(x, y, z, type, true);
    }
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteOutputStream);
        outputStream.writeInt(width*height*depth);
        outputStream.write(map);
        return byteOutputStream.toByteArray();
    }
    public void generate(WorldGenerator generator){
        generator.generate(this);
    }
    public void sendWorld(Socket connection) throws IOException {
        PacketManager.sendPacket(connection, new LevelInitialize());
        byte[] levelData = DataUtils.gzipCompress(serialize());
        List<byte[]> chunks =  DataUtils.segmentByteArray(levelData);
        for(byte[] chunk : chunks){
            PacketManager.sendPacket(connection, new LevelDataChunk(chunk, (byte) 0));
        }
        PacketManager.sendPacket(connection, new LevelFinalize(getWidth(), getHeight(), getDepth()));
    }
    public List<Player> getPlayers(){
        return ClassicServer.getServer().getPlayersOnline().stream().filter(player -> player.getLocation().getWorld().equals(this)).toList();
    }
}
