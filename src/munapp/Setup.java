package munapp;

import javafx.scene.paint.Color;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for project mun-app.
 * Created by Yvan on 10-Feb-18.
 * Copyright (C) 2018.
 */
public class Setup {
    // attributes from the setup page
    private Path logoPath;
    private String conferenceName;
    private String committeeName;
    private List<Country> countriesList = new ArrayList<>();
    private Color primaryColor;
    private Color firstAccent;
    private Color secondAccent;
    private Path configPath;

    // attributes from the session page
    private String topic;
    private int numberOfSpeakers;
    private String sessionMode;
    private Country currentSpeaker;

    // speakers list works by being a list of strings which can be used to refer back to the
    // original countries list. In this way, only one master countries list is ever used and
    // holds all the information.
    private List<String> speakersList;
    private String resolutionTitle;

    // time variables, should add sanitizer to make sure the inputs are clean numbers, otherwise
    // throw error pop-up window
    private String totalTimeStr;
    private String timePerSpeakerStr;
    private int totalTimeSec;
    private int timePerSpeakerSec;

    // getter and setters for the attributes
    public List<Country> getCountriesList() {
        return countriesList;
    }

    // adds value to countriesList
    public void addCountriesList(String country) {
        countriesList.add(new Country(country, false, false, 0, false, false, false));
    }

    public Path getConfigPath() {
        return configPath;
    }

    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }

    public Path getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(Path logoPath) {
        this.logoPath = logoPath;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public Color getFirstAccent() {
        return firstAccent;
    }

    public void setFirstAccent(Color firstAccent) {
        this.firstAccent = firstAccent;
    }

    public Color getSecondAccent() {
        return secondAccent;
    }

    public void setSecondAccent(Color secondAccent) {
        this.secondAccent = secondAccent;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getNumberOfSpeakers() {
        return numberOfSpeakers;
    }

    public void setNumberOfSpeakers(int numberOfSpeakers) {
        this.numberOfSpeakers = numberOfSpeakers;
    }

    public String getSessionMode() {
        return sessionMode;
    }

    public void setSessionMode(String sessionMode) {
        this.sessionMode = sessionMode;
    }

    public Country getCurrentSpeaker() {
        return currentSpeaker;
    }

    public void setCurrentSpeaker(Country currentSpeaker) {
        this.currentSpeaker = currentSpeaker;
    }

    public List<String> getSpeakersList() {
        return speakersList;
    }

    public void setSpeakersList(List<String> speakersList) {
        this.speakersList = speakersList;
    }

    // add someone to the speakers list
    public void addSpeakersList(String countryName) {
        this.speakersList.add(countryName);
    }

    public String getResolutionTitle() {
        return resolutionTitle;
    }

    public void setResolutionTitle(String resolutionTitle) {
        this.resolutionTitle = resolutionTitle;
    }

    public String getTotalTimeStr() {
        return totalTimeStr;
    }

    public void setTotalTimeStr(String totalTimeStr) {
        this.totalTimeStr = totalTimeStr;
    }

    public String getTimePerSpeakerStr() {
        return timePerSpeakerStr;
    }

    public void setTimePerSpeakerStr(String timePerSpeakerStr) {
        this.timePerSpeakerStr = timePerSpeakerStr;
    }

    public int getTotalTimeSec() {
        return totalTimeSec;
    }

    public void setTotalTimeSec(int totalTimeSec) {
        this.totalTimeSec = totalTimeSec;
    }

    public int getTimePerSpeakerSec() {
        return timePerSpeakerSec;
    }

    public void setTimePerSpeakerSec(int timePerSpeakerSec) {
        this.timePerSpeakerSec = timePerSpeakerSec;
    }

    // this functions saves the configuration file to the path filePath
    public void saveConfigFile(Path filePath) {
        /* This function saves all the variables currently being used to a configuration file.
           This allows the program to save previous sessions for ease of use.
         */
        // the lines to write
        List<String> lines = new ArrayList<>();

        // the following add creates a list of stuff to add to the config file
        lines.add("<MUNConfigFile>");
        lines.add("    logoPath=" + logoPath);
        lines.add("    conferenceName=" + conferenceName);
        lines.add("    committeeName=" + committeeName);
        lines.add("    countriesList=" + countriesList.toString());
        lines.add("    primaryColor=" + primaryColor.getRed() + ", "
                + primaryColor.getGreen() + ", " + primaryColor.getBlue()
                + ", " + primaryColor.getOpacity());
        lines.add("    firstAccent=" + firstAccent.getRed() + ", "
                + firstAccent.getGreen() + ", " + firstAccent.getBlue()
                + ", " + firstAccent.getOpacity());
        lines.add("    secondAccent=" + secondAccent.getRed() + ", "
                + secondAccent.getGreen() + ", " + secondAccent.getBlue() + ", "
                + secondAccent.getOpacity());
        lines.add("    topic=" + topic);
        lines.add("    numberOfSpeakers=" + numberOfSpeakers);
        lines.add("    sessionMode=" + sessionMode);
        lines.add("    currentSpeaker=" + currentSpeaker.getCountryName());
        lines.add("    speakersList=" + speakersList.toString());
        lines.add("    resolutionTitle=" + resolutionTitle);
        lines.add("    totalTime=" + totalTimeSec);
        lines.add("    timePerSpeaker=" + timePerSpeakerSec);
        lines.add("</MUNConfigFile>");

        // write it to the file defined in the argument file Path
        try {
            Files.write(filePath, lines, Charset.forName("UTF-8"));
        } catch (java.io.IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public void loadConfigFile(Path filePath) {
        /* This function loads and parses saved configuration files and loads the configuration
           into the current program session.
         */
        // variables. This is the temporary config list
        List<String> config = new ArrayList<>();

        // reads it into the config variable
        try {
            config = Files.readAllLines(filePath);
        } catch (java.io.IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }

        // removes the part before the = sign
        for(int i = 0; i <= 14; i++) {
            config.set(i, config.get(i).replaceFirst(".*=",""));
        }

        // assigns variables based on the config file
        this.logoPath = Paths.get(config.get(1));
        this.conferenceName = config.get(2);
        this.committeeName = config.get(3);
        this.countriesList = countryListParser(config.get(4));
        this.primaryColor = colorParser(config.get(5));
        this.firstAccent = colorParser(config.get(6));
        this.secondAccent = colorParser(config.get(7));
        this.topic = config.get(8);
        this.numberOfSpeakers = Integer.parseInt(config.get(9));
        this.sessionMode = config.get(10);
        this.currentSpeaker = countryParser(config.get(11));
        this.speakersList = stringListParser(config.get(12));
        this.resolutionTitle = config.get(13);
        this.totalTimeSec = Integer.parseInt(config.get(14));
        this.timePerSpeakerSec = Integer.parseInt(config.get(15));

        // string values for the the time variables
        this.totalTimeStr = timeParser(totalTimeSec);
        this.timePerSpeakerStr = timeParser(timePerSpeakerSec);
    }

    private static Color colorParser(String colorString) {
        /* turns the color which is in string form back into a color object */
        // first splits the string into a list of strings at ", "
        List<String> args = Arrays.asList(colorString.split(", "));

        // and then returns it as a new Color object with the list args as its arguments
        return new Color(Double.parseDouble(args.get(0)), Double.parseDouble(args.get(1)),
                Double.parseDouble(args.get(2)), Double.parseDouble(args.get(3)));
    }

    private static List<String> stringListParser(String listString) {
        /* turns a list of string in string form back into a list object */
        // define variables and split the list
        // takes the substring without the first and last brackets, then splits it at ", ",
        // turns it into an ArrayList, and then returns it
        return new ArrayList<>(Arrays.asList(listString.substring(1, listString
                .length() - 1).split(", ")));
    }

    private static Country countryParser(String countryString) {
        /* turns a country in string form back into a Country object */
        // first splits the string into a list of strings at ", "
        List<String> args = Arrays.asList(countryString.split(", "));
        // and then returns it as a Country object with the list args as its arguments
        return new Country(args.get(0), Boolean.parseBoolean(args.get(1)), Boolean.parseBoolean
                (args.get(2)), Integer.parseInt(args.get(3)), Boolean.parseBoolean(args.get(4)),
                Boolean.parseBoolean(args.get(5)), Boolean.parseBoolean(args.get(6)));
    }

    private static List<Country> countryListParser(String countryListString) {
        /* turns the list of countries which is in string form back into a list of Country
        objects
         */
        // defines the returned list as a variable
        List<Country> returnList = new ArrayList<>();

        // takes the substring without the first 2 brackets and the last 2 brackets, then splits
        // it at "], [" and puts it into a list of strings
        List<String> countryList = Arrays.asList(countryListString.substring(2,
                                                 countryListString.length() - 2)
                                                 .split("\\], \\["));

        for(String i : countryList) {
            returnList.add(countryParser(i));
        }
        return returnList;
    }

    private static String timeParser(int time) {
        // turns the time in seconds to a string in human-readable format
        String minutes = Integer.toString(time / 60);
        String seconds = Integer.toString(time % 60);

        // returns the string
        return minutes + ":" + seconds;
    }
}
