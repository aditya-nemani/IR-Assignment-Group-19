package com.ir.project.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchQuery.
 */
public class SearchQuery {

    /** The query ID. */
    private int queryID;
    
    /** The query. */
    private String query;

    /**
     * Instantiates a new search query.
     *
     * @param queryID the query ID
     * @param query the query
     */
    public SearchQuery(int queryID,String query){
        this.query = query;
        this.queryID = queryID;
    }

    /**
     * Gets the query ID.
     *
     * @return the query ID
     */
    public int getQueryID() {
        return queryID;
    }

    /**
     * Sets the query ID.
     *
     * @param queryID the new query ID
     */
    public void setQueryID(int queryID) {
        this.queryID = queryID;
    }

    /**
     * Gets the query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the query.
     *
     * @param query the new query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Query1{" +
                "queryID=" + queryID +
                ", query='" + query + '\'' +
                '}';
    }
}
