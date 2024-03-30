package com.englishdictionary.dictionaryenglish.Management;

import com.englishdictionary.dictionaryenglish.Entities.Dictionary;
import com.englishdictionary.dictionaryenglish.Entities.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    protected Dictionary dictionary;
    protected List<Word> searchResultList;      // List words searcher
    protected static final Scanner scanner = new Scanner(System.in);

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
        System.out.print("Enter the number of words: ");
        int wordCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < wordCount; i++) {
            System.out.println("Word number " + (i+1));
            System.out.print("Enter the word target: ");
            String word_target = scanner.nextLine();

            System.out.print("Enter the word explain: ");
            String word_explain = scanner.nextLine();

            dictionary.addWord(new Word(word_target, word_explain));
        }
    }

    /**
     * Enter data from file.
     */
    public void insertFromFile(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    String wordTarget =  parts[0];
                    String wordExplain = parts[1];
                    dictionary.addWord(new Word(wordTarget, wordExplain));
                }
            }
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

        StringBuilder stringBuilder =new StringBuilder();
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
        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().startsWith(prefixWord)) {
                searchResultList.add(word);
            }
        }
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
