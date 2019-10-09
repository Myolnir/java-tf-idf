package com.myolnir;

import com.myolnir.model.TfIdfFile;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TfIdf {

    private static TfIdfFilesProcessor processor = TfIdfFilesProcessor.getInstance();
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static String directory;
    private static String terms;
    private static String count;
    private static String period;

    public static void main(String[] args)  {
        getInputFields();
        executorService.scheduleAtFixedRate(TfIdf::run, 0, Long.valueOf(period), TimeUnit.SECONDS);
    }

    private static void getInputFields() {
        Scanner s=new Scanner(System.in);
        System.out.print("Enter directory where documents will be written: ");
        directory = s.nextLine();
        System.out.print("Enter terms TT to be analyzed: ");
        terms = s.nextLine();
        System.out.print("Enter the count of top results to show: ");
        count = s.nextLine();
        System.out.print("Enter the period of top results to show: ");
        period = s.nextLine();
    }


    private static void run() {
        try {
            processFiles(directory, terms, Integer.parseInt(count));
        } catch (IOException ioException) {
            System.out.println("Error while process files caused by " + ioException.toString());
            System.exit(-1);
        }

    }

    /**
     * Retrieves every txt files on the first level of the given directory, for each gets its content and make
     * a call to TD-IDF calculator and prints the result.
     * @param directory directory to process
     * @param terms list of words to use in TD-IDF algorithm
     * @throws IOException
     */
    private static void processFiles (String directory, String terms, int resultsToShow) throws IOException{
        List<File> filesInFolder = processor.retrieveFilesIntoFolder(directory);
        List<TfIdfFile> documents = processor.readFileAndExtractItsInfo(filesInFolder);
        List<TfIdfFile> result = processor.processTdIdfAlgorithm(documents, terms);
        printResult(resultsToShow, result);
    }

    private static void printResult(int resultsToShow, List<TfIdfFile> result) {
        result
            .stream()
            .sorted(Comparator.comparing(TfIdfFile::getIdf).reversed())
            .limit(resultsToShow)
            .forEach((element) -> System.out.println("TD-IDF for " + element.getFileName() + " is " + element.getIdf()));
        System.out.println("---------------");
    }


}
