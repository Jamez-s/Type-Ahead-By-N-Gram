package com.company;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

public class MainTest {
    private String hardCodedExample = "Mary had a little lamb its fleece was white as snow;\n" +
            "\n" +
            "And everywhere that Mary went, the lamb was sure to go.\n" +
            "\n" +
            "It followed her to school one day, which was against the rule;\n" +
            "\n" +
            "It made the children laugh and play, to see a lamb at school.\n" +
            "\n" +
            "And so the teacher turned it out, but still it lingered near,\n" +
            "\n" +
            "And waited patiently about till Mary did appear.\n" +
            "\n" +
            "\"Why does the lamb love Mary so?\" the eager children cry;\"Why, Mary loves the lamb, you know\" the teacher did reply.\"\n";

    private String input = "2,the";
    private Main main;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setup(){
       main = new Main(hardCodedExample, input);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void getInputWordTest(){
        String expected = "the";
        String actual = main.getInputWord();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getInputNGramTest(){
        int expected = 2;
        int actual = main.getInputNGram();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getWordsTest(){
        List<String> expected = new ArrayList<>(Arrays.asList("lamb", "rule", "children", "teacher", "lamb", "eager", "lamb", "teacher"));
        List<String> actual = main.findAllWordsOfNGram(main.getText(), main.getInputWord(), main.getInputNGram());

        Assert.assertEquals(expected, actual);
    }
    @Test
    public void getWordsNGramTest(){
        List<String> expected = new ArrayList<>(Arrays.asList("lamb", "rule", "children", "teacher", "lamb", "eager", "lamb", "teacher"));
        List<String> actual = main.findAllWordsOfNGram(main.getText(), main.getInputWord(), 4);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void mapWordOccurencesTest(){
        Map<String, Integer> expected = new TreeMap<>();
        expected.put("lamb", 3);
        expected.put("rule", 1);
        expected.put("teacher", 2);
        expected.put("children", 1);
        expected.put("eager", 1);
        Map<String, Integer> actual = main.mapWordOccurences(Arrays.asList("lamb", "rule", "children", "teacher", "lamb", "eager", "lamb", "teacher"));
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void printReportTest() {
        List<String> given = new ArrayList<>(Arrays.asList("lamb", "rule", "children", "teacher", "lamb", "eager", "lamb", "teacher"));
        Map<String, Integer> expected = main.mapWordOccurences(given);

        main.printReport(expected);
        assertEquals("lamb,0.375; teacher,0.250; children,0.125; eager,0.125; rule,0.125; ", outContent.toString());
    }



    @Test
    public void createReportTest(){
        String first = "lamb";
        int number = 3;
        int total = 8;
        String expected = "";
        String actual = main.formatReport(total, number, first);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }



}