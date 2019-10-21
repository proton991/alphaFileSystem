package com.fudan.Utils;

import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.File.Impl.FileManagerImpl;
import com.fudan.Indexing.FileId;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.fmConfig;
import com.fudan.config.sysConfigurer;

import java.io.*;
import java.util.List;

public class alphaCat {
    public static byte[] getFileData(Id fileId) throws IOException, ClassNotFoundException {
        File f = null;
        List<FileManager> fmList = fmConfig.getFmList();
        for (FileManager tmp : fmList) {
            if (tmp.getFile(fileId) != null) {
                f = tmp.getFile(fileId);
            }
        }
        byte[] fileData = f.read(f.size());
        return fileData;
    }

    private static long checksum(byte[] bs) {
        long h = bs.length;
        for (byte b : bs) {
            long lb = (long) b;
            h = ((h << 1) | (h >>> 63) |
                    ((lb & 0xc3) << 41) | ((lb & 0xa7) << 12)) +
                    lb * 91871341 + 1821349192381L;
        }
        return h;
    }
    public static void retrieveOriginalFile(Id fileId){
//        String origin = sysConfigurer.test_dir_path + fileId.toString();
//        byte[] originBytes = null;
//        try {
//            originBytes = alphaSave.toByteArray(origin);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        long originMd5 = checksum(originBytes);
//        long retrieveMd5 = 0;
        String pathName = sysConfigurer.restore_file_path+fileId.toString();
        OutputStream os = null;
        try {
            byte[] content = getFileData(fileId);
//            retrieveMd5 = checksum(content);
            os = new FileOutputStream(pathName);
            os.write(content);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        if(originMd5 == retrieveMd5)
//            System.out.println("File fetched, check your file in the retrieve folder");
    }
}
