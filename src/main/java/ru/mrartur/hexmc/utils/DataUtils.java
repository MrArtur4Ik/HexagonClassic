package ru.mrartur.hexmc.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataUtils {
    /*public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        gzipOutputStream.write(data);
        byte[] result = outputStream.toByteArray();
        gzipOutputStream.close();
        outputStream.close();
        return result;
    }
    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        byte[] result = gzipInputStream.readAllBytes();
        gzipInputStream.close();
        inputStream.close();
        return result;
    }*/
    /*public static byte[] compress2(byte[] data) {
        byte[] output = new byte[data.length];
        Deflater compresser = new Deflater();
        compresser.setInput(data);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output);
        byte[] dest = new byte[compressedDataLength];
        System.arraycopy(output, 0, dest, 0, compressedDataLength);
        return dest;
    }*/
    //https://stackoverflow.com/questions/14777800/gzip-compression-to-a-byte-array
    public static byte[] gzipCompress(byte[] uncompressedData) {
        byte[] result = new byte[]{};
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressedData.length);
             GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
            gzipOS.write(uncompressedData);
            // You need to close it before using bos
            gzipOS.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] gzipDecompress(byte[] compressedData) {
        byte[] result = new byte[]{};
        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
             ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPInputStream gzipIS = new GZIPInputStream(bis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIS.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static List<byte[]> segmentByteArray(byte[] data){
        int segmentCount = (int) Math.ceil(data.length/1024.f);
        List<byte[]> result = new ArrayList<>();
        for(int i = 0; i < segmentCount; i++){
            result.add(Arrays.copyOfRange(data, i*1024, Math.min(i*1024+1024, data.length)));
        }
        return result;
    }
    public static String readString(InputStream is) throws IOException {
        return new String(is.readNBytes(64)).trim();
    }
    public static void writeString(OutputStream os, String str) throws IOException {
        byte[] result = new byte[64];
        Arrays.fill(result, (byte) 0x20);
        byte[] b = str.getBytes();
        for(int i = 0; i < b.length; i++){
            if(i >= 64) break;
            result[i] = b[i];
        }
        os.write(result);
    }
    public static short floatToShort(float f){
        return (short) (f*32.f);
    }
    public static float shortToFloat(short s){
        return s/32.f;
    }
    public static byte degreesToByte(float d){
        return (byte) (d%360.f/360.f*255);
    }
    public static float byteToDegrees(byte b){
        return b*360.f/255.f;
    }
}
