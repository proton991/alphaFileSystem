package com.fudan.File.Impl;

import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.fmConfig;

import java.io.*;
import java.nio.Buffer;


public class FileImpl implements File, Serializable {
    private static final long serialVersionUID = 1L;
    private FileManager fm;
    private Id fileId;
    private FileMeta fileMeta;
    private long position = 0;


    public FileImpl(Id fileId, FileManager fm) {
        this.fileId = fileId;

        this.fm = fm;

        //新建fileMeta
        this.fileMeta = new FileMeta(this);
    }


    @Override
    public Id getFileId() {
        return fileId;
    }

    @Override
    public FileManager getFileManager() {
        return fm;
    }

    @Override
    public byte[] read(long length) {
        try {
            return this.fileMeta.read(length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(byte[] b) {
        fileMeta.write2(b);
        FileWriter fw = null;

        try {
            fw = new FileWriter(fmConfig.fmDirPath + "/" + fm.getId().toString() + "/" + fileId.toString() + ".txt");
//            BufferedWriter bw = new BufferedWriter(fw);
            String str = fileMeta.toString();
            java.io.File file = new java.io.File(fmConfig.fmDirPath + "/" + fm.getId().toString() + "/" + fileId.toString() + ".txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
//            bw.write(str);
            ps.println(str);
            ps.close();
        } catch (IOException e) {
        }
    }

    @Override
    public FileMeta getFileMeta() {
        return this.fileMeta;
    }

    @Override
    public void setFileMeta(FileMeta meta) {

        this.fileMeta = meta;
    }

    @Override
    public long move(long offset, int where) {
        //移动文件指针时进行限制，空文件禁止移动指针
        if(fileMeta.getFileSize() == 0){
            return 0;
        }
        if (where == File.MOVE_CURR)
            this.position += offset;
        if (where == File.MOVE_HEAD)
            this.position = offset;
        if (where == File.MOVE_TAIL)
            this.position = fileMeta.getFileSize();
        return this.position;
    }

    @Override
    public void close() {
        this.position = 0;

    }

    @Override
    public long size() {
        return this.fileMeta.getFileSize();
    }

    @Override
    public void setSize(long newSize) {
        this.fileMeta.setFileSize(newSize);
    }


}
