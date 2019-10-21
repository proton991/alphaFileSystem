package com.fudan.config.Impl;

import com.fudan.File.FileManager;
import com.fudan.File.Impl.FileManagerImpl;
import com.fudan.Indexing.FileManagerId;
import com.fudan.Indexing.Id;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class fmConfig {
    private static List<FileManager> fmList;
    private int fmCount = 0;
    public static final String fmDirPath = "/workingDir/fileManager";




    public fmConfig(int fmCnt) {
        fmList = new ArrayList<>();
        this.fmCount = fmCnt;
        for (int i = 0; i < fmCount; i++) {
            Id fmId = new FileManagerId("fm-"+ i);
            fmList.add(new FileManagerImpl(fmId));
            //在workingDir下面建立对应的文件夹
            File dir = new File(fmDirPath+"/"+fmId.toString());
            dir.mkdir();
        }
    }

    public static void setFmList(List<FileManager> list) {
        fmList = list;
    }

    public static FileManager getFileManager() {
        Random random = new Random();
        return fmList.get(random.nextInt(fmList.size()));
    }

    public static FileManager getFileManager(int index) {
        return fmList.get(index);
    }

    public static List<FileManager> getFmList() {
        return fmList;
    }


}
