module com.example.dictionaryproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dictionaryproject to javafx.fxml;
    exports com.example.dictionaryproject;
}