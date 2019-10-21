package com.fudan.Utils;

import com.fudan.config.sysConfigurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class alphaSaveTest {
    private sysConfigurer configurer = null;

    @Before
    public void _init() throws IOException, ClassNotFoundException {
        configurer = new sysConfigurer();
        configurer.initialize();
    }

    @Test
    public void saveFile() throws IOException {
        alphaSave.saveFile(sysConfigurer.test_dir_path+"test1.txt");
    }

    @After
    public void _save() throws IOException {
        configurer.serialize();
    }
}