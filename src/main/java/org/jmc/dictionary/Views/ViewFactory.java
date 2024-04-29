package org.jmc.dictionary.Views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.web.WebView;
import org.jmc.dictionary.Controllers.ApplicationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewFactory {
    private final ObjectProperty<DictionaryMenuOptions> selectedMenuDictionary;
    private BorderPane searcherView;
    private BorderPane googleTranslateView;
    private BorderPane favoritesView;
    private BorderPane historyView;
    private BorderPane addToDictionaryView;
    private BorderPane gamesView;


    public ViewFactory() {
        this.selectedMenuDictionary = new SimpleObjectProperty<>();
    }

    public ObjectProperty<DictionaryMenuOptions> getSelectedMenuDictionary() {
        return selectedMenuDictionary;
    }

    public BorderPane getSearcherView() {
        if (searcherView == null) {
            try {
                searcherView = new FXMLLoader(getClass().getResource("/Fxml/Tabs/Searcher.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return searcherView;
    }

    public BorderPane getGoogleTranslateView() {
        if (googleTranslateView == null) {
            try {
                googleTranslateView = new FXMLLoader(getClass().getResource("/Fxml/Tabs/GoogleTranslate.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return googleTranslateView;
    }

    public BorderPane getFavoritesView() {
        if (favoritesView == null) {
            try {
                favoritesView = new FXMLLoader(getClass().getResource("/Fxml/Tabs/Favorites.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return favoritesView;
    }

    public BorderPane getHistoryView() {
        if (historyView == null) {
            try {
                historyView = new FXMLLoader(getClass().getResource("/Fxml/Tabs/History.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return historyView;
    }

    public BorderPane getAddToDictionaryView() {
        if (addToDictionaryView == null) {
            try {
                addToDictionaryView = new FXMLLoader(getClass().getResource("/Fxml/Tabs/AddToDictionary.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addToDictionaryView;
    }

    public BorderPane getGamesView() {
        if (gamesView == null) {
            try {
                gamesView = new FXMLLoader(getClass().getResource("/Fxml/Tabs/Games/game.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gamesView;
    }


    public void showAppWindow () {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/application.fxml"));
        ApplicationController applicationController = new ApplicationController();
        loader.setController(applicationController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dictionary");
        stage.show();
    }
}
