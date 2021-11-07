package com.ir.project.evaluation;

// TODO: Auto-generated Javadoc
/**
 * The Class PrecisionAndRecall.
 */
public class PrecisionAndRecall {
    
    /** The doc id. */
    private String docId;
    
    /** The precision. */
    private Double precision;
    
    /** The recall. */
    private Double recall;
    
    /** The is relevant. */
    private boolean isRelevant;

    /**
     * Instantiates a new precision and recall.
     */
    private PrecisionAndRecall() {

    }

    /**
     * Instantiates a new precision and recall.
     *
     * @param docId the doc id
     * @param precision the precision
     * @param recall the recall
     * @param isRelevant the is relevant
     */
    public PrecisionAndRecall(String docId, Double precision,
                              Double recall, boolean isRelevant) {
        this.docId = docId;
        this.precision = precision;
        this.recall = recall;
        this.isRelevant = isRelevant;
    }

    /**
     * Gets the precision.
     *
     * @return the precision
     */
    public Double getPrecision() {
        return precision;
    }

    /**
     * Sets the precision.
     *
     * @param precision the new precision
     */
    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    /**
     * Gets the recall.
     *
     * @return the recall
     */
    public Double getRecall() {
        return recall;
    }

    /**
     * Sets the recall.
     *
     * @param recall the new recall
     */
    public void setRecall(Double recall) {
        this.recall = recall;
    }

    /**
     * Checks if is relevant.
     *
     * @return true, if is relevant
     */
    public boolean isRelevant() {
        return isRelevant;
    }

    /**
     * Sets the relevant.
     *
     * @param relevant the new relevant
     */
    public void setRelevant(boolean relevant) {
        isRelevant = relevant;
    }

    /**
     * Gets the doc id.
     *
     * @return the doc id
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Sets the doc id.
     *
     * @param docId the new doc id
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuffer tableEntry = new StringBuffer();
        tableEntry
                .append("| ")
                .append(docId)
                .append(" | \t | ")
                .append(precision)
                .append(" | \t | ")
                .append(recall)
                .append(" | \t | ");
        String out = this.isRelevant? tableEntry.append("R |").toString() : tableEntry.append("NR |").toString();
        return out;
    }
}
