package com.fudan.config.Impl;

import com.fudan.Block.BlockManager;
import com.fudan.Block.Impl.BlockManagerImpl;
import com.fudan.File.FileManager;
import com.fudan.Indexing.BlockManagerId;
import com.fudan.Indexing.Id;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class bmConfig {

    private static List<BlockManager> bmList;
    private static int blockSize = 256;
    private int bmCount = 0;
    public static final String bmDirPath = "/workingDir/blockManager";

    public static BlockManager getBlockManager(Id id) {
        for (BlockManager bm : bmList) {
            if (bm.getBlockManagerId().toString().equals(id.toString()))
                return bm;
        }
        return null;
    }


    public bmConfig(int bmCnt) {
        bmList = new ArrayList<>();
        blockSize = 256;
        for (int i = 0; i < bmCnt; i++) {
            Id bmId = new BlockManagerId("bm-" + i);
            bmList.add(new BlockManagerImpl(bmId));
            //在workingDir下面建立对应的文件夹
            File dir = new File(bmDirPath + "/" + bmId.toString());
            dir.mkdir();
        }
    }

    public static List<BlockManager> getBmList() {
        return bmList;
    }

    public static void setBmList(List<BlockManager> list) {
        bmList = list;
    }

    public static BlockManager getBlockManager() {
        Random random = new Random();
        return bmList.get(random.nextInt(bmList.size()));
    }

    public static int getBlockSize() {
        return blockSize;
    }

}
