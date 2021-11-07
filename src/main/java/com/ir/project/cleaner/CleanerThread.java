package com.ir.project.cleaner;

import com.ir.project.utils.Utilities;
import com.sun.istack.internal.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;

// TODO: Auto-generated Javadoc
/**
 * The Class CleanerThread.
 */
public class CleanerThread implements Callable<String> {

    /** The input file path. */
    private String inputFilePath;
    
    /** The out put file path. */
    private String outPutFilePath;


    /**
     * Instantiates a new cleaner thread.
     */
    private CleanerThread() {
    }

    /**
     * Instantiates a new cleaner thread.
     *
     * @param inputFilePath the input file path
     * @param outPutFilePath the out put file path
     */
    public CleanerThread(@NotNull String inputFilePath, @NotNull String outPutFilePath) {
        this.inputFilePath = inputFilePath;
        this.outPutFilePath = outPutFilePath;
    }

    /**
     * Call.
     *
     * @return the string
     * @throws Exception the exception
     */
    public String call() throws Exception {
        File fileToClean = new File(inputFilePath);
        Document doc = Jsoup.parse(fileToClean, "UTF-8");
        Elements elements = doc.body().getElementsByTag("pre");
        StringBuffer cleanedText = new StringBuffer();

        for(Element e: elements) {
            for (String line : e.text().split("\n")) {
                if (line.length() == 0)
                    continue;

                if (isIgnorableLine(line))
                    continue;

                line = Utilities.processedText(line);
                cleanedText.append(line).append("\n");
            }
        }
        writeToFile(cleanedText.toString());
        return outPutFilePath;
    }


    /**
     * Checks if is ignorable line.
     *
     * @param line the line
     * @return true, if is ignorable line
     */
    // if line contains three columns and all of them are digits.
    private boolean isIgnorableLine(String line) {
        String [] lineTokens = line.split("\t");
        if (lineTokens.length != 3)
            return false;

        boolean allNumbers = true;

        for (String token : lineTokens) {
            allNumbers &= token.matches("-?\\d+(\\.\\d+)?");
        }
        return allNumbers;
    }

    /**
     * Write to file.
     *
     * @param content the content
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeToFile(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.outPutFilePath));
        writer.write(content);
        writer.close();
    }
}
