package com.fudan.Block;
import com.fudan.Indexing.Id;
public interface BlockManager {
    Block getBlock(Id indexId);

    Id getBlockManagerId();
    Block newBlock(byte[] b);

    default Block newEmptyBlock(int blockSize) {
        return newBlock(new byte[blockSize]);
    }
}
