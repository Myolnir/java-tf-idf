package com.myolnir.model;

import java.util.List;

public class TfIdfFile implements Comparable<TfIdfFile>{
    private List<String> fileContent;
    private String fileName;
    private Double idf;

    public TfIdfFile(List<String> fileContent, String fileName) {
        this.fileContent = fileContent;
        this.fileName = fileName;
    }

    public TfIdfFile(List<String> fileContent, String fileName, double idf) {
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.idf = idf;
    }

    public List<String> getFileContent() {
        return fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public Double getIdf() {
        return idf;
    }

    @Override
    public int compareTo(TfIdfFile file) {
        return getIdf().compareTo(file.getIdf());
    }

}
