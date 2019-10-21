package com.fudan.File;
import com.fudan.File.Impl.FileMeta;
import com.fudan.Indexing.Id;
public interface File {
    int MOVE_CURR = 0;
    int MOVE_HEAD = 1;
    int MOVE_TAIL = 2;

    Id getFileId();

    FileManager getFileManager();

    byte[] read(long length);

    void write(byte[] b);

    default long pos() {
        return move(0, MOVE_CURR);
    }

    FileMeta getFileMeta();

    void setFileMeta(FileMeta meta);
    long move(long offset, int where);

    void close();

    long size();

    void setSize(long newSize);

}
