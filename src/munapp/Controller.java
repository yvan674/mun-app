package munapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Controller {


    @FXML
    Button chooseLogo;

    @FXML
    protected void chooseLogoClick(ActionEvent event) throws IOException {
        // defines the actions that occur when the choose logo button is clicked. In this case, a
        // file chooser opens up with default directory being the home directory
        final FileChooser fileChooser = new FileChooser();

        // configure the FileChooser
        fileChooser.setTitle("Open Logo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.JPG"),
                new FileChooser.ExtensionFilter("PNG", "*.png", "*.PNG"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp", "*.BMP"));

        File file = fileChooser.showOpenDialog(chooseLogo.getScene().getWindow());
    }
}
