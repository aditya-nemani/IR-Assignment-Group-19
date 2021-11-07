package com.ir.project.evaluation;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PrecisionRecallTable.
 */
public class PrecisionRecallTable {
    
    /** The precision recall list. */
    List<PrecisionAndRecall> precisionRecallList;
    
    /** The average precision. */
    private Double averagePrecision;

    /**
     * Instantiates a new precision recall table.
     */
    private PrecisionRecallTable() {

    }

    /**
     * Instantiates a new precision recall table.
     *
     * @param precisionRecallList the precision recall list
     */
    public PrecisionRecallTable(List<PrecisionAndRecall> precisionRecallList) {
        this.precisionRecallList = precisionRecallList;
        calculateAveragePrecision();
    }

    /**
     * Calculate average precision.
     */
    private void calculateAveragePrecision() {
        int totalRelevantDocs = 0;
        double totalPrecision = 0.0D;
        for (PrecisionAndRecall precisionAndRecall : precisionRecallList) {
            if (precisionAndRecall.isRelevant()) {
                totalPrecision+=precisionAndRecall.getPrecision();
                totalRelevantDocs++;
            }
        }
        this.averagePrecision = totalPrecision/totalRelevantDocs;
    }

    /**
     * Gets the precision recall list.
     *
     * @return the precision recall list
     */
    public List<PrecisionAndRecall> getPrecisionRecallList() {
        return precisionRecallList;
    }

    /**
     * Sets the precision recall list.
     *
     * @param precisionRecallList the new precision recall list
     */
    private void setPrecisionRecallList(List<PrecisionAndRecall> precisionRecallList) {
        this.precisionRecallList = precisionRecallList;
    }

    /**
     * Gets the precision at K.
     *
     * @param k the k
     * @return the precision at K
     */
    public Double getPrecisionAtK(int k) {
        return this.precisionRecallList.get(k).getPrecision();
    }

    /**
     * Gets the reciprocal rank.
     *
     * @return the reciprocal rank
     */
    public Double getReciprocalRank() {
        if (null == precisionRecallList || precisionRecallList.isEmpty()){
            return 0.0D;
        }

        for (int i = 0; i < precisionRecallList.size(); i++) {
            if (precisionRecallList.get(i).isRelevant()) {
                return 1.0D/(i+1);
            }
        }

        return 0.0D;
    }

    /**
     * Gets the average precision.
     *
     * @return the average precision
     */
    public Double getAveragePrecision() {
        return averagePrecision;
    }

    /**
     * Sets the average precision.
     *
     * @param averagePrecision the new average precision
     */
    private void setAveragePrecision(Double averagePrecision) {
        this.averagePrecision = averagePrecision;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuffer table = new StringBuffer();
        table.append("---------------------------------------------");
        table.append(System.getProperty("line.separator"));

        for (PrecisionAndRecall precisionAndRecall : precisionRecallList) {
            table.append(precisionAndRecall.toString())
                    .append(System.getProperty("line.separator"));
        }
        table.append("---------------------------------------------");
        table.append(System.getProperty("line.separator"));
        table.append("Precision at K = 5 :")
                .append(getPrecisionAtK(5))
                .append(System.getProperty("line.separator"));
        table.append("Precision at K = 20 :")
                .append(getPrecisionAtK(20))
                .append(System.getProperty("line.separator"));
        table.append("---------------------------------------------");
        table.append(System.getProperty("line.separator"));
        return table.toString();
    }
}
