package com.fudan.Utils;

import com.fudan.Indexing.FileId;
import com.fudan.config.sysConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class alphaCopyTest {
    private sysConfigurer configurer = null;
    @Before
    public void _init() throws IOException, ClassNotFoundException {
        configurer = new sysConfigurer();
        configurer.initialize();
    }
    @Test
    public void copy() throws IOException {
        alphaCopy.copy(new FileId("gow4.png"));
    }
    @After
    public void _save() throws IOException {
        configurer.serialize();
    }
}