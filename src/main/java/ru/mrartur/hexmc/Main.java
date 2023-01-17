package ru.mrartur.hexmc;

import ru.mrartur.hexmc.packet.*;

import java.io.*;
import java.util.Properties;

public class Main {
    public static int port;
    public static void main(String[] args) throws IOException {
        File propertiesFile = new File("server.properties");
        Properties properties = new Properties();
        if(propertiesFile.exists()) {
            FileInputStream inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
        }else{
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("server.properties");
            properties.load(inputStream);
            properties.store(new FileOutputStream(propertiesFile), "");
        }
        try {
            port = Integer.parseInt(properties.getProperty("port"));
        }catch(NumberFormatException e){
            System.err.println("Failed to parse port.");
            return;
        }
        PacketManager.packets.add(Message.class);
        PacketManager.packets.add(PlayerIdentification.class);
        PacketManager.packets.add(SetBlockToServer.class);
        PacketManager.packets.add(SetPosition.class);
        ClassicServer server = new ClassicServer(port);
        server.start();
    }
}