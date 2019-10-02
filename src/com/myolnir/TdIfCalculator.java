package com.myolnir;

import com.myolnir.model.TdIdfFile;

import java.util.List;

public class TdIfCalculator {

    public double tfIdf(List<String> doc, List<TdIdfFile> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }

    private double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    private double idf(List<TdIdfFile> docs, String term) {
        double n = 0;
        for (TdIdfFile doc : docs) {
            for (String word : doc.getFileContent()) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

}
