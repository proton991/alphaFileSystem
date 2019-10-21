package com.fudan.File;


import com.fudan.File.Impl.FileMeta;
import com.fudan.Indexing.Id;

public interface FileManager {
    Id getId();
    File getFile(Id fileId);

    File newFile(Id fileId);

    File copyFile(Id fileId, FileMeta meta);
}
