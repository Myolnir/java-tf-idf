package com.myolnir;

import com.myolnir.model.TdIdfFile;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;

public class TdIdf {

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

        boolean valid = true;
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
     * @param directory
     * @param terms
     * @throws IOException
     */
    private static void processFiles (String directory, String terms, int resultsToShow) throws IOException{
        List<File> filesInFolder = Files.list(Paths.get(directory))
                .filter(file -> file.getFileName().toString().endsWith(".txt"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        List<TdIdfFile> documents = new ArrayList<>();
        TdIfCalculator calculator = new TdIfCalculator();
        for (File file : filesInFolder) {
            try {
                List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(file.getAbsolutePath())));
                documents.add(new TdIdfFile(lines, file.getName()));
            }
            catch (IOException e) {
                System.out.println("File not exists");
            }
        }

        List<TdIdfFile> result = new ArrayList<>();
        for (TdIdfFile document : documents) {
            double tfidf = calculator.tfIdf(document.getFileContent(), documents, terms);
            result.add(new TdIdfFile(document.getFileContent(), document.getFileName(), tfidf));
        }
        result
            .stream()
            .sorted(Comparator.comparing(TdIdfFile::getIdf).reversed())
            .limit(resultsToShow)
            .forEach((element) -> System.out.println("TD-IDF for " + element.getFileName() + " is " + element.getIdf()));
    }



}
