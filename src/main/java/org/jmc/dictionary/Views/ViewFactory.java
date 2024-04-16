package org.jmc.dictionary.Views;

import org.jmc.dictionary.Controllers.ApplicationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private BorderPane searcherView;

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
