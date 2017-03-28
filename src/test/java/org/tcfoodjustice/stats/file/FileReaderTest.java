package org.tcfoodjustice.stats.file;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class FileReaderTest {

    private FileReader reader;
    private String fileName = "emailtemplate.html";
    private String badFileName = "emailtemplateasdfasdf.html";

    @Before
    public void setup(){
        reader = new FileReader();
    }
    @Test
    public void testGetFileString() throws Exception {
        String file = reader.getFileAsString(fileName);
        assert file.contains("<span class='rg-hed' style=\"color:#444444; font-weight:bold\">TC Food Justice </span>");
    }
    @Test(expected = RuntimeException.class)
    public void testGetBadFileString() throws Exception {
        String file = reader.getFileAsString(badFileName);
    }
}