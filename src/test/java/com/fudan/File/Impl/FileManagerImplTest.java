package com.fudan.File.Impl;

import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.Indexing.FileId;
import com.fudan.config.Impl.fmConfig;
import com.fudan.config.sysConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class FileManagerImplTest {

    private sysConfigurer configurer = null;
    @Before
    public void _init() throws IOException, ClassNotFoundException {
        configurer = new sysConfigurer();
        configurer.initialize();
    }
    @Test
    public void getFile() throws IOException {
//        FileWriter fw = new FileWriter(fmConfig.fmDirPath + "/fm-1/1.txt");
//        BufferedWriter bw = new BufferedWriter(fw);
//        bw.write("hello world22");
//        bw.close();
//        fw.close();
    }

    @Test
    public void newFile() throws IOException {

        List<FileManager> list = fmConfig.getFmList();
        FileManager fm = fmConfig.getFileManager(0);

        File f = fm.newFile(new FileId("hello"));
        byte[] b = {0, 0, 0, 0, 0, 0};
        f.write(b);

//        for(FileManager fm : list){
//            if(fm.getFile(new FileId("hello")) != null){
//                String name = fm.getFile(new FileId("hello")).getFileId().toString();
//                System.out.println(name);
//            }
//
//        }
    }
    @After
    public void _save() throws IOException {
        configurer.serialize();
    }

}