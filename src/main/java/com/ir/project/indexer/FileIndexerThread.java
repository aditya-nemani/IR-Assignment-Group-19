package com.ir.project.indexer;

import com.ir.project.utils.Utilities;
import javafx.util.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

// TODO: Auto-generated Javadoc
/**
 * The Class FileIndexerThread.
 */
public class FileIndexerThread implements Callable <Pair<String, Map<String, Integer>>> {

    /** The file path. */
    private String filePath;
    
    /** The stop list. */
    private List<String> stopList;

    /**
     * Instantiates a new file indexer thread.
     */
    private FileIndexerThread() {
    }

    /**
     * Instantiates a new file indexer thread.
     *
     * @param filePath the file path
     */
    public FileIndexerThread(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Instantiates a new file indexer thread.
     *
     * @param filePath the file path
     * @param stopList the stop list
     */
    public FileIndexerThread(String filePath, List<String> stopList) {
        this.filePath = filePath;
        this.stopList = stopList;
    }

    /**
     * Call.
     *
     * @return the pair
     * @throws Exception the exception
     */
    public Pair<String, Map<String, Integer>> call() throws Exception {

        Map<String, Integer> wordMap = new HashMap<String, Integer>();
        File file = new File(filePath);

        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            String line = sc.nextLine();

            if (line.length() > 0) {

                for (String word : line.split(Utilities.WHITESPACE)) {
                    word = Utilities.processedWord(word);

                    if (stopList != null && stopList.contains(word))
                        continue;

                    if(!(word.trim().equals("")) && !(word.trim().equals(Utilities.MULTIPLE_WHITESPACES))) {
                        int count = wordMap.containsKey(word) ? wordMap.get(word) : 0;
                        wordMap.put(word, count + 1);
                    }
                }
            }
        }
        sc.close();
        String docId = file.getName().split("\\.")[0];
        return new Pair<String, Map<String, Integer>>(docId,wordMap);
    }

    /**
     * Gets the file path.
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path.
     *
     * @param filePath the new file path
     */
    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the stop list.
     *
     * @return the stop list
     */
    public List<String> getStopList() {
        return stopList;
    }

    /**
     * Sets the stop list.
     *
     * @param stopList the new stop list
     */
    private void setStopList(List<String> stopList) {
        this.stopList = stopList;
    }
}
