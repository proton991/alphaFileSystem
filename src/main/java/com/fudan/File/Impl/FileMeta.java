package com.fudan.File.Impl;

import com.fudan.Block.Block;
import com.fudan.Block.BlockManager;
import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.Indexing.BlockId;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.bmConfig;
import com.fudan.config.Impl.fmConfig;
import com.fudan.config.sysConfigurer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.*;

public class FileMeta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int blockSize;
    private long fileSize;
    private List<List<Map<Id, Id>>> logicBlock;
    private File f;
    private String metaPath;

    @Override
    public String toString() {
        if (fileSize == 0)
            return "";
        StringBuffer buffer = new StringBuffer();
        String str1 = String.valueOf(blockSize);
        String str2 = String.valueOf(fileSize);
        buffer.append("blockSize: " + str1 + "\n");
        buffer.append("fileSize: " + str2 + "\n");
        for (int i = 0; i < logicBlock.size(); i++) {
            buffer.append(i + ": ");
            List<Map<Id, Id>> tmp = logicBlock.get(i);
            if(tmp == null){
                buffer.append("null");
            }
            else {
                for (int j = 0; j < tmp.size(); j++) {
                    buffer.append(tmp.get(j).toString() + " ");
                }
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public FileMeta(File f) {
        this.fileSize = 0;
        this.blockSize = bmConfig.getBlockSize();
        this.logicBlock = new ArrayList<>();
        this.f = f;
        this.metaPath = fmConfig.fmDirPath + "/" +f.getFileManager().getId().toString() + "/" +f.getFileId().toString() + ".meta";
    }

    public byte[] read(long length) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int readBlocks = (int) (length / blockSize + 1);
        List<Byte> list = new ArrayList<>();

        for (int i = 0; i < readBlocks; i++) {
            if (logicBlock.get(i) != null) {
                Map<Id, Id> map = logicBlock.get(i).get(0);
                for (Map.Entry<Id, Id> entry : map.entrySet()) {
                    Id bmId = entry.getKey();
                    Id blockId = entry.getValue();
                    BlockManager bm = bmConfig.getBlockManager(bmId);
                    Block block = bm.getBlock(blockId);
                    byte[] content = block.read();
                    if (i == readBlocks - 1) {
                        int remain_size = (int) length % blockSize;
                        os.write(content, 0, remain_size);
                        os.flush();
                    } else {
                        os.write(content);
                        os.flush();
                    }
                }
            } else {
                byte[] content = new byte[blockSize];
                if (i == readBlocks - 1) {
                    int remain_size = (int) length % blockSize;
                    os.write(content, 0, remain_size);
                    os.flush();
                } else {
                    os.write(content);
                    os.flush();
                }
            }
        }
        byte[] res = os.toByteArray();
        os.close();
        return res;
    }

    public int getAddBlocks(byte[] b){
        int addBlocks = (b.length % blockSize == 0) ? (b.length % blockSize) : (b.length / blockSize + 1);
        return addBlocks;
    }
    public void write2(byte[] b){
        byte[] zero = new byte[this.blockSize];
        long pos = f.pos();
        ByteBuffer buffer = ByteBuffer.wrap(expandArray(b));
        if(pos + b.length > fileSize){
            setFileSize(pos + b.length);
        }
        int addBlocks = getAddBlocks(b);
        int index = (int) (pos / blockSize);
        List<Map<Id, Id>> indexList = logicBlock.get(index);
        if(indexList != null){
            for (Map.Entry<Id, Id> entry : indexList.get(0).entrySet()) {
                Id bmId = entry.getKey();
                Id blockId = entry.getValue();
                BlockManager bm = bmConfig.getBlockManager(bmId);
                Block block = bm.getBlock(blockId);
                byte[] content = block.read();
                byte[] concatArr = concat(content, b, (int) (pos % blockSize));
                concatArr = expandArray(concatArr);
                buffer = ByteBuffer.wrap(concatArr);
                addBlocks = getAddBlocks(concatArr);
            }
        }
        for (int i = index; i < index + addBlocks; i++) {
            BlockManager bm = bmConfig.getBlockManager();
            byte[] writeBytes = new byte[this.blockSize];
            buffer.get(writeBytes, 0, this.blockSize);
            List<Map<Id, Id>> list =  new ArrayList<>();
            for (int j = 0; j < sysConfigurer.backupCount; j++) {
                if (Arrays.equals(writeBytes, zero)){
                    logicBlock.set(i, null);
                    break;
                }
                else {
                    Block addBlock = bm.newBlock(writeBytes);
                    Map<Id, Id> map = new HashMap<>();
                    //暂时没有实现备份
                    map.put(bm.getBlockManagerId(), addBlock.getIndexId());
                    list.add(map);
                }
            }
            logicBlock.set(i, list);
        }
    }


    public void write(byte[] b) {
        //判断数组
        byte[] zero = new byte[this.blockSize];

        long pos = f.pos();
        //计算需要变更的block的个数
        int addBlocks = b.length / blockSize + 1;
        byte[] newArr = new byte[addBlocks * blockSize];
        System.arraycopy(b, 0, newArr, 0, b.length);
        ByteBuffer buffer = ByteBuffer.wrap(newArr);

        //分情况讨论
        //1.空文件/文件不空但是文件指针在文件尾部，写入时直接分配相应个数的block然后将byte写入block
        if (fileSize == 0 || pos == fileSize) {
            for (int i = 0; i < addBlocks; i++) {
                BlockManager bm = bmConfig.getBlockManager();
                byte[] writeBytes = new byte[this.blockSize];
                buffer.get(writeBytes, 0, this.blockSize);
                if (Arrays.equals(writeBytes, zero))
                    logicBlock.add(null);
                else {
                    Block addBlock = bm.newBlock(writeBytes);
                    Map<Id, Id> map = new HashMap<>();
                    //暂时没有实现备份
                    map.put(bm.getBlockManagerId(), addBlock.getIndexId());
                    List<Map<Id, Id>> list = new ArrayList<>();
                    list.add(map);
                    logicBlock.add(list);
                }
            }
            fileSize += b.length;
        }

        //2.position < fileSize 指针位于头和尾之间，此时会出现覆盖的情况
        else if (pos < fileSize) {
            int origin_size = logicBlock.size();
            //定位到需要替换的block
            int id = (int) (pos / blockSize);
            for (int i = 0; i < addBlocks; i++) {
                if (i + id > origin_size - 1) {
                    BlockManager bm = bmConfig.getBlockManager();
                    byte[] writeBytes = new byte[this.blockSize];
                    //代码和第一中情况相同
                    buffer.get(writeBytes, 0, this.blockSize);
                    if (Arrays.equals(writeBytes, zero))
                        logicBlock.add(null);
                    else {
                        Block addBlock = bm.newBlock(writeBytes);
                        Map<Id, Id> map = new HashMap<>();
                        //暂时没有实现备份
                        map.put(bm.getBlockManagerId(), addBlock.getIndexId());
                        List<Map<Id, Id>> list = new ArrayList<>();
                        list.add(map);
                        logicBlock.add(list);
                    }

                } else {
                    BlockManager bm = bmConfig.getBlockManager();
                    byte[] writeBytes = new byte[this.blockSize];
                    //代码和第一中情况相同
                    buffer.get(writeBytes, 0, this.blockSize);
                    Block addBlock = bm.newBlock(writeBytes);
                    //写入内容全为0，不保存block
                    if (Arrays.equals(writeBytes, zero))
                        logicBlock.set(i, null);
                    else {
                        Map<Id, Id> map = new HashMap<>();
                        //暂时没有实现备份
                        map.put(bm.getBlockManagerId(), addBlock.getIndexId());
                        List<Map<Id, Id>> list = new ArrayList<>();
                        list.add(map);
                        logicBlock.set(i + id, list);
                    }
                }
            }
            //需要减去一个blockSize
            fileSize += b.length - blockSize;
        } else {
            //3. pos > fileSize 指针移动到文件外 此时应不予写入
        }
        //写完以后移动文件指针到文件尾部
        f.move(b.length, File.MOVE_TAIL);
    }

    public byte[] concat(byte[] a, byte[] b, int pos){
        byte[] a1 = Arrays.copyOfRange(a, 0, pos);
        byte[] res = new byte[a1.length + b.length];
        System.arraycopy(a1, 0, res, 0, a1.length);
        System.arraycopy(b, 0, res, a1.length, b.length);
        return res;
    }
    public byte[] expandArray(byte[] b){
        int blocks = b.length / blockSize + 1;
        byte[] newArr = new byte[blocks * blockSize];
        System.arraycopy(b, 0, newArr, 0, b.length);
        return newArr;
    }
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long newSize) {
        if(newSize > fileSize){
            int addBlocks = (int) ((newSize - fileSize) / blockSize + 1);
            for (int i = 0; i < addBlocks; i++) {
                this.logicBlock.add(null);
            }
        }
        else {
            int index = (int) (newSize / blockSize);
            for (int i = index + 1; i < logicBlock.size(); i++) {
                logicBlock.remove(i);
            }
        }
        this.fileSize = newSize;
    }
}
