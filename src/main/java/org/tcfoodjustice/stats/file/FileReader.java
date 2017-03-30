package org.tcfoodjustice.stats.file;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
@Component
public class FileReader {
    private static final Logger log = Logger.getLogger(FileReader.class);

    /**
     * Method to read in file and retrun as string
     * @param file
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String getFileAsString(String file) throws IOException, URISyntaxException, RuntimeException {
        URL url = getClass().getClassLoader()
                .getResource(file);
        if(url == null){
            //todo- I want to use InvalidArgumentException but gradle is telling me it can't find the dependency?
            //I thought it was shipped with core java.....
            throw new RuntimeException("File does not exist");

        }
        log.debug("File url " + url);

        Path path = Paths.get(url.toURI());

        StringBuilder data = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line).append("\n"));
        lines.close();

        return data.toString();
    }
}
