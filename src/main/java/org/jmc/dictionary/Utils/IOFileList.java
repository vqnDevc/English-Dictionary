package org.jmc.dictionary.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IOFileList {
    public static void loadFileToList(String filePath, List<String> list) throws IOException {
        FileReader fileReader;
        BufferedReader bufferedReader;

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            System.err.println(e.toString());
            throw e;
        }

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }

        bufferedReader.close();
        fileReader.close();
    }

    public static void saveListToFile(String filePath, List<String> list) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);

        StringBuilder content = new StringBuilder();
        for (String s : list) {
            content.append(s).append("\n");
        }

        fileWriter.write(content.toString());
        fileWriter.close();
    }
}

