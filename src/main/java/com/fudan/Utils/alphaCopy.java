package com.fudan.Utils;

import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.File.Impl.FileMeta;
import com.fudan.Indexing.FileId;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.fmConfig;

//import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class alphaCopy {
    public static void copyFileUsingFileChannels(java.io.File source, java.io.File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
    public static void copy(Id fileId) throws IOException {
        List<FileManager> file = fmConfig.getFmList();
        FileMeta meta = null;
        FileManager fm = null;
        for (FileManager tmp : file) {
            File f = tmp.getFile(fileId);
            if (f != null) {
                meta = f.getFileMeta();
                fm = tmp;
            }
        }

        //找到fileMeta文件
        String src_path = fmConfig.fmDirPath + "/" + fm.getId().toString() + "/" + fileId.toString() + ".txt";

        String dst_path = fmConfig.fmDirPath + "/" + fm.getId().toString() + "/" + getFileName(fileId.toString()) + "-copy" + getFileExtensionName(fileId.toString()) + ".txt";
        java.io.File src = new java.io.File(src_path);
        java.io.File dst = new java.io.File(dst_path);

        //copy fileMeta
        copyFileUsingFileChannels(src, dst);
        //
        fm.copyFile(new FileId(getFileName(fileId.toString()) + "-copy" + getFileExtensionName(fileId.toString())), meta);

    }

    public static String getFileName(String fname) {
        String filename = fname;
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static String getFileExtensionName(String fname) {
        String filename = fname;
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return filename;
    }
}