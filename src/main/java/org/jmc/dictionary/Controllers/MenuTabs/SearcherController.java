package org.jmc.dictionary.Controllers.MenuTabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javazoom.jl.decoder.JavaLayerException;
import org.jmc.dictionary.Controllers.ErrorController;
import org.jmc.dictionary.DictionaryApp;
import org.jmc.dictionary.Entities.Word;
import org.jmc.dictionary.Management.DictionaryManagement;
import org.jmc.dictionary.Management.FavoriteWords;
import org.jmc.dictionary.Management.SearchHistory;
import org.jmc.dictionary.Utils.GoogleVoiceAPI;
import org.jmc.dictionary.Utils.TextToSpeech;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearcherController extends ErrorController implements Initializable {
    public TextField searchField;
    public ListView<String> autocompleteList;
    public Label wordTargetLabel;
    public WebView wordExplainView;
    public HTMLEditor wordExplainEditor;
    public ImageView wordToSpeechUS;
    public ImageView wordToSpeechUK;
    public ImageView favoriteButton;
    public ImageView saveEditButton;
    public ImageView editButton;
    public ImageView deleteButton;

    protected Image starImage;
    protected Image starFilledImage;
    protected Image editImage;
    protected Image cancelImage;
    protected final ObservableList<String> autocompleteWordList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autocompleteList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onAutocompleteListClick();
            }
        });

        File star =new File(DictionaryManagement.PATH_UTILITY_ICONS_FOLDER + "star.png");
        starImage = new Image(star.toURI().toString());
        File starFilled = new File(DictionaryManagement.PATH_UTILITY_ICONS_FOLDER + "star_filled.png");
        starFilledImage = new Image(starFilled.toURI().toString());
        File edit = new File(DictionaryManagement.PATH_UTILITY_ICONS_FOLDER + "edit_note.png");
        editImage = new Image(edit.toURI().toString());
        File cancel = new File(DictionaryManagement.PATH_UTILITY_ICONS_FOLDER + "cancel.png");
        cancelImage = new Image(cancel.toURI().toString());

        updateAutocompleteList();
    }

    public void updateAutocompleteList() {
        autocompleteWordList.clear();
        DictionaryApp.dictionary.dictionarySearcher(searchField.getText().trim().replaceAll("\\s+", " "));

        for (Word word : DictionaryApp.dictionary.getSearchResultList()) {
            autocompleteWordList.add(word.getWord_target());
        }

        autocompleteList.setItems(autocompleteWordList);
    }

    public void onSearchFieldKeyTyped() {
        updateAutocompleteList();
    }

    public void onAutocompleteListClick() {
        String target = autocompleteList.getSelectionModel().getSelectedItem();
        if (target == null) {
            return;
        }

        if (FavoriteWords.inFavorites(target)) {
            favoriteButton.setImage(starFilledImage);
        } else {
            favoriteButton.setImage(starImage);
        }

        Word word = DictionaryApp.dictionary.dictionaryAppLookup(target);
        if (word == null) {
            if (wordExplainEditor.isDisable()) {
                wordExplainView.getEngine().loadContent("No word exists!", "text/html");
            } else if (wordExplainView.isDisable()) {
                wordExplainEditor.setHtmlText("No word exists!");
            }
            return;
        }

        wordTargetLabel.setText(word.getWord_target());

        if (wordExplainEditor.isDisable()) {
            wordExplainView.getEngine().loadContent(word.getWord_explain(), "text/html");
        } else if (wordExplainView.isDisable()) {
            wordExplainEditor.setHtmlText(word.getWord_explain());
        }

        SearchHistory.getSearchHistory().add(word.getWord_target());
    }

    public void onWordLabelClick() {
        String word = wordTargetLabel.getText();
        if (word.isEmpty()) {
            showNotification("Copy to clipboard", "No word chosen!");
            return;
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(word);
        clipboard.setContent(content);

        showNotification("Copy to clipboard", "'" + word + "' copied to clipboard!");
    }

    public void onWordToSpeechUKClick() {
        String word = wordTargetLabel.getText();
        if (word.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        try {
            GoogleVoiceAPI.getInstance().playAudio(GoogleVoiceAPI.getInstance().getAudio(wordTargetLabel.getText(),
                    "en-UK"));
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(wordTargetLabel.getText());
        }
    }

    public void onWordToSpeechUSClick() {
        String word = wordTargetLabel.getText();
        if (word.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        try {
            GoogleVoiceAPI.getInstance().playAudio(GoogleVoiceAPI.getInstance().getAudio(wordTargetLabel.getText(),
                    "en-US"));
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(wordTargetLabel.getText());
        }
    }

    public void onFavoriteClick() {
        String target = wordTargetLabel.getText();
        if (target.isEmpty()) {
            showNotification("Favorite", "No word chosen!");
            return;
        }

        if (!FavoriteWords.inFavorites(target)) {
            FavoriteWords.insertFavorite(target);
            favoriteButton.setImage(starFilledImage);
        } else {
            FavoriteWords.removeFavorite(target);
            favoriteButton.setImage(starImage);
        }
    }

    public void onEditButtonClick() {
        if (wordTargetLabel.getText().isEmpty()) {
            showNotification("Edit", "Cannot edit a non-existent word!");
            return;
        }
        if (wordExplainEditor.isVisible()) {
            disableEditView();
        } else {
            enableEditView();
        }
    }

    protected void enableEditView() {
        wordExplainView.setVisible(false);
        wordExplainView.setDisable(true);

        String explain = DictionaryApp.dictionary.dictionaryAppLookup(wordTargetLabel.getText()).getWord_explain();
        wordExplainEditor.setVisible(true);
        wordExplainEditor.setDisable(false);
        wordExplainEditor.setHtmlText(explain);

        saveEditButton.setVisible(true);
        saveEditButton.setDisable(false);

        deleteButton.setVisible(false);
        deleteButton.setDisable(true);

        favoriteButton.setVisible(false);
        favoriteButton.setDisable(true);

        editButton.setImage(cancelImage);
    }

    protected void disableEditView() {
        String explain = DictionaryApp.dictionary.dictionaryAppLookup(wordTargetLabel.getText()).getWord_explain();
        wordExplainView.setVisible(true);
        wordExplainView.setDisable(false);
        wordExplainView.getEngine().loadContent(explain, "text/html");

        wordExplainEditor.setVisible(false);
        wordExplainEditor.setDisable(true);

        saveEditButton.setVisible(false);
        saveEditButton.setDisable(true);

        deleteButton.setVisible(true);
        deleteButton.setDisable(false);

        favoriteButton.setVisible(true);
        favoriteButton.setDisable(false);

        editButton.setImage(editImage);
    }

    public void onSaveEditClick() {
        String newExplain = wordExplainEditor.getHtmlText().replace(" dir=\"ltr\"><head></head" +
                "><body contenteditable=\"true\">", ">");
        newExplain = newExplain.replace("</body>", "");
        newExplain = newExplain.replace("<html>", "");
        newExplain = newExplain.replace("</html>", "");

        DictionaryApp.dictionary.updateWordInDictionary(wordTargetLabel.getText(), newExplain);

        showNotification("Edit", "Word edited successfully!");
        disableEditView();
    }

    public void onRemoveClick() {
        String wordTarget = wordTargetLabel.getText();
        if (wordTarget.isEmpty()) {
            showNotification("Delete", "Cannot delete a non-existent word!");
            return;
        }

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Delete '" + wordTargetLabel.getText() + "'?",
                ButtonType.YES,
                ButtonType.NO
        );
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            DictionaryApp.dictionary.removeFromDictionary(wordTarget);
            SearchHistory.removeFromHistory(wordTarget);
            FavoriteWords.removeFavorite(wordTarget);

            showNotification("Remove", "Word removed successfully!");
            updateAutocompleteList();
            favoriteButton.setImage(starImage);
            wordTargetLabel.setText("");
            wordExplainView.getEngine().loadContent("");
        }
    }
}
