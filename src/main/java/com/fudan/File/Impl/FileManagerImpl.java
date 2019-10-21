package com.fudan.File.Impl;

import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.Indexing.Id;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileManagerImpl implements FileManager, Serializable {
    private static final long serialVersionUID = 1L;
    private List<File> fileList;
    private Id fmId;
    public FileManagerImpl(Id fmId){
        this.fmId = fmId;
        fileList = new ArrayList<>();
    }

    @Override
    public Id getId() {
        return fmId;
    }

    @Override
    public File getFile(Id fileId) {
        for(File f : fileList){
            if(f.getFileId().equals(fileId))
                return f;
        }
        return null;
    }

    @Override
    public File newFile(Id fileId) {
        File newFile = new FileImpl(fileId, this);
        fileList.add(newFile);
        return newFile;
    }

    @Override
    public File copyFile(Id fileId, FileMeta meta) {
        File copyFile = new FileImpl(fileId, this);
        copyFile.setFileMeta(meta);
        fileList.add(copyFile);
        return null;
    }
}
