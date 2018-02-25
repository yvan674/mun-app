package munapp;

import javafx.scene.paint.Color;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Class for project mun-app.
 * Created by Yvan on 10-Feb-18.
 * Copyright (C) 2018.
 */
public class Setup {
    private Properties appConfig = new Properties();

    // attributes from the setup page
    private File logoFile;
    private String conferenceName;
    private String committeeName;
    private List<Country> countriesList;
    private Color primaryColor;
    private Color firstAccent;
    private Color secondAccent;

    // attributes from the session page
    private String topic;
    private int numberOfSpeakers;
    private Country currentSpeaker;
    // options for session mode are off, black, logo, roleCall, voting, unmod, session, moderated
    private String sessionMode;

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

    /**
     * Constructor for the Setup class. Creates a setup object with basically no contents, but
     * enough so that it can be read by Controller.java and create an empty app window.
     */
    public Setup(){
        logoFile = null;
        conferenceName = "";
        committeeName = "";
        countriesList = new ArrayList<>();
        primaryColor = new Color(1, 1, 1, 1);
        firstAccent = new Color(1, 1, 1, 1);
        secondAccent = new Color(1, 1, 1, 1);
        topic = "";
        numberOfSpeakers = 0;
        currentSpeaker = null;
        sessionMode = "off";
        speakersList = new ArrayList<>();
        resolutionTitle = "";
        totalTimeSec = 0;
        timePerSpeakerSec = 0;
        totalTimeStr = "00:00";
        timePerSpeakerStr = "00:00";

    }

    /**
     * Gets primary color.
     *
     * @return the primary color
     */
    public Color getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Sets primary color.
     *
     * @param primaryColor the primary color
     */
    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    /**
     * Gets first accent.
     *
     * @return the first accent
     */
    public Color getFirstAccent() {
        return firstAccent;
    }

    /**
     * Sets first accent.
     *
     * @param firstAccent the first accent
     */
    public void setFirstAccent(Color firstAccent) {
        this.firstAccent = firstAccent;
    }

    /**
     * Gets second accent.
     *
     * @return the second accent
     */
    public Color getSecondAccent() {
        return secondAccent;
    }

    /**
     * Sets second accent.
     *
     * @param secondAccent the second accent
     */
    public void setSecondAccent(Color secondAccent) {
        this.secondAccent = secondAccent;
    }

    /**
     * Gets logo file.
     *
     * @return the logo file
     */
    public File getLogoFile() {
        return logoFile;
    }

    /**
     * Sets logo file.
     *
     * @param logoFile the logo file
     */
    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

    /**
     * Gets conference name.
     *
     * @return the conference name
     */
    public String getConferenceName() {
        return conferenceName;
    }

    /**
     * Sets conference name.
     *
     * @param conferenceName the conference name
     */
    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    /**
     * Gets committee name.
     *
     * @return the committee name
     */
    public String getCommitteeName() {
        return committeeName;
    }

    /**
     * Sets committee name.
     *
     * @param committeeName the committee name
     */
    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    /**
     * Gets countries list.
     *
     * @return the countries list
     */
    public List<Country> getCountriesList() {
        return countriesList;
    }

    /**
     * Sets countries list.
     *
     * @param countriesList the countries list
     */
    public void setCountriesList(List<Country> countriesList) {
        this.countriesList = countriesList;
    }

    /**
     * Loads an XML config for the appConfig and then applies the configuration to all the
     * variables.
     *
     * @param configFile The configuration file
     */
    void loadXMLConfig(File configFile){
        // try catch block to load the property from an xml file
        try {
            appConfig.loadFromXML(new FileInputStream(configFile));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }

        conferenceName = appConfig.getProperty("conferenceName", "");
        committeeName = appConfig.getProperty("committeeName", "");
        countriesList = countryListParser(appConfig.getProperty("countriesList", "[]"));
        primaryColor = colorParser(appConfig.getProperty("primaryColor", "0, 0, 0, 0"));
        firstAccent = colorParser(appConfig.getProperty("firstAccent", "0, 0, 0, 0"));
        secondAccent = colorParser(appConfig.getProperty("secondAccent", "0, 0, 0, 0"));
        topic = appConfig.getProperty("topic", "");
        numberOfSpeakers = Integer.parseInt(appConfig.getProperty("numberOfSpeakers", "0"));
        sessionMode = appConfig.getProperty("sessionMode", "black");
        resolutionTitle = appConfig.getProperty("resolutionTitle", "");
        totalTimeSec = Integer.parseInt(appConfig.getProperty("totalTimeSec", "0"));
        timePerSpeakerSec = Integer.parseInt(appConfig.getProperty("timePerSpeakerSec", "0"));
        //logoFile = get(appConfig.getProperty("logoFile", ""));
    }

    /**
     * Save the current variables/configurations as an XML file
     *
     * @param configFile the directory path to save the XML file in
     */
    void saveXMLConfig(File configFile){
        appConfig.setProperty("conferenceName", conferenceName);
        appConfig.setProperty("committeeName", committeeName);
        appConfig.setProperty("countriesList", countriesList.toString());
        appConfig.setProperty("primaryColor", primaryColor.getRed() + ", "
                + primaryColor.getGreen() + ", " + primaryColor.getBlue() + ", "
                + primaryColor.getOpacity());
        appConfig.setProperty("firstAccent", firstAccent.getRed() + ", "
                + firstAccent.getGreen() + ", " + firstAccent.getBlue() + ", "
                + firstAccent.getOpacity());
        appConfig.setProperty("secondAccent", secondAccent.getRed() + ", "
                + secondAccent.getGreen() + ", " + secondAccent.getBlue() + ", "
                + secondAccent.getOpacity());
        appConfig.setProperty("topic", topic);
        appConfig.setProperty("numberOfSpeakers", Integer.toString(numberOfSpeakers));
        appConfig.setProperty("sessionMode", sessionMode);
        if(currentSpeaker != null) {
            appConfig.setProperty("currentSpeaker", currentSpeaker.getCountryName());
        } else {
            appConfig.setProperty("currentSpeaker", "");
        }
        appConfig.setProperty("speakersList", speakersList.toString());
        appConfig.setProperty("resolutionTitle", resolutionTitle);
        appConfig.setProperty("totalTimeSec", Integer.toString(totalTimeSec));
        appConfig.setProperty("timePerSpeakerSec", Integer.toString(timePerSpeakerSec));
        if(logoFile != null) {
            appConfig.setProperty("logoFile", logoFile.toString());
        } else {
            appConfig.setProperty("logoFile", "");
        }
        try {
            appConfig.storeToXML(new FileOutputStream(configFile), "Configuration to xml file");
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Parses the string object in properties and turns it into a color object
     *
     * @param colorString The color in the form of a string
     * @return The color in the form of a color object
     */
    private static Color colorParser(String colorString) {
        // first splits the string into a list of strings at ", "
        List<String> args = Arrays.asList(colorString.split(", "));

        // and then returns it as a new Color object with the list args as its arguments
        return new Color(Double.parseDouble(args.get(0)), Double.parseDouble(args.get(1)),
                Double.parseDouble(args.get(2)), Double.parseDouble(args.get(3)));
    }

    /**
     * Turns a list of string in string form back into a list object
     *
     * @param listString The list in string form
     * @return The list as a List of String objects
     */
    private static List<String> stringListParser(String listString) {
        // define variables and split the list
        // takes the substring without the first and last brackets, then splits it at ", ",
        // turns it into an ArrayList, and then returns it
        try {
            return new ArrayList<>(Arrays.asList(listString.substring(1, listString
                    .length() - 1).split(", ")));
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds: " + e.getMessage());
            return null;
        }
    }

    /**
     * turns a country in string form back into a Country object
     *
     * @param countryString The country in string form
     * @return A Country object from the list
     */
    private static Country countryParser(String countryString) {
        // minimum of 5 commas unless the country is null
        if(countryString.length() < 6) {
            return null;
        } else {
            // first splits the string into a list of strings at ", "
            List<String> args = Arrays.asList(countryString.split(", "));
            // and then returns it as a Country object with the list args as its arguments
            try {
                return new Country(args.get(0), Boolean.parseBoolean(args.get(1)), Boolean.parseBoolean
                        (args.get(2)), Integer.parseInt(args.get(3)), Boolean.parseBoolean(args.get(4)),
                        Boolean.parseBoolean(args.get(5)), Boolean.parseBoolean(args.get(6)));
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Index out of bounds: " + e.getMessage());
                return null;
            } catch (NumberFormatException e) {
                System.err.println("Number format exception: " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * turns the list of countries which is in string form back into a list of Country
     * objects
     *
     * @param countryListString The list of countries as a string object
     * @return The list as a list of Country objects
     */
    private static List<Country> countryListParser(String countryListString) {
        // defines the returned list as a variable
        List<Country> returnList = new ArrayList<>();

        // if country list is empty, return an empty array list
        if(countryListString.length() == 2) {
            return returnList;
        } else {
            // takes the substring without the first 2 brackets and the last 2 brackets, then splits
            // it at "], [" and puts it into a list of strings
            try {
                List<String> countryList = Arrays.asList(countryListString.substring(2,
                        countryListString.length() - 2)
                        .split("\\], \\["));

                for (String i : countryList) {
                    returnList.add(countryParser(i));
                }
                return returnList;
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Index out of bounds: " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * Turns the time in seconds to a string in human-readable format
     *
     * @param time The time in seconds as an int
     * @return The time in a human readable string
     */
    private static String timeParser(int time) {
        String minutes;
        String seconds;

        try {
            // divides the string by 60 to get minutes
            minutes = Integer.toString(time / 60);
            // gets the time modulo 60 for the seconds
            seconds = Integer.toString(time % 60);
        } catch (NumberFormatException e) {
            System.err.println("Number format exception: " + e.getMessage());
            return null;
        }
        // returns the string
        return minutes + ":" + seconds;
    }


}
