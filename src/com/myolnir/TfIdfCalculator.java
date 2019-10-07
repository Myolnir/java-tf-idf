package com.myolnir;

import com.myolnir.model.TfIdfFile;

import java.util.List;

class TfIdfCalculator {

    double tfIdf(List<String> doc, List<TfIdfFile> docs, String term) {
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

    private double idf(List<TfIdfFile> docs, String term) {
        double n = 0;
        for (TfIdfFile doc : docs) {
            for (String word : doc.getFileContent()) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log((double)docs.size()/n);
    }

}