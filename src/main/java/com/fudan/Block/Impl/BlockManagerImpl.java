package com.fudan.Block.Impl;

import com.fudan.Block.Block;
import com.fudan.Block.BlockManager;
import com.fudan.Indexing.BlockId;
import com.fudan.Indexing.BlockManagerId;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.bmConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BlockManagerImpl implements BlockManager, Serializable {
    private static final long serialVersionUID = 1L;
    private Id bmId;
    private int blockCount;
    private List<Block> blockList;

    public BlockManagerImpl(Id bmId){
        this.bmId = bmId;
        blockList = new ArrayList<>();
    }
    @Override
    public Block getBlock(Id indexId) {
        int index = Integer.valueOf(indexId.toString());
        Block target = blockList.get(index);
        long checkSum = checksum(target.read());

        return blockList.get(index);
    }
    private long checksum(byte[] bs) {
        long h = bs.length;
        for (byte b : bs) {
            long lb = (long) b;
            h = ((h << 1) | (h >>> 63) |
                    ((lb & 0xc3) << 41) | ((lb & 0xa7) << 12)) +
                    lb * 91871341 + 1821349192381L;
        }
        return h;
    }
    @Override
    public Id getBlockManagerId() {
        return bmId;
    }

    @Override
    public Block newBlock(byte[] b) {
        OutputStream os = null;
        Block nblock = new BlockImpl(blockCount, this);
        FileWriter fw = null;
        blockCount += 1;
        try {
            String osPath = bmConfig.bmDirPath+"/"+bmId.toString()+"/"+nblock.getIndexId().toString()+".data";
            os = new FileOutputStream(osPath);
            fw = new FileWriter(bmConfig.bmDirPath+"/"+bmId.toString()+"/"+nblock.getIndexId().toString()+".meta");
            BufferedWriter bw = new BufferedWriter(fw);
            os.write(b);
            bw.write("block size: " + bmConfig.getBlockSize() + "\n" + "checkSum" + checksum(b));
            os.flush();
            bw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        blockList.add(nblock);
        return nblock;
    }
}
