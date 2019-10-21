package com.fudan.File.Impl;

import com.fudan.Utils.alphaCopy;
import com.fudan.Utils.alphaHex;
import com.fudan.config.Impl.fmConfig;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static org.junit.Assert.*;

public class FileImplTest {
    @Test
    public void FileImpl() throws IOException {
        byte[] b = new byte[10];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte)i;
        }
        ByteBuffer buffer = ByteBuffer.wrap(b);
        byte[] b1 = new byte[3];
        buffer.get(b1, 0, 3);
        System.out.println(Arrays.toString(b1));
        byte[] b2 = new byte[4];
        buffer.get(b2, 1, 3);
        System.out.println(Arrays.toString(b2));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(b1);
        os.write(b2);
        System.out.println(Arrays.toString(os.toByteArray()));
        Map<String, String> map = new HashMap<>();
        map.put("bm-18", "22");
        System.out.println(map.keySet().toString());
        Set<String> keySet = map.keySet();
        System.out.println(Arrays.toString(keySet.toArray()));
        System.out.println(keySet.toArray()[0]);
    }
    @Test
    public void getFileId() {
        java.io.File[] fmFiles = new File("/workingDir/serObj/fm/").listFiles();
        for (int i = 0; i < fmFiles.length; i++){
            System.out.println(fmFiles[i].getPath());
        }
    }

    @Test
    public void getFileManager() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add(i);
        }
        System.out.println(list.toString());
        System.out.println(list.subList(0, 3).toString());
    }

    @Test
    public void read() {
        byte[] b = new byte[256];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte)i;
        }
        alphaHex.testHex(b);
    }

    @Test
    public void write() throws IOException {
        File src = new File(fmConfig.fmDirPath +"/fm-0/hello.txt");
        File dst = new File(fmConfig.fmDirPath +"/fm-0/hello-copy.txt");
        alphaCopy.copyFileUsingFileChannels(src, dst);
    }
    public byte[] concat(byte[] a, byte[] b, int pos){
        byte[] a1 = Arrays.copyOfRange(a, 0, pos);
        byte[] res = new byte[a1.length + b.length];
        System.arraycopy(a1, 0, res, 0, a1.length);
        System.arraycopy(b, 0, res, a1.length, b.length);
        return res;
    }
    @Test
    public void move() {
        byte[] a = {1, 2, 3, 4, 5};
        byte[] a1 = {11, 21, 31, 41, 51};
        System.out.println(Arrays.toString(concat(a, a1, 3)));
    }

    @Test
    public void close() {
    }

    @Test
    public void size() {
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(null);
        arr.add(4);
        arr.add(5);

        byte[] b = {0, 0, 0, 0, 0};
        List<String> list = null;
        List<List<String>> list2 = new ArrayList<>();
        list2.add(null);
        System.out.println(list2.get(1));
        byte[] zero = new byte[b.length];
//        System.out.println(Arrays.equals(b, zero));
//        System.out.println(arr.toString());

    }

    @Test
    public void setSize() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] b1 = {1, 2, 3, 4, 5, 6};
        byte[] b2 = {11, 22, 33, 44, 55, 66};
        os.write(b1, 0, 2);
        os.flush();
        System.out.println(os.size());
        os.write(b2);
        os.flush();
        byte[] bb = os.toByteArray();
        System.out.println(os.size());
        System.out.println(Arrays.toString(bb));
    }
}