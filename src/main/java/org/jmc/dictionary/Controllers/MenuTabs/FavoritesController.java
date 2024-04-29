package org.jmc.dictionary.Controllers.MenuTabs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jmc.dictionary.DictionaryApp;
import org.jmc.dictionary.Management.FavoriteWords;

public class FavoritesController extends SearcherController {
    @Override
    public void updateAutocompleteList() {
        autocompleteWordList.clear();
        FavoriteWords.searchFavorite(searchField.getText().trim().replaceAll("\\s+", " "));
        autocompleteWordList.addAll(FavoriteWords.searchFavoriteResult);
        autocompleteList.setItems(autocompleteWordList);
    }

    public void onFavoriteClick() {
        String target = wordTargetLabel.getText();
        if (target.isEmpty()) {
            showNotification("Favorite", "No word chosen!");
            return;
        }

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Remove '" + wordTargetLabel.getText() + "' from Favorites?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            String wordTarget = wordTargetLabel.getText();
            FavoriteWords.removeFavorite(wordTarget);

            showNotification("Remove from Favorites", "Word removed from Favorites successfully!");
            updateAutocompleteList();
            wordTargetLabel.setText("");
            wordExplainView.getEngine().loadContent("");
        }
    }

    @Override
    protected void enableEditView() {
        wordExplainView.setVisible(false);
        wordExplainView.setDisable(true);

        String explain = DictionaryApp.dictionary.dictionaryAppLookup(wordTargetLabel.getText()).getWord_explain();
        wordExplainEditor.setVisible(true);
        wordExplainEditor.setDisable(false);
        wordExplainEditor.setHtmlText(explain);

        saveEditButton.setVisible(true);
        saveEditButton.setDisable(false);

        favoriteButton.setVisible(false);
        favoriteButton.setDisable(true);

        editButton.setImage(cancelImage);
    }

    @Override
    protected void disableEditView() {
        String explain = DictionaryApp.dictionary.dictionaryAppLookup(wordTargetLabel.getText()).getWord_explain();
        wordExplainView.setVisible(true);
        wordExplainView.setDisable(false);
        wordExplainView.getEngine().loadContent(explain, "text/html");

        wordExplainEditor.setVisible(false);
        wordExplainEditor.setDisable(true);

        saveEditButton.setVisible(false);
        saveEditButton.setDisable(true);

        favoriteButton.setVisible(true);
        favoriteButton.setDisable(false);

        editButton.setImage(editImage);
    }
}
