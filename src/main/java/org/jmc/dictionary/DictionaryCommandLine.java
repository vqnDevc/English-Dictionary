package org.jmc.dictionary;

import org.jmc.dictionary.Commandline.DictionaryCommandline;

import java.io.IOException;


public class DictionaryCommandLine {
    private final DictionaryCommandline dictionaryCommandline;

    public DictionaryCommandLine() {
        dictionaryCommandline = new DictionaryCommandline();
    }

    public void dictionaryBasic() {
//        dictionaryCommandline.insertFromCommandline();

    }

    public static void main(String[] args) throws IOException {
        DictionaryCommandLine test = new DictionaryCommandLine();
        test.dictionaryBasic();

        test.dictionaryCommandline.dictionaryAdvanced();
    }
}
