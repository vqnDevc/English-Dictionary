package org.jmc.dictionary.Controllers.MenuTabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javazoom.jl.decoder.JavaLayerException;
import org.jmc.dictionary.Controllers.ErrorController;
import org.jmc.dictionary.Utils.GoogleTranslateAPI;
import org.jmc.dictionary.Utils.GoogleVoiceAPI;
import org.jmc.dictionary.Utils.TextToSpeech;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

public class GoogleTranslateController extends ErrorController implements Initializable {
    public TextArea inputTextArea;
    public ChoiceBox<String> sourceLangChoiceBox;
    public ImageView speakButtonSrc;
    public Label charCountLabel;
    public TextArea resultTextArea;
    public ImageView speakButtonDest;
    public ChoiceBox<String> destLangChoiceBox;
    public Button translateButton;

    final String[] inLanguages = {"English", "Vietnamese", "Auto"};
    final String[] outLanguages = {"English", "Vietnamese"};
    GoogleTranslateAPI.LANGUAGE sourceLang;
    GoogleTranslateAPI.LANGUAGE destLang;

    private static final int CHAR_LIMIT = 9999;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sourceLangChoiceBox.getItems().addAll(inLanguages);
        sourceLangChoiceBox.setValue(inLanguages[0]);

        destLangChoiceBox.getItems().addAll(outLanguages);
        destLangChoiceBox.setValue(outLanguages[1]);

        sourceLangChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if (sourceLangChoiceBox.getItems().get((Integer) number2).equals(inLanguages[0])) {
                    destLangChoiceBox.setValue(outLanguages[1]);
                } else if (sourceLangChoiceBox.getItems().get((Integer) number2).equals(inLanguages[1])) {
                    destLangChoiceBox.setValue(outLanguages[0]);
                }
            }
        });

        destLangChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if (!sourceLangChoiceBox.getValue().equals(inLanguages[2])) {
                    if (destLangChoiceBox.getItems().get((Integer) number2).equals(outLanguages[0])) {
                        sourceLangChoiceBox.setValue(inLanguages[1]);
                    } else if (destLangChoiceBox.getItems().get((Integer) number2).equals(outLanguages[1])) {
                        sourceLangChoiceBox.setValue(inLanguages[0]);
                    }
                }
            }
        });

        sourceLang = GoogleTranslateAPI.LANGUAGE.ENGLISH;
        destLang = GoogleTranslateAPI.LANGUAGE.VIETNAMESE;
        updateCharCountLabel();
    }

    public void onInputTextAreaTyped() {
        updateCharCountLabel();
    }

    public void onSpeakButtonSrcClick() {
        String input = inputTextArea.getText();
        if (input.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        String languageOutput = sourceLangChoiceBox.getValue().equals(inLanguages[1])
                ? "vi-VN"
                : "en-US";

        try {
            GoogleVoiceAPI.getInstance().playAudio(GoogleVoiceAPI.getInstance().getAudio(inputTextArea.getText(),
                    languageOutput));
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(inputTextArea.getText());
        }
    }

    public void onSpeakButtonDestClick() {
        String input = resultTextArea.getText();
        if (input.isEmpty()) {
            showNotification("TextToSpeech", "No word chosen!");
            return;
        }

        String languageOutput = destLangChoiceBox.getValue().equals(outLanguages[1])
                ? "vi-VN"
                : "en-US";

        try {
            GoogleVoiceAPI.getInstance().playAudio(
                    GoogleVoiceAPI.getInstance().getAudio(resultTextArea.getText(),
                            languageOutput)
            );
        } catch (IOException | JavaLayerException e) {
            System.err.println("Failed to play Audio from Google, fallback to FreeTTS");
            TextToSpeech.speak(resultTextArea.getText());
        }
    }

    public void onTranslateButtonClicked() {
        String inputText = inputTextArea.getText();

        if (sourceLangChoiceBox.getValue().equals(inLanguages[0])) {
            sourceLang = GoogleTranslateAPI.LANGUAGE.ENGLISH;
        } else if (sourceLangChoiceBox.getValue().equals(inLanguages[1])) {
            sourceLang = GoogleTranslateAPI.LANGUAGE.VIETNAMESE;
        } else {
            sourceLang = GoogleTranslateAPI.LANGUAGE.AUTO;
        }

        destLang = destLangChoiceBox.getValue().equals(outLanguages[0])
                ? GoogleTranslateAPI.LANGUAGE.ENGLISH
                : GoogleTranslateAPI.LANGUAGE.VIETNAMESE;

        try {
            resultTextArea.setText(GoogleTranslateAPI.translate(
                    inputText, sourceLang, destLang
            ));
        } catch (IOException e) {
            resultTextArea.setText("ERROR: Cannot connect to Google Translate.");
        } catch (TimeoutException e) {
            resultTextArea.setText("ERROR: Took too long to get query result from Google Translate");
        }
    }

    private void updateCharCountLabel() {
        String inputText = inputTextArea.getText();

        if (inputText.length() > CHAR_LIMIT) {
            showNotification("Warning!", "Translate word limit reached!");
            inputText = inputText.substring(0, CHAR_LIMIT);
            inputTextArea.setText(inputText);
        }

        charCountLabel.setText(inputText.length() + " / " + CHAR_LIMIT);
    }
}
