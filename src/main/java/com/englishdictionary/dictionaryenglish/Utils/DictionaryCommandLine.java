package com.englishdictionary.dictionaryenglish.Utils;

import com.englishdictionary.dictionaryenglish.Commandline.DictionaryCommandline;



public class DictionaryCommandLine {
    private final DictionaryCommandline dictionaryCommandline;

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }

    public void dictionaryBasic() {
        dictionaryCommandline.insertFromCommandline();
        dictionaryCommandline.showAllWords();
/*
        String filePath = "E:\\DictionaryEnglish\\src\\main\\resources\\data\\text.txt";
        dictionaryCommandline.insertFromFile(filePath);
*/
    }

    public static void main(String[] args) {
        DictionaryCommandLine test = new DictionaryCommandLine();
        test.dictionaryBasic();

        test.dictionaryCommandline.dictionaryLookup();
    }
}
