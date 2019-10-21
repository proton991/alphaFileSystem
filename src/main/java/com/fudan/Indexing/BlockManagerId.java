package com.fudan.Indexing;


import java.io.Serializable;

public class BlockManagerId implements Id, Serializable {
    private static final long serialVersionUID = 1L;
    private String bmId = "";

    public BlockManagerId(String bmId){
        this.bmId = bmId;
    }



    @Override
    public String toString() {
        return bmId;
    }
}
