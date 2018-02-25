package munapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Controller {
    @FXML
    TableView delTableSetup;
    // the choose logo button and view
    @FXML
    Button chooseLogo;
    @FXML
    ImageView logoView;

    // the color pickers
    @FXML
    ColorPicker primaryColorPicker;
    @FXML
    ColorPicker firstColorPicker;
    @FXML
    ColorPicker secondColorPicker;

    // setup text fields
    @FXML
    TextField conNameSetup;
    @FXML
    Button conNameApply;
    @FXML
    TextField commNameSetup;
    @FXML
    Button commNameApply;

    // setup delegates list
    @FXML
    TextField delNameField;
    @FXML
    Button delNameSubmit;
    @FXML
    ListView delListSetup;

    // logo chooser action
    @FXML
    protected void chooseLogoClick(ActionEvent event) throws IOException {
        // defines the actions that occur when the choose logo button is clicked. In this case, a
        // file chooser opens up with default directory being the home directory
        final FileChooser fileChooser = new FileChooser();

        // configure the FileChooser
        fileChooser.setTitle("Open Logo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.png", "*.PNG",
                        "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.mpo", "*.MP0"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.JPG"),
                new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG"),
                new FileChooser.ExtensionFilter("GIF", "*.gif", "*.GIF"));

        // sets the logoFile variable to whatever file is chosen in the file chooser
        File file = fileChooser.showOpenDialog(chooseLogo.getScene().getWindow());
        Main.setup.setLogoFile(file);
        try {
            logoView.setImage(new Image(file.toURI().toString()));
        } catch (NullPointerException e) {
            System.err.println("No file found/selected: " + e);
        }
    }

    // color picker actions
    @FXML
    protected void setPrimaryColor(ActionEvent event) throws IOException {
        // sets the primaryColor value in the setup
        Main.setup.setPrimaryColor(primaryColorPicker.getValue());
    }

    @FXML
    protected void setFirstColor(ActionEvent event) throws IOException {
        // sets the firstAccent value in the setup
        Main.setup.setFirstAccent(firstColorPicker.getValue());
    }

    @FXML
    protected void setSecondColor(ActionEvent event) throws IOException {
        // sets the secondColor value in the setup
        Main.setup.setSecondAccent(secondColorPicker.getValue());
    }

    // text field actions
    @FXML
    protected void conNameSet(ActionEvent event) throws IOException {
        // saves the current conference name to the setup class
        Main.setup.setConferenceName(conNameSetup.getText());
    }

    @FXML
    protected void commNameSet(ActionEvent event) throws IOException {
        // saves the current committee name to the setup class
        Main.setup.setCommitteeName(commNameSetup.getText());
    }

}
