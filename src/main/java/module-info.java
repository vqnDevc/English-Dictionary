module com.englishdictionary.dictionaryenglish {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.jmc.dictionary to javafx.fxml;
    exports org.jmc.dictionary;
}