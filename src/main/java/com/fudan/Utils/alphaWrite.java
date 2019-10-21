package com.fudan.Utils;

import com.fudan.File.File;
import com.fudan.File.FileManager;
import com.fudan.File.Impl.FileImpl;
import com.fudan.Indexing.Id;
import com.fudan.config.Impl.fmConfig;

import java.util.List;

public class alphaWrite {

    public static void write(Id fileId, byte[] writeBytes, long off){
        List<FileManager> file = fmConfig.getFmList();
        File f = null;
        for(FileManager tmp : file){
            if(tmp.getFile(fileId) != null){
                f = tmp.getFile(fileId);
            }
        }
        f.move(off, File.MOVE_CURR);
        f.write(writeBytes);
    }
}
