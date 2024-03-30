package com.englishdictionary.dictionaryenglish.Utils;

import com.englishdictionary.dictionaryenglish.Commandline.DictionaryCommandline;

import java.io.IOException;


public class DictionaryCommandLine {
    private final DictionaryCommandline dictionaryCommandline;

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }

    public void dictionaryBasic() {
//        dictionaryCommandline.insertFromCommandline();
        String filePath = "E:\\DictionaryEnglish\\src\\main\\resources\\data\\text.txt";
        dictionaryCommandline.insertFromFile(filePath);

    }

    public static void main(String[] args) throws IOException {
        DictionaryCommandLine test = new DictionaryCommandLine();
        test.dictionaryBasic();

        test.dictionaryCommandline.dictionaryAdvanced();
    }
}
