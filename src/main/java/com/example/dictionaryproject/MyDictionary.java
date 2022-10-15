package com.example.dictionaryproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MyDictionary extends Application {
    private Group titleGroup = new Group();

    int xLine = 20;
    int yLine = 20;
    int yLine2 = 50;

    DictionaryUsingHM dictionary;
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(400, 350);
        root.getChildren().addAll(titleGroup);

        dictionary = new DictionaryUsingHM();

        TextField wordText = new TextField("Accio Word");
        wordText.setTranslateX(xLine);
        wordText.setTranslateY(yLine);

        Label meaningLable = new Label("I am meaning");
        meaningLable.setTranslateX(xLine);
        meaningLable.setTranslateY(yLine2);

        Button searchButton = new Button("Search");
        searchButton.setTranslateX(xLine+200);
        searchButton.setTranslateY(yLine);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String word = wordText.getText();
                if(word.isBlank()){
                    meaningLable.setText("Please Enter a Word");
                }
                else{
                    meaningLable.setText(dictionary.findMeaning(word));
                }
            }
        });


        titleGroup.getChildren().addAll(wordText, searchButton, meaningLable);
        return root;
    }
    public void connectToDatabase(){
        final String DB_URL = "jdbc:mysql://localhost:3306/my_dict";
        final String USER = "root";
        final String PASS = "MUSLAXMI96@verma";

        System.out.println("Connecting to Database");
        String newId = "select * from word_list";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(newId);
        ) {
            while (rs.next()){
                System.out.println(rs.getInt("id")+rs.getString("word")+rs.getString("meaning"));
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        connectToDatabase();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("My Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}