module org.jmc.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires javafx.web;
    requires jlayer;
    requires freetts;
    requires org.xerial.sqlitejdbc;


    opens org.jmc.dictionary to javafx.fxml;
    exports org.jmc.dictionary;
    exports org.jmc.dictionary.Controllers;
    exports org.jmc.dictionary.Controllers.MenuTabs;
    exports org.jmc.dictionary.Controllers.Games;
    exports org.jmc.dictionary.Models;
    exports org.jmc.dictionary.Views;
    exports org.jmc.dictionary.Management;
    exports org.jmc.dictionary.Entities;
    exports org.jmc.dictionary.Utils;

}