package com.fudan.Indexing;

import java.io.Serializable;

public class FileManagerId implements Id, Serializable {
    private static final long serialVersionUID = 1L;
    private String fmId = "";

    public FileManagerId(String fmId){
        this.fmId = fmId;
    }

    @Override
    public String toString() {
        return fmId;
    }
}
