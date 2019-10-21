package com.fudan.Utils;

import com.fudan.Block.Block;
import com.fudan.Block.BlockManager;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.bmConfig;

public class alphaHex {
    public static void getHexBlock(Id bmId, Id blockId){
        BlockManager bm = bmConfig.getBlockManager(bmId);
        Block block = bm.getBlock(blockId);
        byte[] content = block.read();
        for (int i = 0; i < content.length; i++) {
            String hex = Integer.toHexString(content[i] & 0XFF);
            if(hex.length() < 2)
                hex = "0" + hex;
            System.out.print(hex + "\t");
            if((i+1) % 16 == 0)
                System.out.print("\n");
        }
    }

    public static void testHex(byte[] content){
        for (int i = 0; i < content.length; i++) {
            String hex = Integer.toHexString(content[i] & 0XFF);
            if(hex.length() < 2)
                hex = "0" + hex;
            System.out.print(hex + "\t");
            if((i+1) % 16 == 0)
                System.out.print("\n");
        }
    }
}
