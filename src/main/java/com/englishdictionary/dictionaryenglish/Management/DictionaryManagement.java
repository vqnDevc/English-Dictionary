package com.englishdictionary.dictionaryenglish.Management;

import com.englishdictionary.dictionaryenglish.Entities.Dictionary;
import com.englishdictionary.dictionaryenglish.Entities.Word;

import java.io.*;
import java.util.*;

public class DictionaryManagement {
    private final Dictionary dictionary;

    private final List<Word> searchResultList;      // List words searcher

    protected static final Scanner scanner = new Scanner(System.in);

    public final static String DICTIONARY_INPUT_FILE_PATH = System.getProperty("user.dir")   //filepath input data
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "data"
            + File.separator + "inputData.txt";

    public final static String DICTIONARY_EXPORT_FILE_PATH = System.getProperty("user.dir")   //filepath input data
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "data"
            + File.separator + "exportData.txt";

    public DictionaryManagement() {
        dictionary = new Dictionary();
        searchResultList = new ArrayList<>();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public List<Word> getSearchResultList() {
        return searchResultList;
    }

    /**
     * Enter data from keyboard.
     */
    public void insertFromCommandline() {
        int wordCount = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("ADD: Enter the number of words: ");
            try {
                wordCount = scanner.nextInt();
                scanner.nextLine();
                validInput = true;      //Mark valid input to exit the loop
            } catch (InputMismatchException e) {
                System.err.println("Please enter an integer.");
                scanner.next();
            }
        }

        for (int i = 0; i < wordCount; i++) {
            System.out.println("Word number " + (i + 1));
            System.out.print("Enter the word target: ");
            String word_target = scanner.nextLine();

            while (wordExit(word_target)) {
                System.out.println("This word is already existed!");
                System.out.print("Enter the word target: ");
                word_target = scanner.nextLine();
            }

            System.out.print("Enter the word explain: ");
            String word_explain = scanner.nextLine();

            addToDictionary(word_target, word_explain);
            System.out.println("This word was added to Dictionary.\n");
        }
        dictionarySort();
    }


    /**
     * Enter data from file.
     */
    public void insertFromFile(String filePath) {
        dictionary.getWordList().clear();
        File file = new File(filePath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    String wordTarget =  parts[0];
                    String wordExplain = parts[1];
                    addToDictionary(wordTarget, wordExplain);
                }
            }
            dictionarySort();
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export to file.
     * @param filePath file
     */
    public void dictionaryExportFile(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        System.out.println(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        for (Word word : dictionary.getWordList()) {
            stringBuilder.append(word.getWord_target()).append("\t");
            stringBuilder.append(word.getWord_explain()).append("\n");
        }

        fileWriter.write(stringBuilder.toString());
        fileWriter.close();
    }

    /**
     * Find word explain in dictionary.
     */
    public void dictionaryLookup(String wordTarget) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                System.out.println("Word Explain: " + word.getWord_explain());
                return;
            }
        }
        System.out.println("This word don't exit in Dictionary");
    }

    /**
     * Add word to Dictionary.
     * @param wordTarget target
     * @param wordExplain explain
     */
    public void addToDictionary(String wordTarget, String wordExplain) {
        dictionary.addWord(new Word(wordTarget, wordExplain));
    }

    /**
     * Edit word in Dictionary.
     * @param wordTarget target
     * @param replaceWordExplain replace explain
     */
    public void updateWordInDictionary(String wordTarget, String replaceWordExplain) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                word.setWord_explain(replaceWordExplain);
                return;
            }
        }
    }

    /**
     * Remove a word from Dictionary.
     * @param wordTarget target
     */
    public  void removeFromDictionary(String wordTarget) {
        dictionary.getWordList().removeIf(word -> word.getWord_target().equals(wordTarget));
    }

    /**
     * Search word with prefix.
     * @param prefixWord string
     */
    public void dictionarySearcher(String prefixWord) {
        searchResultList.clear();
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().startsWith(prefixWord)) {
                searchResultList.add(word);
            }
        }
    }

    /**
     * Sort the dictionary by A-Z.
     */
    public void dictionarySort() {
        dictionary.getWordList().sort(Comparator.comparing(Word::getWord_target));
    }

    /**
     * Check word exit.
     * @param wordTarget target
     * @return boolean
     */
    public boolean wordExit(String wordTarget) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                return true;
            }
        }
        return false;
    }
}
