package com.ir.project.indexer;

// TODO: Auto-generated Javadoc
/**
 * The Class Posting.
 */
public class Posting {
    
    /** The document id. */
    private String documentId;
    
    /** The frequency. */
    private int frequency;

    /**
     * Instantiates a new posting.
     */
    private Posting(){
    }

    /**
     * Instantiates a new posting.
     *
     * @param documentId the document id
     * @param frequency the frequency
     */
    public Posting(String documentId, int frequency) {
        this.documentId = documentId;
        this.frequency = frequency;
    }

    /**
     * Gets the document id.
     *
     * @return the document id
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the document id.
     *
     * @param documentId the new document id
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Gets the frequency.
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets the frequency.
     *
     * @param frequency the new frequency
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Posting{" +
                "documentId='" + documentId + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
