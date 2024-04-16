package org.jmc.dictionary;

import org.jmc.dictionary.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class DictionaryApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showAppWindow();
    }

    @Override
    public void stop() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Closing the Application");
    }

    public static void main(String[] args) {
        launch();
    }
}
