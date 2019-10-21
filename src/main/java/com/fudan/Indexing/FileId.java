package com.fudan.Indexing;

import java.io.Serializable;

public class FileId implements Id, Serializable {
    private static final long serialVersionUID = 1L;
    private String fileId = "";
    public FileId(String fileName){
        this.fileId = fileName;
    }

    public boolean equals(Object o){
        if(o instanceof FileId){
            if(((FileId) o).fileId.equals(this.fileId))
                return true;
            else
                return false;
        }
        return false;
    }



    @Override
    public String toString() {
        return fileId;
    }
}
