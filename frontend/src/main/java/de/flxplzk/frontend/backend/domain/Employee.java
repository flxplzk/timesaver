package de.flxplzk.frontend.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate employeeDate;
    private LocalDate unemployeeDate;

    @ManyToOne
    private EmployeeProfile employeeProfile;

    private double hourAccount;
    private int holidayAccount;

    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate employeeDate, LocalDate unemployeeDate, EmployeeProfile employeeProfile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeDate = employeeDate;
        this.unemployeeDate = unemployeeDate;
        this.employeeProfile = employeeProfile;
    }

    public Employee(String firstName, String lastName, LocalDate employeeDate, LocalDate unemployeeDate, EmployeeProfile employeeProfile, double hourAccount, int holidayAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeDate = employeeDate;
        this.unemployeeDate = unemployeeDate;
        this.employeeProfile = employeeProfile;
        this.hourAccount = hourAccount;
        this.holidayAccount = holidayAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getEmployeeDate() {
        return employeeDate;
    }

    public void setEmployeeDate(LocalDate employeeDate) {
        this.employeeDate = employeeDate;
    }

    public LocalDate getUnemployeeDate() {
        return unemployeeDate;
    }

    public void setUnemployeeDate(LocalDate unemployeeDate) {
        this.unemployeeDate = unemployeeDate;
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
    }

    public double getHourAccount() {
        return hourAccount;
    }

    public void setHourAccount(double hourAccount) {
        this.hourAccount = hourAccount;
    }

    public int getHolidayAccount() {
        return holidayAccount;
    }

    public void setHolidayAccount(int holidayAccount) {
        this.holidayAccount = holidayAccount;
    }
}
