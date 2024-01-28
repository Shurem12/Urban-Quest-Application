package com.techelevator.model.searchlandmark.openinghours;

import com.techelevator.model.searchlandmark.openinghours.periods.Periods;

import java.util.List;

public class CurrentOpeningHours {

    private String openNow;

    private List<Periods> periods;

    private List<String> weekdayDescriptions;

    public String getOpenNow() {
        return openNow;
    }

    public void setOpenNow(String openNow) {
        this.openNow = openNow;
    }

    public List<Periods> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Periods> periods) {
        this.periods = periods;
    }

    public List<String> getWeekdayDescriptions() {
        return weekdayDescriptions;
    }

    public void setWeekdayDescriptions(List<String> weekdayDescriptions) {
        this.weekdayDescriptions = weekdayDescriptions;
    }
}
