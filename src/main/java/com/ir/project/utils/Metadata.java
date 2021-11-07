package com.ir.project.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


// TODO: Auto-generated Javadoc
/**
 * The Class Metadata.
 */
public class Metadata {
    
    /** The path. */
    private static String path="";
    
    /** The file count. */
    public static int fileCount=0;

    /**
     * Instantiates a new metadata.
     */
    private Metadata() {
    }
    
    /**
     * Average doc len.
     *
     * @return the double
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static double averageDocLen() throws IOException{

        File directory = new File(path);
        File[] fList = directory.listFiles();
        double totalDocLen = 0.0d;
        for (File file : fList){
            if(!file.getName().endsWith(".txt")) continue;
            fileCount++;
            BufferedReader br = new BufferedReader(new FileReader(file));
            totalDocLen += br.toString().length();
        }
        return totalDocLen/fileCount ;
    }
    
    /**
     * Total docs.
     *
     * @return total number of documents
     */
    public static int totalDocs(){
        File directory = new File(path);
        File[] fList = directory.listFiles();
        for (File file : fList){
            if(!file.getName().endsWith(".txt")) continue;
            fileCount++;
        }
        return fileCount;


    }



}
