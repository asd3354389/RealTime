package com.foxlink.realtime.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class ImageUtils {
	
	/**
     * 将文件保存到字节数组中
     * This class implements an output stream in which the data is written into a byte array. 
     * @author chunlynn
     */
    public static byte[] inputStreamToByte(InputStream in) throws Exception {
        // This class implements an output stream in which the data is written into a byte array. 
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 输出流对象，用来接收文件流，然后写入一个字节数组中
        int len;
        byte[] buffer = new byte[1024]; //缓存1KB
        while ((len = in.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] data = bos.toByteArray(); // 字节数组，输出流中的文件保存到字节数组
        bos.close();
        return data;
    }
}
