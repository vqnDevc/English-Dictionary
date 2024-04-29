package org.jmc.dictionary.Controllers.MenuTabs;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import org.jmc.dictionary.Controllers.ErrorController;
import org.jmc.dictionary.DictionaryApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddToDictionaryController extends ErrorController {
    public TextArea wordField;
    public HTMLEditor explainField;
    public Label warningLabel;
    public Button addButton;
    public TextField pronunciationField;
    public TextArea shortDescArea;

    private static final Pattern INVALID_WORD_PATTERN = Pattern.compile(
            "[^-a-zA-Z0-9'()\\s]+(?!\\s)"
    );
    private static final Pattern INVALID_WORD_PATTERN_2 = Pattern.compile(
            "\\s[^a-zA-Z0-9]+\\s"
    );

    public void onWordFieldType() {
        String word = wordField.getText().trim().replaceAll("\\s+", " ");

        Matcher m = INVALID_WORD_PATTERN.matcher(word);
        Matcher m2 = INVALID_WORD_PATTERN_2.matcher(word);

        if (word.isEmpty()) {
            clearFields();
        } else if (m.find() || m2.find()) {
            warningLabel.setText("WARNING: Invalid English Word!");
            warningLabel.setTextFill(Color.RED);
            disableAddInfoFields();
        } else if (DictionaryApp.dictionary.wordExit(word)) {
            warningLabel.setText("WARNING: Word already exist in Dictionary!");
            warningLabel.setTextFill(Color.RED);
            disableAddInfoFields();
        } else {
            warningLabel.setText("Word can be added to Dictionary!");
            warningLabel.setTextFill(Color.GREEN);
            enableAddInfoFields();
            updateExplainFieldTemplate();
        }
    }

    private void disableAddInfoFields() {
        addButton.setDisable(true);
        explainField.setDisable(true);
        pronunciationField.setDisable(true);
        shortDescArea.setDisable(true);
    }

    private void enableAddInfoFields() {
        addButton.setDisable(false);
        explainField.setDisable(false);
        pronunciationField.setDisable(false);
        shortDescArea.setDisable(false);
    }

    //pronunciationField.getText()
    public void updateExplainFieldTemplate() {
        String explainTemplate = String.format(
                "%s (meaning): ",
                wordField.getText());
        explainField.setHtmlText(explainTemplate);
    }

    public void onAddButtonClick() {
        String target = wordField.getText();
        String explain = explainField.getHtmlText().replace(" dir=\"ltr\"><head></head" +
                "><body contenteditable=\"true\">", ">");
        explain = explain.replace("</body>", "");
        explain = explain.replace("<html>", "");
        explain = explain.replace("</html>", "");

        String shortDesc = shortDescArea.getText();
        String pronunciation = pronunciationField.getText();
        // , shortDesc, pronunciation
        DictionaryApp.dictionary.addToDictionary(target, explain);

        showNotification("Add to Dictionary", "Added '" + target + "' to Dictionary successfully");
        clearFields();
    }

    private void clearFields() {
        wordField.setText("");
        explainField.setHtmlText("");
        pronunciationField.setText("");
        shortDescArea.setText("");
        warningLabel.setText("");

        disableAddInfoFields();
    }
}
