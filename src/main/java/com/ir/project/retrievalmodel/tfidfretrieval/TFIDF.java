// =========================
// ADD RELEVANCE INFORMATION
// =========================

// create getter - setters

package com.ir.project.retrievalmodel.tfidfretrieval;

import java.util.*;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ir.project.indexer.DocMetadataAndIndex;
import com.ir.project.indexer.Posting;

import com.ir.project.retrievalmodel.RetrievalModel;
import com.ir.project.retrievalmodel.RetrievalModelType;
import com.ir.project.retrievalmodel.RetrievedDocument;
import com.ir.project.utils.*;


// TODO: Auto-generated Javadoc
/**
 * The Class TFIDF.
 */
public class TFIDF implements RetrievalModel {

    /** The total docs. */
    private int totalDocs;
    
    /** The avg length. */
    private double avgLength;
    
    /** The inverted index. */
    private Map<String, List<Posting>> invertedIndex;
    
    /** The doc lengths. */
    private Map<String, Integer> docLengths;

    /**
     * Instantiates a new tfidf.
     *
     * @param metadataAndIndex the metadata and index
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public TFIDF(DocMetadataAndIndex metadataAndIndex)throws IOException {

        invertedIndex = metadataAndIndex.getIndex();
        docLengths = metadataAndIndex.getDocumentLength();
        avgLength = this.avgDocLength();
        totalDocs = docLengths.size();
    }

    /**
     * Avg doc length.
     *
     * @return the double
     */
    ///Method to find the average doc length
    private double avgDocLength(){
        double totalLength = 0.0d;
        for(String doc : this.docLengths.keySet()) {
            totalLength += this.docLengths.get(doc);
        }
        return totalLength/this.docLengths.size();
    }

    /**
     * Gets the model type.
     *
     * @return the model type
     */
    @Override
    public RetrievalModelType getModelType() {
        return RetrievalModelType.TFIDF;
    }

    /**
     * Search.
     *
     * @param query the query
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<RetrievedDocument> search(SearchQuery query) throws IOException {
        List<RetrievedDocument> retrievedDocs = new ArrayList<>();
        List<String> queryTerms;
        queryTerms = Utilities.getQueryTerms(query.getQuery());
        //queryTerms .forEach(q->System.out.println("QUERY TERM: "+q));

        // Add all docs to retrievedDocsList
        for (Map.Entry<String, Integer> doc : docLengths.entrySet()) {
            retrievedDocs.add(new RetrievedDocument(doc.getKey()));
        }

        // Calculate Query Likelihood probability(score) for all documents
        // (one document at a time for a given query)

        for(RetrievedDocument rd : retrievedDocs){
            rd.setScore(calculateTFIDFScore(queryTerms,rd.getDocumentID()));
        }

        // sort the docs in decreasing order of score
        Collections.sort(retrievedDocs, (RetrievedDocument a, RetrievedDocument b) -> Double.compare(b.getScore(), a.getScore()));
        return retrievedDocs;

    }

    /**
     * Calculate TFIDF score.
     *
     * @param queryTerms the query terms
     * @param docID the doc ID
     * @return the double
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private double calculateTFIDFScore(List<String> queryTerms, String docID) throws IOException {

        double tfidfScore = 0;
        for(String term : queryTerms){
            tfidfScore = tfidfScore + termScore(term,docID,getQueryTermFreq(term, queryTerms));
        }
        return tfidfScore;
    }

    /**
     * Term score.
     *
     * @param term the term
     * @param docID the doc ID
     * @param queryTermFreq the query term freq
     * @return the double
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private double termScore(String term, String docID, double queryTermFreq) throws IOException {


        double tf = getTermDocumentFrequency(term,docID); // term frequency of term in given doc
        //System.out.println("TF(fi)   : "+f);
        //System.out.println("% of doc : "+ (f*100)/DocLength.getDocLength(rdoc));
        //System.out.println("QTF(qfi): "+qf);

        double ni = computeDocFreq(term);
        double N = totalDocs;

        double idf = (Math.log(N/ni));
        double score = tf*idf;

        return score;
    }

    /**
     * Compute doc freq.
     *
     * @param term the term
     * @return the double
     */
    private double computeDocFreq(String term) {
        if(invertedIndex.containsKey(term)){
            return invertedIndex.get(term).size();
        }
        else{
            return 0;
        }

    }


    /**
     * Gets the term document frequency.
     *
     * @param term the term
     * @param docID the doc ID
     * @return the term document frequency
     */
    private double getTermDocumentFrequency(String term, String docID) {
        if(invertedIndex.get(term)!=null){
            List<Posting> indexList = invertedIndex.get(term);
            double termFrequency = 0;
            for (Posting p  : indexList) {
                if(p.getDocumentId().equals(docID)){
                    return p.getFrequency();
                }
            }
            return termFrequency;
        }
        else{
            //System.out.println("TERM not in inverted index - pre processing mis-match : "+term);
            return 0;
        }

    }

    /**
     * Gets the query term freq.
     *
     * @param queryTerm the query term
     * @param queryTerms the query terms
     * @return the query term freq
     */
    private static int getQueryTermFreq(String queryTerm, List<String> queryTerms) {
        int freq = 0;
        for(String s: queryTerms){
            if(s.equals(queryTerm)){
                freq+=1;
            }
        }
        //System.out.println("QTF(qfi)  : "+freq);
        return freq;
    }



    //Main method to run BM25

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException {

        // ====================
        // Test query Search
        // ====================

        String queryText = "What articles exist which deal with TSS (Time Sharing System), an\n" +
                "operating system for IBM computers?";
        int queryID =1;
        SearchQuery testQuery = new SearchQuery(queryID,queryText);

        String indexPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "invertedindex" + File.separator + "metadata.json";
        // load previously created inverted index and metadata
        ObjectMapper om = new ObjectMapper();
        try {
            DocMetadataAndIndex metadataAndIndex = om.readValue(new File(indexPath), DocMetadataAndIndex.class);
            TFIDF test = new TFIDF(metadataAndIndex);
            Utilities.displayRetrieverdDoc(test.search(testQuery));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);

        }
    }



}
