package com.ir.project.evaluation;

import com.ir.project.utils.SearchQuery;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class EvaluationStats.
 */
public class EvaluationStats {

    /** The run model. */
    private String runModel; // just for info.
    
    /** The map. */
    private Double map; // mean average precision;
    
    /** The mrr. */
    private Double mrr;
    
    /** The precision recall table and query map. */
    Map<SearchQuery, PrecisionRecallTable> precisionRecallTableAndQueryMap;

    /**
     * Instantiates a new evaluation stats.
     */
    private EvaluationStats() {
    }

    /**
     * Instantiates a new evaluation stats.
     *
     * @param runModel the run model
     * @param map the map
     * @param mrr the mrr
     * @param precisionRecallTableAndQueryMap the precision recall table and query map
     */
    public EvaluationStats(@NotNull String runModel, @NotNull Double map,
                           @NotNull Double mrr,
                           @NotNull Map<SearchQuery, PrecisionRecallTable> precisionRecallTableAndQueryMap) {
        this.runModel = runModel;
        this.map = map;
        this.mrr = mrr;
        this.precisionRecallTableAndQueryMap = precisionRecallTableAndQueryMap;
    }

    /**
     * Gets the run model.
     *
     * @return the run model
     */
    public String getRunModel() {
        return runModel;
    }

    /**
     * Sets the run model.
     *
     * @param runModel the new run model
     */
    public void setRunModel(String runModel) {
        this.runModel = runModel;
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Double getMap() {
        return map;
    }

    /**
     * Sets the map.
     *
     * @param map the new map
     */
    private void setMap(Double map) {
        this.map = map;
    }

    /**
     * Gets the mrr.
     *
     * @return the mrr
     */
    public Double getMrr() {
        return mrr;
    }

    /**
     * Sets the mrr.
     *
     * @param mrr the new mrr
     */
    private void setMrr(Double mrr) {
        this.mrr = mrr;
    }

    /**
     * Gets the precision recall table and query map.
     *
     * @return the precision recall table and query map
     */
    public Map<SearchQuery, PrecisionRecallTable> getPrecisionRecallTableAndQueryMap() {
        return precisionRecallTableAndQueryMap;
    }

    /**
     * Sets the precision recall table and query map.
     *
     * @param precisionRecallTableAndQueryMap the precision recall table and query map
     */
    private void setPrecisionRecallTableAndQueryMap(Map<SearchQuery, PrecisionRecallTable> precisionRecallTableAndQueryMap) {
        this.precisionRecallTableAndQueryMap = precisionRecallTableAndQueryMap;
    }

    /**
     * Write precision tables to folder.
     *
     * @param outputFolderPath the output folder path
     */
    public void writePrecisionTablesToFolder(String outputFolderPath) {

        if (outputFolderPath.charAt(outputFolderPath.length() - 1 ) != File.separator.charAt(0))
            outputFolderPath = outputFolderPath + "/";

        if (!new File(outputFolderPath).exists()) {
            new File(outputFolderPath).mkdirs();
        }
        for (Map.Entry<SearchQuery, PrecisionRecallTable> entry : precisionRecallTableAndQueryMap.entrySet()) {
            String outFilePath = outputFolderPath + entry.getKey().getQueryID() + this.runModel + ".table";
            StringBuffer content = new StringBuffer();
            content
                    .append("Query ID: ")
                    .append(entry.getKey().getQueryID())
                    .append(System.getProperty("line.separator"))
                    .append("Query : ")
                    .append(entry.getKey().getQuery())
                    .append(System.getProperty("line.separator"))
                    .append(entry.getValue().toString());

            try {
                Files.write(Paths.get(outFilePath), content.toString().getBytes());
            } catch (IOException e) {
                 e.printStackTrace();
            }
         }
    }

}
