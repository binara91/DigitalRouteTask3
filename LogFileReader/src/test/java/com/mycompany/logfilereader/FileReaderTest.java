package com.mycompany.logfilereader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @DisplayName("Test Unavailable Server Log")
    @Test
    void readUnavailableFile() {
        assertEquals(false, FileReader.readFile(new File("Test.log")));
    }

    @DisplayName("Test available Server Log")
    @Test
    void readAvailableFile() {
        assertEquals(true, FileReader.readFile(new File("ServerLog.log")));
    }
}
