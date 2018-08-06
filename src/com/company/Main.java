package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private String inputWord;
    private String text;
    private int inputNGram = -1;



    public Main(String text, String input) throws NumberFormatException{
        this.text = setText(text);
        this.inputWord = input.replaceAll("[^a-zA-Z ]", "");
        try {
            this.inputNGram = Integer.valueOf(input.replaceAll("[^0-9]+", ""));
        }
        catch(NumberFormatException n){
            System.out.println("No N-Gram Entered");
        }
    }

    public static void main(String[] args) throws IOException {
        String hardCodedExample = "Mary had a little lamb its fleece was white as snow;\n" +
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

        //String input = "3,the";
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String input;
        while ((input = in.readLine()) != null) {
            Main main = new Main(hardCodedExample, input);
            if((main.getInputNGram() > 0) && (!main.getInputWord().equals(""))) {
                List<String> allWords = main.findAllWordsOfNGram(main.getText(), main.getInputWord(), main.getInputNGram());
                Map<String, Integer> mappedWords = main.mapWordOccurences(allWords);
                main.printReport(mappedWords);
            }
            else if(main.getInputWord().equals(""))
                System.out.println("No Word Entered");
        }
    }

    public String getInputWord() {
        return inputWord;
    }
    //not tested because it is pretty standard with no added logic
    public String getText() {
        return text;
    }

    public int getInputNGram() {
        return inputNGram;
    }

    public String setText(String text){
        return text.replaceAll("\\W", " ");
    }


    public List<String> findAllWordsOfNGram(String text, String inputWord, int inputNGram) {
        List<String> myList = new ArrayList<>();
        String regexPattern = "("+inputWord+")\\s*(\\w+)";
        for(int x = 2; x< inputNGram; x++){
            regexPattern = regexPattern + "\\s*(\\w+)";
        }
        Pattern p = Pattern.compile(regexPattern);
        Matcher m = p.matcher(text);
        while (m.find()) {
            if (inputNGram > 2) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int x = 2; x <= inputNGram; x++) {
                    stringBuilder.append(m.group(x).toLowerCase() + " ");
                }
                myList.add(stringBuilder.toString());
            }
            else
                myList.add(m.group(2).toLowerCase());
        }
        return myList;
    }

    public Map<String, Integer> mapWordOccurences(List<String> myList){
        Map <String, Integer> myMap = new TreeMap<>();
        int temp;
        for(String s: myList){
            if(myMap.containsKey(s)){
                temp = myMap.get(s);
                myMap.put(s, ++temp);
            }
            else
                myMap.put(s, 1);

        }
        myMap.put("total", myList.size());
        return myMap;
    }



    public void printReport(Map<String, Integer> given){
        int total = given.get("total");
        given.remove("total");
        Collection<Integer> keySet = new HashSet<>(given.values());
        while (keySet.size() != 0) {
            int maxValue = Collections.max(keySet);
            for (String s : given.keySet()) {
                if (given.get(s).equals(maxValue)) {
                    System.out.print(formatReport(total, maxValue, s));
                    keySet.remove(maxValue);
                }
            }

        }
        System.out.println();
    }

    public String formatReport(int total, int mapped, String word) {
        StringBuilder stringBuilder = new StringBuilder();
            double deci = (double)mapped/(double)total;
            String format = String.format("%s,%.3f; ", word, deci);
            stringBuilder.append(format);
        return stringBuilder.toString();
    }
}
