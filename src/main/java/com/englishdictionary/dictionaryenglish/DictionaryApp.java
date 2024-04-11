package com.englishdictionary.dictionaryenglish;

import javafx.application.Application;
import javafx.stage.Stage;

public class DictionaryApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

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
