package com.ir.project.stemmer;

import org.tartarus.snowball.ext.PorterStemmer;

import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryEnhancer.
 */
public class QueryEnhancer {
    
    /** The stem classes. */
    private Map<String, Set<String>> stemClasses;
    
    /** The porter stemmer. */
    private PorterStemmer porterStemmer;

    /**
     * Instantiates a new query enhancer.
     *
     * @param stemClasses the stem classes
     */
    public QueryEnhancer(Map<String, Set<String>> stemClasses) {
        this.stemClasses = stemClasses;
        this.porterStemmer = new PorterStemmer();
    }

    /**
     * Instantiates a new query enhancer.
     */
    private QueryEnhancer() {
    }


    /**
     * Enhance query.
     *
     * @param query the query
     * @return the string
     */
    public String enhanceQuery(String query) {
        StringBuffer enhancedQuery = new StringBuffer();

        for (String queryWord : query.split(" ")) {
            enhancedQuery.append(queryWord)
                    .append(" ");

            porterStemmer.setCurrent(queryWord);
            porterStemmer.stem();
            String stemClass = porterStemmer.getCurrent();

            Set<String> stemClassWords = stemClasses.get(stemClass);

            if (null != stemClassWords && !stemClassWords.isEmpty()) {
                for (String stemWord : stemClassWords) {
                    enhancedQuery.append(stemWord).append(" ");
                }
            }
        }

        return enhancedQuery.toString().trim();
    }
}
