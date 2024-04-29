package org.jmc.dictionary.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.jmc.dictionary.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    public BorderPane application;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuDictionary().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case GOOGLE_TRANSLATE -> application.setCenter(Model.getInstance().getViewFactory().getGoogleTranslateView());
                case FAVORITES -> application.setCenter(Model.getInstance().getViewFactory().getFavoritesView());
                case HISTORY -> application.setCenter(Model.getInstance().getViewFactory().getHistoryView());
                case ADD_TO_DICTIONARY -> application.setCenter(Model.getInstance().getViewFactory().getAddToDictionaryView());
                case GAMES -> application.setCenter(Model.getInstance().getViewFactory().getGamesView());
                default -> application.setCenter(Model.getInstance().getViewFactory().getSearcherView());
            }
        });
    }
}
