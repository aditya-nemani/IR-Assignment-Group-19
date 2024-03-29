package com.ir.project.cleaner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// TODO: Auto-generated Javadoc
/**
 * The Class Cleaner.
 */
public class Cleaner {

    /** The Constant MAX_THREADS. */
    public static final int MAX_THREADS = 100;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String args[]) {
        String documentFolderPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testcollection" + File.separator + "cacm" + File.separator + "";
        String outputFolderPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testcollection" + File.separator + "cleanedcorpus";
        List<String> cleanedFiles = cleanDocuments(documentFolderPath, outputFolderPath);

        System.out.println("Cleaned " + cleanedFiles.size() + " files.");
    }

    /**
     * Clean documents.
     *
     * @param documentFolderPath the document folder path
     * @param outputFolderPath the output folder path
     * @return the list
     */
    public static List<String> cleanDocuments(String documentFolderPath, String outputFolderPath) {

        if (outputFolderPath.lastIndexOf(File.separator) == outputFolderPath.length() - 1) {
            outputFolderPath = outputFolderPath.substring(0, outputFolderPath.length() - 1);
        }


        File outputFolder = new File(outputFolderPath);

        if (!outputFolder.isDirectory()) {
            System.out.println("Error!! A file with output folder path name exists! path: " + outputFolder.getAbsolutePath() + " " + outputFolder.isDirectory());
            return new ArrayList<String>();
        } else if (!outputFolder.exists()) {
            System.out.println("Error!! A folder doesn't exist!");
            return new ArrayList<String>();
        }
        List<String> cleanedFiles = new ArrayList<String>();
        File documentFolder = new File(documentFolderPath);

        if (documentFolder.isDirectory()) {

            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
            List<Future<String>> futures = new ArrayList<Future<String>>();
            for (File file : documentFolder.listFiles()) {

                if (file.isFile() && file.getName().endsWith(".html")) {
                    CleanerThread thread =
                            new CleanerThread(file.getAbsolutePath(),
                                    outputFolderPath + "" + File.separator + "" + file.getName() + "_cleaned");
                    Future<String> f = executor.submit(thread);
                    futures.add(f);
                }
            }

            executor.shutdown();
            cleanedFiles = pollForCompletion(futures);
        }

        return cleanedFiles;

    }

    /**
     * Poll for completion.
     *
     * @param futures the futures
     * @return the list
     */
    private static List<String> pollForCompletion(List<Future<String>> futures) {
        List<String> cleanedFiles = new ArrayList<String>();
        for (Future<String> f : futures) {
            try {
                String outFile = f.get();
                cleanedFiles.add(outFile);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return cleanedFiles;
    }
}
