package com.fudan.Block.Impl;

import com.fudan.Block.Block;
import com.fudan.Block.BlockManager;
import com.fudan.File.FileManager;
import com.fudan.Indexing.BlockId;
import com.fudan.Indexing.BlockManagerId;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.bmConfig;
import com.fudan.config.Impl.fmConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BlockManagerImplTest {

    @Before
    public void _init(){
    }
    @Test
    public void getBlock() {
        byte[] b = new byte[10];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte)i;
        }
        BlockManager bm1 = bmConfig.getBlockManager();
        bm1.newBlock(b);
        byte[] readByte = bm1.getBlock(new BlockId(0)).read();
        System.out.println(Arrays.toString(readByte));

    }

    @Test
    public void getBlockManagerId() {
        String meta = "";
        StringBuffer bf = new StringBuffer();
        List<List<Map<Id, Id>>> logicBlock = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Id bmId = new BlockManagerId("bm-"+i);
            Id bId = new BlockId(i);
            Id bmId1 = new BlockManagerId("bm-"+(i+1));
            Id bId1 = new BlockId(i+1);
            Map<Id, Id> map = new HashMap<>();
            map.put(bmId, bId);
            map.put(bmId1, bId1);
            List<Map<Id, Id>> tmp = new ArrayList<>();
            tmp.add(map);
            logicBlock.add(tmp);
            String str = tmp.toString();

            bf.append(str.replace('=', '.') + "\n");
        }
        System.out.println(bf.toString());
    }

    @Test
    public void newBlock() {
        byte[] b1 = {1, 0, 1, 1, 1, 0};
        byte[] b2 = new byte[10];
        System.arraycopy(b1, 0, b2, 0, b1.length);
        System.out.println(Arrays.toString(b2));
    }
}