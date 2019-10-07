package com.myolnir;

import com.myolnir.model.TfIdfFile;

import java.io.File;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class TfIdf {

    private static TfIdfFilesProcessor processor = TfIdfFilesProcessor.getInstance();

    public static void main(String[] args) throws IOException,
            InterruptedException {
        Scanner s=new Scanner(System.in);
        System.out.print("Enter directory where documents will be written: ");
        String directory = s.nextLine();
        System.out.print("Enter terms TT to be analyzed: ");
        String terms = s.nextLine();
        System.out.print("Enter the count of top results to show: ");
        String count = s.nextLine();
        System.out.print("Enter the period of top results to show: ");
        String period = s.nextLine();


        Path folder = Paths.get(directory);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        boolean valid;
        processFiles(directory, terms, Integer.parseInt(count));
        do {
            WatchKey watchKey = watchService.take();

            for (WatchEvent event : watchKey.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                    String fileName = event.context().toString();
                    System.out.println("A new file has been created:" + fileName);
                    processFiles(directory, terms, Integer.parseInt(count));
                }
            }
            valid = watchKey.reset();

        } while (valid);

    }

    /**
     * Retrieves every txt files on the first level of the given directory, for each gets its content and make
     * a call to TD-IDF calculator and prints the result.
     * @param directory directory to process
     * @param terms list of words to use in TD-IDF algorithm
     * @throws IOException
     */
    private static List<TfIdfFile> processFiles (String directory, String terms, int resultsToShow) throws IOException{
        List<File> filesInFolder = processor.retrieveFilesIntoFolder(directory);
        List<TfIdfFile> documents = processor.readFileAndExtractItsInfo(filesInFolder);
        List<TfIdfFile> result = processor.processTdIdfAlgorithm(documents, terms);
        result
            .stream()
            .sorted(Comparator.comparing(TfIdfFile::getIdf).reversed())
            .limit(resultsToShow)
            .forEach((element) -> System.out.println("TD-IDF for " + element.getFileName() + " is " + element.getIdf()));
        return result;
    }


}
