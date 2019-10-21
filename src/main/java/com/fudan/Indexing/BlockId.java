package com.fudan.Indexing;

import java.io.Serializable;

public class BlockId implements Id, Serializable {
    private static final long serialVersionUID = 1L;
    private int blockId;
    public BlockId(int blockId){
        this.blockId = blockId;
    }

    public boolean equals(Object o){
        if(o instanceof BlockId){
            if(((BlockId) o).blockId == this.blockId)
                return true;
            else
                return false;
        }
        return false;
    }


    @Override
    public String toString() {
        return String.valueOf(blockId);
    }
}
