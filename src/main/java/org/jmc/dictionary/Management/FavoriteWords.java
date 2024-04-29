package org.jmc.dictionary.Management;

import org.jmc.dictionary.Utils.IOFileList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteWords {
    private static List<String> favoriteWords;
    public static List<String> searchFavoriteResult;


    public static final String PATH_FAVORITES_FILE = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "data"
            + File.separator + "favorites.txt";

    public static void loadFavoriteWords() throws IOException {
        if (favoriteWords == null) {
            favoriteWords = new ArrayList<>();
        } else {
            favoriteWords.clear();
        }
        IOFileList.loadFileToList(PATH_FAVORITES_FILE, favoriteWords);
    }

    public static List<String> getFavoriteWords() {
        return favoriteWords;
    }

    public static void saveFavoriteWords() throws IOException {
        IOFileList.saveListToFile(PATH_FAVORITES_FILE, favoriteWords);
    }

    public static boolean inFavorites(String word) {
        if (favoriteWords.isEmpty()) {
            return false;
        }
        return Collections.binarySearch(favoriteWords, word) >= 0;
    }

    public static void insertFavorite(String word) {
        favoriteWords.add(word);

        int i = favoriteWords.size() - 2;
        while (i >= 0 && favoriteWords.get(i).compareTo(word) > 0) {
            favoriteWords.set(i + 1, favoriteWords.get(i));
            i--;
        }

        favoriteWords.set(i + 1, word);
    }

    public static void searchFavorite(String word) {
        if (searchFavoriteResult == null) {
            searchFavoriteResult = new ArrayList<>();
        } else {
            searchFavoriteResult.clear();
        }

        int index = Collections.binarySearch(favoriteWords, word, new StringPrefixComparator());
        if (index < 0) {
            return;
        }

        int startIndex = index;
        int endIndex = index;

        while (startIndex > 0
                && favoriteWords.get(startIndex - 1).startsWith(word)
        ) {
            startIndex--;
        }

        while (endIndex < favoriteWords.size() - 1
                && favoriteWords.get(endIndex + 1).startsWith(word)
        ) {
            endIndex++;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            searchFavoriteResult.add(favoriteWords.get(i));
        }
    }

    public static void removeFavorite(String word) {
        favoriteWords.remove(word);
    }
}

