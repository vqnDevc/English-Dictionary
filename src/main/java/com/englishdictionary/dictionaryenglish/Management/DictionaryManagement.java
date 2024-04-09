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
            + File.separator + "dictionaries.txt";

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
            System.out.println("Import from file successfully.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportFile() {
        if (dictionary.getWordList().isEmpty()) {
            System.out.println("The Dictionary has no words, please add or import words to Dictionary.");
        } else {
            try {
                dictionaryExportFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Export to file successfully.");
        }
    }

    /**
     * Export to file.
     */
    private void dictionaryExportFile() throws IOException {
        FileWriter fileWriter = new FileWriter(DictionaryManagement.DICTIONARY_EXPORT_FILE_PATH);
        System.out.println(DictionaryManagement.DICTIONARY_EXPORT_FILE_PATH);
        StringBuilder stringBuilder = new StringBuilder();
        for (Word word : dictionary.getWordList()) {
            stringBuilder.append(word.getWord_target()).append("\t");
            stringBuilder.append(word.getWord_explain()).append("\n");
        }

        fileWriter.write(stringBuilder.toString());
        fileWriter.close();
    }

    public void lookUpWord() {
        if (getDictionary().getWordList().isEmpty()) {
            System.out.println("The Dictionary has no words, please add or import words to Dictionary.");
        } else {
            System.out.println("LOOKUP: Enter your word target: ");
            String wordTarget = scanner.nextLine().toLowerCase();

            if (wordExit(wordTarget)) {
                dictionaryLookup(wordTarget);
            } else {
                System.err.println("This word does not exist in the dictionary");
            }
        }
    }

    /**
     * Find word explain in dictionary.
     */
    private void dictionaryLookup(String wordTarget) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                System.out.println("Word Explain: " + word.getWord_explain());
                return;
            }
        }
        System.err.println("This word does not exist in the dictionary");
    }

    /**
     * Add word to Dictionary.
     * @param wordTarget target
     * @param wordExplain explain
     */
    private void addToDictionary(String wordTarget, String wordExplain) {
        dictionary.addWord(new Word(wordTarget.toLowerCase(), wordExplain.toLowerCase()));
    }

    public void updateWord() {
        if (getDictionary().getWordList().isEmpty()) {
            System.out.println("The Dictionary has no words, please add or import words to Dictionary.");
        } else {
            System.out.println("UPDATE: Enter word target to update: ");
            String wordTarget = scanner.nextLine().toLowerCase();

            if (wordExit(wordTarget)) {
                System.out.println("UPDATE: Enter new word explain of this word: ");
                String wordExplain = scanner.nextLine();
                updateWordInDictionary(wordTarget, wordExplain);
                System.out.println("Update completed.\n");
            } else {
                System.err.println("This word does not exist in the dictionary");
            }
        }
    }

    /**
     * Edit word in Dictionary.
     * @param wordTarget target
     * @param replaceWordExplain replace explain
     */
    private void updateWordInDictionary(String wordTarget, String replaceWordExplain) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                word.setWord_explain(replaceWordExplain);
                return;
            }
        }
    }

    public void removeWord() {
        if (dictionary.getWordList().isEmpty()) {
            System.out.println("The Dictionary has no words, please add or import words to Dictionary.");
        } else {
            System.out.println("REMOVE: Enter word target to remove: ");
            String wordTarget = scanner.nextLine().toLowerCase();

            if (wordExit(wordTarget)) {
                removeFromDictionary(wordTarget);
                System.out.println("This word are completely removed.\n");
            } else {
                System.out.println("This word is not exit.\n");
            }
        }
    }

    /**
     * Remove a word from Dictionary.
     * @param wordTarget target
     */
    private void removeFromDictionary(String wordTarget) {
        dictionary.getWordList().removeIf(word -> word.getWord_target().equals(wordTarget));
    }

    public void searchWord() {
        if (getDictionary().getWordList().isEmpty()) {
            System.out.println("The Dictionary has no words, please add or import words to Dictionary.");
        } else {
            System.out.println("SEARCH: Enter your prefix word: ");
            String wordTarget = scanner.nextLine().toLowerCase();
            dictionarySearcher(wordTarget);
            showAllWordsSearch();
        }
    }

    /**
     * Search word with prefix.
     * @param prefixWord string
     */
    private void dictionarySearcher(String prefixWord) {
        searchResultList.clear();
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().startsWith(prefixWord)) {
                searchResultList.add(word);
            }
        }
    }

    private void showAllWordsSearch() {
        if (searchResultList.isEmpty()) {
            System.out.println("No words found!");
        } else {
            System.out.println();
            System.out.printf("%-4s | %-18s | %-20s%n", "No", "English", "Vietnamese");

            for (Word word : searchResultList) {
                System.out.printf(
                        "%-4s | %-18s | %-20s%n",
                        searchResultList.indexOf(word) + 1,
                        word.getWord_target(),
                        word.getWord_explain());
            }
        }
    }

    /**
     * Sort the dictionary by A-Z.
     */
    private void dictionarySort() {
        dictionary.getWordList().sort(Comparator.comparing(Word::getWord_target));
    }

    /**
     * Check word exit.
     * @param wordTarget target
     * @return boolean
     */
    private boolean wordExit(String wordTarget) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                return true;
            }
        }
        return false;
    }
}
