/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.net.xtelco.ironbark.rflett.cse3mip.controllers;

import au.net.xtelco.ironbark.rflett.cse3mip.Control;
import au.net.xtelco.ironbark.rflett.cse3mip.model.Car;
import au.net.xtelco.ironbark.rflett.cse3mip.model.IOManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JOptionPane;

/**
 *
 * @author Ryan
 */
public class LandingController implements Initializable {

    private ArrayList<Car> carDB = new ArrayList();
    private static String currentFileName = "car.dat";
    private String currentCarSelection;
    public static String filename;
    
    @FXML
    private ComboBox<String> carSelection;
    @FXML
    private Button controlButton;
    @FXML
    private Button newButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button cancelButton_m;
    @FXML
    private Button modifySaveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label IPLabel;
    @FXML
    private Label portLabel;
    @FXML
    private Label nameLabel_m;
    @FXML
    private Label usernameLabel_m;
    @FXML
    private Label passwordLabel_m;
    @FXML
    private Label IPLabel_m;
    @FXML
    private Label portLabel_m;
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField IPField;
    @FXML
    private TextField portField;
    @FXML
    private TextField nameField_m;
    @FXML
    private TextField usernameField_m;
    @FXML
    private TextField passwordField_m;
    @FXML
    private TextField IPField_m;
    @FXML
    private TextField portField_m;
    @FXML
    private ImageView landingImage;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private Pane pane;
    @FXML
    private VBox vbox;
    @FXML
    private WebView videoContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carDB = IOManager.readTextDatabase(new File(currentFileName));

        showImage();
        
        hideStream();

        buildComboBox();

        disableButtons();

        carSelection.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (newValue != null) {
                        enableButtons();
                    } else if (newValue == null) {
                        disableButtons();
                    }
                });
    }

    @FXML
    private void handleCarSelection(ActionEvent event) {

    }

    @FXML
    private void handleControl(ActionEvent event) {
        String name = (String) carSelection.getValue();

        playVideo();
        showImage();
        hideImage();

        carDB.stream().filter((car) -> (car.getName().equals(name))).forEach((car) -> {
            try {
                Control.takeOver(car);
            } catch (IOException ex) {
                Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void handleNew(ActionEvent event) throws IOException {
        showNew();
    }

    @FXML
    private void handleModify(ActionEvent event) {
        showModify();

        String name = (String) carSelection.getValue();

        carDB.stream().filter((car) -> (car.getName().equals(name))).map((car) -> {
            nameField_m.setText(car.getName());
            return car;
        }).map((car) -> {
            usernameField_m.setText(car.getUser());
            return car;
        }).map((car) -> {
            passwordField_m.setText(car.getPass());
            return car;
        }).map((car) -> {
            IPField_m.setText(car.getIp());
            return car;
        }).forEach((car) -> {
            portField_m.setText(Integer.toString(car.getPort()));
        });
    }

    @FXML
    private void handleSave(ActionEvent event) {
        Car car1 = new Car();

        if (nameField != null
                || usernameField != null
                || passwordField != null
                || IPField != null
                || portField != null) {
            car1.setName(nameField.getText());
            car1.setUser(usernameField.getText());
            car1.setPass(passwordField.getText());
            car1.setIp(IPField.getText());
            car1.setPort(Integer.parseInt(portField.getText()));

            carSelection.getItems().add(car1.getName());
            carDB.add(car1);

            clearNewFields();

            showImage();

            JOptionPane.showMessageDialog(null,
                    "Successfully saved car",
                    "Car saved",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "One of the fields has no entry, please enter values in every field before saving.",
                    "Blank fields error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void handleModifySave(ActionEvent event) {
        String name = (String) carSelection.getValue();
        Car oldCar = new Car();
        Car car1 = new Car();

        for (Car car : carDB) {
            if (car.getName().equals(name)) {
                oldCar = car;
            }
        }

        if (nameField_m != null
                || usernameField_m != null
                || passwordField_m != null
                || IPField_m != null
                || portField_m != null) {
            car1.setName(nameField_m.getText());
            car1.setUser(usernameField_m.getText());
            car1.setPass(passwordField_m.getText());
            car1.setIp(IPField_m.getText());
            car1.setPort(Integer.parseInt(portField_m.getText()));

            carDB.remove(oldCar);
            carDB.add(car1);

            clearModifyFields();

            showImage();

            buildComboBox();

            JOptionPane.showMessageDialog(null,
                    "Successfully saved car",
                    "Car saved",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null,
                    "One of the fields has no entry, please enter values in every field before saving.",
                    "Blank fields error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearNewFields();
        showImage();
    }

    @FXML
    private void handleCancel_m(ActionEvent event) {
        clearModifyFields();
        showImage();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        String name = (String) carSelection.getValue();
        Car oldCar = new Car();

        for (Car car : carDB) {
            if (car.getName().equals(name)) {
                oldCar = car;
            }
        }

        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this car?",
                "Wifi Car Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (selectedOption == 0) {
            carDB.remove(oldCar);
            buildComboBox();

            carSelection.getSelectionModel().select(-1);
        }

        showImage();
    }

    @FXML
    private void handleFileSave(ActionEvent event) {
        File file = new File(currentFileName);
        IOManager.writeTextDatabase(file, carDB);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        ArrayList<Car> fileDB = IOManager.readTextDatabase(new File(currentFileName));

        if (!fileDB.containsAll(carDB) && carDB.containsAll(fileDB)) {
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "Do you want to save changes to your database? If you exit without"
                    + " saving all changes will be lost.",
                    "DVD Database",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            switch (selectedOption) {
                case 0:
                    File file = new File(currentFileName);
                    IOManager.writeTextDatabase(file, carDB);
                    System.exit(0);
                    break;
                case 1:
                    System.exit(0);
                    break;
            }
        } else {
            Object[] options = {"Yes", "No"};

            int Answer = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (Answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void buildComboBox() {
        carSelection.getItems().clear();

        List<String> names = new ArrayList();
        carDB.stream().forEach((car) -> {
            names.add(car.getName());
        });

        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.setAll(names);

        carSelection.setItems(obList);
    }

    private void showImage() {
        landingImage.setVisible(true);

        hideModify();
        hideNew();
    }

    private void showNew() {
        nameField.setVisible(true);
        usernameField.setVisible(true);
        passwordField.setVisible(true);
        IPField.setVisible(true);
        portField.setVisible(true);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);

        nameLabel.setVisible(true);
        usernameLabel.setVisible(true);
        passwordLabel.setVisible(true);
        IPLabel.setVisible(true);
        portLabel.setVisible(true);

        hideImage();
        hideModify();
    }

    private void showModify() {
        nameField_m.setVisible(true);
        usernameField_m.setVisible(true);
        passwordField_m.setVisible(true);
        IPField_m.setVisible(true);
        portField_m.setVisible(true);

        modifySaveButton.setVisible(true);
        cancelButton_m.setVisible(true);

        nameLabel_m.setVisible(true);
        usernameLabel_m.setVisible(true);
        passwordLabel_m.setVisible(true);
        IPLabel_m.setVisible(true);
        portLabel_m.setVisible(true);

        hideImage();
        hideNew();
    }

    private void hideImage() {
        landingImage.setVisible(false);
    }

    private void hideNew() {
        nameField.setVisible(false);
        usernameField.setVisible(false);
        passwordField.setVisible(false);
        IPField.setVisible(false);
        portField.setVisible(false);

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        nameLabel.setVisible(false);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        IPLabel.setVisible(false);
        portLabel.setVisible(false);
    }

    private void hideModify() {
        nameField_m.setVisible(false);
        usernameField_m.setVisible(false);
        passwordField_m.setVisible(false);
        IPField_m.setVisible(false);
        portField_m.setVisible(false);

        modifySaveButton.setVisible(false);
        cancelButton_m.setVisible(false);

        nameLabel_m.setVisible(false);
        usernameLabel_m.setVisible(false);
        passwordLabel_m.setVisible(false);
        IPLabel_m.setVisible(false);
        portLabel_m.setVisible(false);
    }
    
    private void showStream() {
        videoContainer.setVisible(true);
    }
    
    private void hideStream() {
        videoContainer.setVisible(false);
    }

    private void clearNewFields() {
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
        IPField.clear();
        portField.clear();
    }

    private void clearModifyFields() {
        nameField_m.clear();
        usernameField_m.clear();
        passwordField_m.clear();
        IPField_m.clear();
        portField_m.clear();
    }

    private void disableButtons() {
        controlButton.setDisable(true);
        modifyButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void enableButtons() {
        controlButton.setDisable(false);
        modifyButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    @FXML
    private void handleHelp(ActionEvent event) throws IOException, URISyntaxException {
        File htmlFile = new File("public_html/index.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
    
    private void playVideo(){
        String name = (String) carSelection.getValue();
        
        carDB.stream().filter((car) -> (car.getName().equals(name))).forEach((car) -> {
            filename = ("http://" + car.getIp());    
        });
        WebEngine webEngine = videoContainer.getEngine();
        
         webEngine.load(filename);
        // Display the video view
        showStream();
    }
}
