package de.flxplzk.frontend.backend.domain;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = Employee_.ID)
    private long id;

    @Column(name = Employee_.FIRST_NAME)
    private String firstName;

    @Column(name = Employee_.LAST_NAME)
    private String lastName;

    @Column(name = Employee_.EMPLOYEE_DATE)
    private LocalDate employeeDate;

    @Column(name = Employee_.UNEMPLOYEE_DATE)
    private LocalDate unemployeeDate;

    @ManyToOne
    private EmployeeProfile employeeProfile;

    @Column(name = Employee_.CREATION_DATE)
    private LocalDateTime created;

    public Employee() {
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equal(firstName, employee.firstName) &&
                Objects.equal(lastName, employee.lastName) &&
                Objects.equal(employeeDate, employee.employeeDate) &&
                Objects.equal(unemployeeDate, employee.unemployeeDate) &&
                Objects.equal(employeeProfile, employee.employeeProfile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, lastName, employeeDate, unemployeeDate, employeeProfile);
    }

    public static class Employee_ implements ColumnDefinition {
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String EMPLOYEE_DATE = "EMPLOYEE_DATE";
        public static final String UNEMPLOYEE_DATE = "UNEMPLOYEE_DATE";
    }
}
