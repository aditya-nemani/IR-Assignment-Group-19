package com.ir.project.retrievalmodel;

import com.ir.project.utils.SearchQuery;

import java.io.IOException;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface RetrievalModel.
 */
public interface RetrievalModel {

    /**
     * Gets the model type.
     *
     * @return the model type
     */
    RetrievalModelType getModelType();

    /**
     * Search.
     *
     * @param query the query
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    List<RetrievedDocument> search(SearchQuery query) throws IOException;

}
