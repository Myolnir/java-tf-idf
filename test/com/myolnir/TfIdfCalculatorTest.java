package com.myolnir;

import com.myolnir.model.TfIdfFile;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TfIdfCalculatorTest {
    private TfIdfCalculator calculator;
    private List<String> fileWithOccurences = Arrays.asList("ipsum", "lorem", "ipsum", "lorem", "ipsum");
    private List<String> fileWithoutOccurences = Arrays.asList("at", "test", "lorem");
    private TfIdfFile occurences = new TfIdfFile(fileWithOccurences, "fileWithOccurences");
    private TfIdfFile noOccurences = new TfIdfFile(fileWithoutOccurences, "fileWithoutOccurences");

    private String termToSearch = "ipsum";

    @Before
    public void setUp() {
        calculator = new TfIdfCalculator();
    }

    @Test
    public void correctFileWithOccurrenciesTest() {
        double result = calculator.tfIdf(fileWithOccurences, Arrays.asList(occurences, noOccurences), termToSearch);
        assertEquals(0.4158f, result, 0.0002);
    }

    @Test
    public void correctFileWithNoOccurrenciesTest() {
        double result = calculator.tfIdf(fileWithoutOccurences, Arrays.asList(noOccurences), termToSearch);
        assertEquals(0.0, result, 0.0002);
    }


}
