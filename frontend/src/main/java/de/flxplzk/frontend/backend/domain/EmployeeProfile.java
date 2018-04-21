package de.flxplzk.frontend.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EmployeeProfile {

    @Id
    @GeneratedValue
    private long id;
    private String profileName;
    private long weeklyHours;
    private long yearlyHoliday;

    private boolean permanentEmployed;

    public EmployeeProfile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public long getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(long weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public long getYearlyHoliday() {
        return yearlyHoliday;
    }

    public void setYearlyHoliday(long yearlyHoliday) {
        this.yearlyHoliday = yearlyHoliday;
    }

    public boolean isPermanentEmployed() {
        return permanentEmployed;
    }

    public void setPermanentEmployed(boolean permanentEmployed) {
        this.permanentEmployed = permanentEmployed;
    }
}
