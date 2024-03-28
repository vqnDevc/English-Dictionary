package com.englishdictionary.dictionaryenglish.Commandline;

import com.englishdictionary.dictionaryenglish.Entities.Word;
import com.englishdictionary.dictionaryenglish.Management.DictionaryManagement;

public class DictionaryCommandline extends DictionaryManagement {

    public DictionaryCommandline() {
        super();
    }

    public void showAllWords() {
        System.out.println();
        System.out.printf("%-4s | %-18s | %-20s%n", "No", "English", "Vietnamese");

        for (Word word : dictionary.getWordList()) {
            System.out.printf(
                    "%-4s | %-18s | %-20s%n",
                    dictionary.getIndex(word) + 1,
                    word.getWord_target(),
                    word.getWord_explain());
        }
    }
}
