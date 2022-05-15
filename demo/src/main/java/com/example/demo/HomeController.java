package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton bruteForceBut;

    @FXML
    private TextField exitField;

    @FXML
    private TextField pathField;

    @FXML
    private Button rashifrButton;

    @FXML
    private Button shifrButton;

    @FXML
    private RadioButton statBut;

    @FXML
    private Label welcomeText1;

    @FXML
    void initialize() {
        rashifrButton.setOnAction(event -> {
            String beginFile = pathField.getText().trim();
            String finalFile = exitField.getText().trim();
            int key = 0;
            if (bruteForceBut.isSelected()){
                try {
                    key = Criptoanalizer.fileDeshifr(beginFile,finalFile,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(statBut.isSelected()){
                try {
                    key = Criptoanalizer.fileDeshifr(beginFile,finalFile,2);
                } catch (IOException e) {
                    welcomeText1.setText("Ошибка! Неправильные данные");
                    e.printStackTrace();
                }
            }
            welcomeText1.setText("Файл расшифрован. " + "Ключ к шифру Цезаря " + key);
        });
        shifrButton.setOnAction(event -> {
            shifrButton.getScene().getWindow().hide();
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/demo/hello-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            welcomeText1.setText(null);
        });
    }
    }


