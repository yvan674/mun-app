package munapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {
    static Setup setup = new Setup();

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("munapp.fxml")));
        primaryStage.setTitle("MUN Presenter");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // create the menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        // --- File menu
        Menu menuFile = new Menu("File");
        MenuItem openSession = new MenuItem("Open Session");
        openSession.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // defines the actions that occur when the Open Session menu is clicked. In this
                // case, a file chooser opens up with default directory being the home directory
                final FileChooser fileChooser = new FileChooser();

                // configure the FileChooser
                fileChooser.setTitle("Open Session");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

                // set extension filter
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XML", "*.xml"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

                // show open file dialog
                File config = fileChooser.showOpenDialog(primaryStage.getScene().getWindow());

                if(config != null) {
                    setup.loadXMLConfig(config);
                }
            }
        });

        MenuItem saveSession = new MenuItem("Save Session");
        saveSession.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // defines the actions that occur when the Save Session menu is clicked. In this
                // case, a file chooser opens up with default directory being the home directory
                final FileChooser fileChooser = new FileChooser();


                // configure the FileChooser
                fileChooser.setTitle("Save Session");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.setInitialFileName("Untitled Session");
                
                // set extension filter
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XML", "*.xml"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

                // show save file dialog
                File config = fileChooser.showSaveDialog(primaryStage);

                if(config != null) {
                    setup.saveXMLConfig(config);
                }
            }
        });

        MenuItem saveDelegates = new MenuItem("Save Delegates List");
        saveDelegates.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // todo add a save delegates list function to csv
            }
        });
        menuFile.getItems().addAll(openSession, saveSession, saveDelegates);

        // --- Help menu
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        MenuItem helpMe = new Menu("Help");
        helpMe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        menuHelp.getItems().addAll(about, helpMe);

        // enable the menus
        menuBar.getMenus().addAll(menuFile, menuHelp);


        final String OS = System.getProperty("os.name");
        if (OS != null && OS.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
        } else if (OS != null && OS.startsWith("Windows")) {
            root.setTop(menuBar);
        }

        // show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
