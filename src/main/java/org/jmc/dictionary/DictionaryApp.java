package org.jmc.dictionary;

import org.jmc.dictionary.Management.DictionaryManagement;
import org.jmc.dictionary.Management.FavoriteWords;
import org.jmc.dictionary.Management.SearchHistory;
import org.jmc.dictionary.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jmc.dictionary.Utils.GoogleTranslateAPI;
import org.jmc.dictionary.Utils.GoogleVoiceAPI;
import org.jmc.dictionary.Utils.TextToSpeech;



public class DictionaryApp extends Application {
    public static DictionaryManagement dictionary;

    @Override
    public void start(Stage stage) throws Exception {
        dictionary = new DictionaryManagement();
        dictionary.insertFromFile(DictionaryManagement.DICTIONARY_INPUT_FILE_PATH);
        SearchHistory.loadSearchHistory();
        FavoriteWords.loadFavoriteWords();
        Model.getInstance().getViewFactory().showAppWindow();
    }

    @Override
    public void stop() {
        TextToSpeech.shutDown();
        GoogleVoiceAPI.shutdownExecutorService();
        GoogleTranslateAPI.shutdownExecutorService();
        System.out.println(Thread.currentThread().getName());
        System.out.println("Closing the Application");
    }

    public static void main(String[] args) {
        launch();
    }
}
