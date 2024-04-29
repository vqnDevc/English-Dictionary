package org.jmc.dictionary.Controllers.MenuTabs;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.jmc.dictionary.Management.FavoriteWords;
import org.jmc.dictionary.Management.SearchHistory;
import org.jmc.dictionary.Models.Model;
import org.jmc.dictionary.Views.DictionaryMenuOptions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryMenuController implements Initializable {
    public Button search_btn;
    public Button gg_translate_btn;
    public Button favorites_btn;
    public Button history_btn;
    public Button add_words_btn;
    public Button game_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        search_btn.setOnAction(event -> onSearcher());
        gg_translate_btn.setOnAction(event -> onGoogleTranslate());
        favorites_btn.setOnAction(event -> onFavorites());
        history_btn.setOnAction(event -> onHistory());
        add_words_btn.setOnAction(event -> onAddWords());
        game_btn.setOnAction(event -> onGames());
    }

    private void onSearcher() {
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().set(DictionaryMenuOptions.SEARCHER);
    }

    private void onGoogleTranslate() {
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().set(DictionaryMenuOptions.GOOGLE_TRANSLATE);
    }

    private void onFavorites() {
        turnOffServices();
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().set(DictionaryMenuOptions.FAVORITES);
    }

    private void onHistory() {
        turnOffServices();
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().set(DictionaryMenuOptions.HISTORY);
    }

    private void onAddWords() {
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().set(DictionaryMenuOptions.ADD_TO_DICTIONARY);
    }

    private void onGames() {
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().set(DictionaryMenuOptions.GAMES);
    }

    private void turnOffServices() {
        try {
            SearchHistory.saveSearchHistory();
            FavoriteWords.saveFavoriteWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
