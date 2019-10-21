package com.fudan.config;



import com.fudan.Block.BlockManager;
import com.fudan.File.FileManager;
import com.fudan.config.Impl.bmConfig;
import com.fudan.config.Impl.fmConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class sysConfigurer {
    public final static int backupCount = 2;
    public final static String test_dir_path = "/workingDir/test/";
    public final static String restore_file_path = "/workingDir/retrieve/";
    private String bm_config_path = "/workingDir/serObj/bm/";
    private String fm_config_path = "/workingDir/serObj/fm/";
    private int bmCount = 0;
    private int fmCount = 0;
    //default initialization, 3 FileManagers 3 BlockManagers
    public sysConfigurer(){
        this.bmCount = 3;
        this.fmCount = 3;
    }

    //specified initialization
    public sysConfigurer(int bmCnt, int fmCnt){
        this.bmCount = bmCnt;
        this.fmCount = fmCnt;
    }

    public void initialize() throws IOException, ClassNotFoundException {
        List<FileManager> fmList = new ArrayList<>();
        List<BlockManager> bmList = new ArrayList<>();
        FileInputStream bmFis = null;
        ObjectInputStream bmOis = null;
        FileInputStream fmFis = null;
        ObjectInputStream fmOis = null;
        File[] bmFiles = new File(this.bm_config_path).listFiles();
        File[] fmFiles = new File(this.fm_config_path).listFiles();
        if(fmFiles.length != 0){
            for (int i = 0; i < fmFiles.length; i++) {
                fmFis = new FileInputStream(fmFiles[i].getPath());
                fmOis = new ObjectInputStream(fmFis);
                FileManager tmp = (FileManager) fmOis.readObject();
                fmList.add(tmp);
            }
            fmConfig.setFmList(fmList);
        }
        else {
            fmConfig fmConf = new fmConfig(this.fmCount);
        }
        if(bmFiles.length != 0){
            for (int i = 0; i < bmFiles.length; i++) {
                bmFis = new FileInputStream(bmFiles[i].getPath());
                bmOis = new ObjectInputStream(bmFis);
                BlockManager tmp = (BlockManager) bmOis.readObject();
                bmList.add(tmp);
            }
            bmConfig.setBmList(bmList);
        }
        else {
            bmConfig bmConf = new bmConfig(this.bmCount);
        }

    }

    public void serialize() throws IOException {
        List<FileManager> fmList = fmConfig.getFmList();
        List<BlockManager> bmList = bmConfig.getBmList();
        FileOutputStream bmFos = null;
        ObjectOutputStream bmOos = null;
        FileOutputStream fmFos = null;
        ObjectOutputStream fmOos = null;

        for(FileManager fm : fmList){
            fmFos = new FileOutputStream(this.fm_config_path + fm.getId().toString() + ".ser");
            fmOos = new ObjectOutputStream(fmFos);
            fmOos.writeObject(fm);
        }
        for(BlockManager bm : bmList){
            bmFos = new FileOutputStream(this.bm_config_path + bm.getBlockManagerId().toString() + ".ser");
            bmOos = new ObjectOutputStream(bmFos);
            bmOos.writeObject(bm);
        }

        //关闭流
        bmOos.close();
        fmOos.close();
    }

}
