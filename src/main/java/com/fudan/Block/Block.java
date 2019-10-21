package com.fudan.Block;

import com.fudan.Indexing.Id;

public interface Block {
    Id getIndexId();

    BlockManager getBlockManager();

    byte[] read();

    int blockSize();
}
