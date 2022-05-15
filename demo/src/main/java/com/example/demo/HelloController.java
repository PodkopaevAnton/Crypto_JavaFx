package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Character.toUpperCase;

public class HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField keyField;

    @FXML
    private TextField pathField;

    @FXML
    private Button rashifrButton;

    @FXML
    private Button shifrButton;

    @FXML
    private TextField shifrField;

    @FXML
    private Label welcomeText;

    @FXML
    void initialize() {
        shifrButton.setOnAction(event -> {
        String beginFile = pathField.getText().trim();
        int key = Integer.parseInt((keyField.getText()));
        String finalFile = shifrField.getText().trim();
            try {
                Criptoanalizer.fileShifr(beginFile,key,finalFile);
            } catch (IOException e) {
                welcomeText.setText("Ошибка! Неправильные данные");
                e.printStackTrace();
            }
            welcomeText.setText("Файл зашифрован!");
        });

        rashifrButton.setOnAction(event -> {
            rashifrButton.getScene().getWindow().hide();
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/demo/app.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            welcomeText.setText(null);
        });
    }

}

