package com.ir.project.retrievalmodel;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrievedDocument.
 */
public class RetrievedDocument {

    /** The document ID. */
    private String documentID;
    
    /** The score. */
    private double score;

    /**
     * Instantiates a new retrieved document.
     */
    private RetrievedDocument(){}

    /**
     * Instantiates a new retrieved document.
     *
     * @param documentID the document ID
     */
    public RetrievedDocument(String documentID) {

        this.documentID = documentID;
        this.score = 0;
    }

    /**
     * Gets the document ID.
     *
     * @return the document ID
     */
    public String getDocumentID() {
        return documentID;
    }

    /**
     * Instantiates a new retrieved document.
     *
     * @param documentID the document ID
     * @param score the score
     */
    public RetrievedDocument(String documentID, double score) {
        this.documentID = documentID;
        this.score = score;
    }

    /**
     * Sets the document ID.
     *
     * @param documentID the new document ID
     */
    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score the new score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "RetrievedDocument{" +
                "documentID='" + documentID + '\'' +
                ", score=" + score+
                '}';
    }
}
