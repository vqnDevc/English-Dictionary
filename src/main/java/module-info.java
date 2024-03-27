module com.englishdictionary.dictionaryenglish {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.englishdictionary.dictionaryenglish to javafx.fxml;
    exports com.englishdictionary.dictionaryenglish;
}