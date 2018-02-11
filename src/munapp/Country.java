package munapp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class for project mun-app.
 * Created by Yvan on 10-Feb-18.
 * Copyright (C) 2018.
 */
public class Country {
    // the attributes of class Country
    private final SimpleStringProperty countryName;
    private SimpleBooleanProperty present;
    private SimpleBooleanProperty voting;
    private SimpleIntegerProperty timesSpoken;
    private SimpleBooleanProperty author;
    private SimpleBooleanProperty coauthor;
    private SimpleBooleanProperty sponsor;

    // the actual constructor
    public Country (String countryName, boolean present, boolean voting, int timesSpoken,
                    boolean author, boolean coauthor, boolean sponsor) {
        this.countryName = new SimpleStringProperty(countryName);
        this.present = new SimpleBooleanProperty(present);
        this.voting = new SimpleBooleanProperty(voting);
        this.timesSpoken = new SimpleIntegerProperty(timesSpoken);
        this.author = new SimpleBooleanProperty(author);
        this.coauthor = new SimpleBooleanProperty(coauthor);
        this.sponsor = new SimpleBooleanProperty(sponsor);
    }

    public String getCountryName() {
        return countryName.get();
    }

    public SimpleStringProperty countryProperty() {
        return countryName;
    }

    public boolean isPresent() {
        return present.get();
    }

    public SimpleBooleanProperty presentProperty() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present.set(present);
    }

    public boolean isVoting() {
        return voting.get();
    }

    public SimpleBooleanProperty votingProperty() {
        return voting;
    }

    public void setVoting(boolean voting) {
        this.voting.set(voting);
    }

    public int getTimesSpoken() {
        return timesSpoken.get();
    }

    public SimpleIntegerProperty timesSpokenProperty() {
        return timesSpoken;
    }

    public void setTimesSpoken(int timesSpoken) {
        this.timesSpoken.set(timesSpoken);
    }

    // if someone spoke, addTimesSpoken is called to increment the number of times they spoke
    public void addTimesSpoken() {
        this.timesSpoken.set((getTimesSpoken() + 1));
    }

    public boolean isAuthor() {
        return author.get();
    }

    public SimpleBooleanProperty authorProperty() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author.set(author);
    }

    public boolean isCoauthor() {
        return coauthor.get();
    }

    public SimpleBooleanProperty coauthorProperty() {
        return coauthor;
    }

    public void setCoauthor(boolean coauthor) {
        this.coauthor.set(coauthor);
    }

    public boolean isSponsor() {
        return sponsor.get();
    }

    public SimpleBooleanProperty sponsorProperty() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor.set(sponsor);
    }

    public String toString() {
        return "[" + countryName.get() + ", " + present.get() + ", " + voting.get() + ", "
                + timesSpoken.get() + ", " + author.get() + ", " + coauthor.get() + ", "
                + sponsor.get() + "]";
    }
}
