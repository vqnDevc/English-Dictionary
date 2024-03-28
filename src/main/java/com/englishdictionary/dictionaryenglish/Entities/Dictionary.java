package com.englishdictionary.dictionaryenglish.Entities;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private final List<Word> wordList;

    public Dictionary() {
        wordList = new ArrayList<>();
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void addWord(Word word) {
        wordList.add(word);
    }

    public int getIndex(Word word) {
        return wordList.indexOf(word);
    }
}
