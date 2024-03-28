package com.englishdictionary.dictionaryenglish.Management;

import com.englishdictionary.dictionaryenglish.Entities.Dictionary;
import com.englishdictionary.dictionaryenglish.Entities.Word;

import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    protected Dictionary dictionary;
    private static final Scanner scanner = new Scanner(System.in);

    public DictionaryManagement() {
        dictionary = new Dictionary();
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
     * Find word explain in dictionary.
     */
    public void dictionaryLookup() {
        System.out.print("Look up a word in Dictionary: ");
        String wordTarget = scanner.nextLine();

        for (Word word : dictionary.getWordList()) {
            if (word.getWord_target().equals(wordTarget)) {
                System.out.println("Word Explain: " + word.getWord_explain());
                return;
            }
        }
        System.out.println("This word don't exit in Dictionary");
    }
}
