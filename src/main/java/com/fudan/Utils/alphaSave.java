package com.fudan.Utils;

import com.fudan.File.FileManager;
import com.fudan.Indexing.FileId;
import com.fudan.config.Impl.bmConfig;
import com.fudan.config.Impl.fmConfig;

import java.io.*;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class alphaSave {
    public static void saveFile(String filePath) throws IOException {
        byte[] fileBytes = toByteArray(filePath);
        FileManager fm = fmConfig.getFileManager();
        FileId fileId = new FileId(getFileName(filePath));
        com.fudan.File.File file = fm.newFile(fileId);
        //保存文件
        alphaWrite.write(fileId, fileBytes, 0);

    }

    public static byte[] toByteArray(String filename)throws IOException {

        FileChannel fc = null;
        try{
            fc = new RandomAccessFile(filename,"r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).load();
//            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int)fc.size()];
            if (byteBuffer.remaining() > 0) {
//              System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        }catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally{
            try{
                fc.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileName(String filePath) {
        File f= new File(filePath);
        String filename = f.getName();
//        if ((filename != null) && (filename.length() > 0)) {
//            int dot = filename.lastIndexOf('.');
//            if ((dot >-1) && (dot < (filename.length() - 1))) {
//                return filename.substring(0, dot);
//            }
//        }
        return filename;
    }



}
