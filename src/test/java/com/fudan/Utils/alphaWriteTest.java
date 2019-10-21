package com.fudan.Utils;

import com.fudan.Indexing.FileId;
import com.fudan.config.sysConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class alphaWriteTest {
    private sysConfigurer configurer = null;

    @Before
    public void _init() throws IOException, ClassNotFoundException {
        configurer = new sysConfigurer();
        configurer.initialize();
    }
    @Test
    public void write() {
        byte[] bytes = new byte[300];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        alphaWrite.write(new FileId("test1.txt"), bytes, 3);
        alphaCat.retrieveOriginalFile(new FileId("test1.txt"));
    }
    @After
    public void _save() throws IOException {
        configurer.serialize();
    }

}