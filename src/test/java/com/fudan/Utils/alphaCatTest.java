package com.fudan.Utils;

import com.fudan.Indexing.FileId;
import com.fudan.config.sysConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class alphaCatTest {
    private sysConfigurer configurer = null;

    @Before
    public void _init() throws IOException, ClassNotFoundException {
        configurer = new sysConfigurer();
        configurer.initialize();
    }
    @Test
    public void getOriginalFile() throws IOException, ClassNotFoundException {
        alphaCat.retrieveOriginalFile(new FileId("test1.txt"));
    }
    @Test
    public void getFileData() throws IOException, ClassNotFoundException {
        byte[] test1_bytes = alphaCat.getFileData(new FileId("gow4.png"));
        System.out.println(Arrays.toString(test1_bytes));
    }

    @After
    public void _save() throws IOException {
        configurer.serialize();
    }


}