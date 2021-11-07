package com.ir.project.retrievalmodel;

import com.ir.project.utils.SearchQuery;
import javafx.util.Pair;

import javax.validation.constraints.NotNull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrievalTask.
 */
public class RetrievalTask implements Callable<Pair<SearchQuery, List<RetrievedDocument>>>{

    /** The retrieval model. */
    private RetrievalModel retrievalModel;
    
    /** The query. */
    private SearchQuery query;
    
    /** The out folder. */
    private String outFolder;
    
    /** The generate snippet. */
    private boolean generateSnippet;
    
    /** The snippet folder. */
    private String snippetFolder;
    
    /** The system name. */
    private String systemName; // represents the RUN e.g. NoStopNoStem
    
    /** The literal. */
    private String literal;


    /**
     * Instantiates a new retrieval task.
     *
     * @param retrievalModel the retrieval model
     * @param query the query
     * @param outFileName the out file name
     * @param snippetDir the snippet dir
     * @param systemName the system name
     * @param generateSnippet the generate snippet
     */
    public RetrievalTask(@NotNull RetrievalModel retrievalModel, @NotNull SearchQuery query,
                         @NotNull String outFileName,@NotNull String snippetDir, @NotNull String systemName, boolean generateSnippet) {
        this.retrievalModel = retrievalModel;
        this.query = query;
        this.systemName =  systemName;
        this.outFolder = outFileName+ retrievalModel.getModelType().name()+ File.separator +systemName+File.separator;
        this.snippetFolder = snippetDir + retrievalModel.getModelType().name()+ File.separator +systemName+File.separator;
        this.literal = "Q0";
        this.generateSnippet = generateSnippet;
    }

    /**
     * Call.
     *
     * @return the pair
     * @throws Exception the exception
     */
    @Override
    public Pair<SearchQuery, List<RetrievedDocument>> call() throws Exception {
        long start = System.currentTimeMillis();
        List<RetrievedDocument> retrievedDocumentList = retrievalModel.search(this.query);
        long searchTime = System.currentTimeMillis() - start;
        writeToFile(retrievedDocumentList,searchTime);
        return new Pair(this.query, retrievedDocumentList);
    }

    /**
     * Write to file.
     *
     * @param retrievedDocumentList the retrieved document list
     * @param searchTime the search time
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeToFile(List<RetrievedDocument> retrievedDocumentList, long searchTime) throws IOException {
        //TODO:

        // Create output and snippet directory
        if (!new File(snippetFolder).isDirectory())
        {
            File dir = new File(snippetFolder);
            dir.mkdirs();
            //System.out.println(snippetFolder+" created! ==========");
        }


        if (!new File(outFolder).isDirectory())
        {
            File dir = new File(outFolder);
            dir.mkdirs();
            //System.out.println(outFolder+" created!");
        }


        // Writing top 100 results with snippets to html files

        FileWriter fw = new FileWriter(outFolder+"QUERY_" + query.getQueryID()+"_results.txt");
        FileWriter htmlfilewriter = new FileWriter(snippetFolder+"QUERY_" + query.getQueryID()+"_snippets.txt");
        BufferedWriter bw= new BufferedWriter(fw);
        BufferedWriter htmlwriter = new BufferedWriter(htmlfilewriter);
        // Write to html file

        htmlwriter.append(snippetPageIntro
                (this.query.getQuery(),retrievalModel.getModelType().name(),this.systemName, searchTime));

        RetrievedDocument rd;
        String docDir = "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "testcollection" +  File.separator + "cleanedcorpus" + File.separator;
        List<String> snippetSentences;

        for (int i = 0; i < retrievedDocumentList.size() && i < 100; i++){
            rd = retrievedDocumentList.get(i);
            htmlwriter.append("\n" +
                            "DocID: "+rd.getDocumentID()+"\n" +
                            "\n");
            // rd.getDocumentID();
            // rd.getScore();
            snippetSentences = new ArrayList<>();
            if (this.generateSnippet) {
                snippetSentences = SnippetGenerator.getSummary(docDir+rd.getDocumentID()+".html_cleaned",this.query.getQuery());
            }


            if(i<100){

                // Write to html file again
                for(String sentence : snippetSentences){
                    htmlwriter.append(sentence);
                    htmlwriter.append("  \n");
                }
                htmlwriter.append("\n");

                bw.append(this.query.getQueryID()+"\t"+this.literal+"\t"+rd.getDocumentID()
                        +"\t"+(i+1)+"\t"+rd.getScore()+"\t\t"+retrievalModel.getModelType().name()
                        +this.systemName);

                if(i<99){
                    bw.append("\n");
                }
                /*

                System.out.println(this.query.getQueryID()+"\t"+this.literal+"\t"+rd.getDocumentID()
                        +"\t"+(i+1)+"\t"+rd.getScore()+"\t\t"+retrievalModel.getModelType().name()
                        +this.systemName+"\r");

                System.out.printf("%-4d %4s %-80s  %-4d   %3.7f  %s%s \n",
                        this.query.getQueryID(),this.literal,rd.getDocumentID(),rd.getScore(),
                        retrievalModel.getModelType().name(),this.systemName);
                */
            }
        }
        htmlwriter.close();
        htmlfilewriter.close();
        bw.close();
        fw.close();
        //System.out.println("\""+retrievalModel.getModelType().name()+"\\"+this.systemName+"\\QUERY_" + query.getQueryID()+"_results.txt\" created!");
    }

    /**
     * Snippet page intro.
     *
     * @param query the query
     * @param model the model
     * @param systemRunName the system run name
     * @param time the time
     * @return the string
     */
    private String snippetPageIntro(String query,String model, String systemRunName, Long time) {
        return (model+"_"+systemRunName+"\n\n"+
                "Query\n"+query+"\n\n"+
                "Top 100 Results ("+time+" millisecs)\n" +
                "\n");
    }
}

