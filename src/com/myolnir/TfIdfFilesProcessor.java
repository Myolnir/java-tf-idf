package com.myolnir;

import com.myolnir.model.TfIdfFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class TfIdfFilesProcessor {

    private static TfIdfFilesProcessor instance;

    private TfIdfFilesProcessor(){}

    static synchronized TfIdfFilesProcessor getInstance(){
        if(instance == null){
            instance = new TfIdfFilesProcessor();
        }
        return instance;
    }

    /**
     * Receives a list of files and for each read it, extract its content to a List and return a List of files to be
     * processed
     * @param filesInFolder Files to read
     * @return List of TfIdfFile with file content.
     */
    List<TfIdfFile> readFileAndExtractItsInfo(List<File> filesInFolder) {
        List<TfIdfFile> documents = new ArrayList<>();
        for (File file : filesInFolder) {
            try {
                List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(file.getAbsolutePath())));
                documents.add(new TfIdfFile(lines, file.getName()));
            }
            catch (IOException e) {
                System.out.println("File not exists");
            }
        }
        return documents;
    }

    /**
     * Retrieves a directory and return the files with txt extension that are in the first level of it.
     * @param directory to retrieve the files
     * @return List of files with txt extension
     * @throws IOException
     */
    List<File> retrieveFilesIntoFolder(String directory) throws IOException {
        return Files.list(Paths.get(directory))
                .filter(file -> file.getFileName().toString().endsWith(".txt"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    List<TfIdfFile> processTdIdfAlgorithm(List<TfIdfFile> documents, String terms) {
        TfIdfCalculator calculator = new TfIdfCalculator();
        List<TfIdfFile> result = new ArrayList<>();
        for (TfIdfFile document : documents) {
            double tfidf = calculator.tfIdf(document.getFileContent(), documents, terms);
            result.add(new TfIdfFile(document.getFileContent(), document.getFileName(), tfidf));
        }
        return result;
    }
}
