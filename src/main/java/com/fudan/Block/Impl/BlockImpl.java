package com.fudan.Block.Impl;

import com.fudan.Block.Block;
import com.fudan.Block.BlockManager;
import com.fudan.Indexing.BlockId;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.bmConfig;

import java.io.*;

public class BlockImpl implements Block, Serializable {
    private static final long serialVersionUID = 1L;
    private Id indexId;
    private int blockSize;
//    private byte[] content;




    private BlockManager bm;

    public BlockImpl(int indexId, BlockManager bm) {
        this.indexId = new BlockId(indexId);
        this.bm = bm;
        blockSize = bmConfig.getBlockSize();
//        content = new byte[blockSize];
//        content = b.clone();
    }


    @Override
    public Id getIndexId() {
        return indexId;
    }

    @Override
    public BlockManager getBlockManager() {
        return bm;
    }

    @Override
    public byte[] read() {
        String blockPath = bmConfig.bmDirPath+"/"+ bm.getBlockManagerId().toString() + "/" + this.indexId.toString()+".data";
        InputStream in = null;
        byte[] res = new byte[blockSize];
        try {
            in = new FileInputStream(blockPath);
            in.read(res);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    @Override
    public int blockSize() {
        return blockSize;
    }
}
