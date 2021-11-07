package com.ir.project.indexer;

import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class DocMetadataAndIndex.
 */
public class DocMetadataAndIndex {
    
    /** The index. */
    private  Map<String,List<Posting>> index;
    
    /** The document length. */
    private  Map<String, Integer> documentLength;

    /**
     * Instantiates a new doc metadata and index.
     */
    private DocMetadataAndIndex() {
    }

    /**
     * Instantiates a new doc metadata and index.
     *
     * @param index the index
     * @param documentLength the document length
     */
    public DocMetadataAndIndex(Map<String, List<Posting>> index, Map<String, Integer> documentLength) {
        this.index = index;
        this.documentLength = documentLength;
    }

    /**
     * Gets the index.
     *
     * @return the index
     */
    public Map<String, List<Posting>> getIndex() {
        return index;
    }

    /**
     * Sets the index.
     *
     * @param index the index
     */
    public void setIndex(Map<String, List<Posting>> index) {
        this.index = index;
    }

    /**
     * Gets the document length.
     *
     * @return the document length
     */
    public Map<String, Integer> getDocumentLength() {
        return documentLength;
    }

    /**
     * Sets the document length.
     *
     * @param documentLength the document length
     */
    public void setDocumentLength(Map<String, Integer> documentLength) {
        this.documentLength = documentLength;
    }
}
