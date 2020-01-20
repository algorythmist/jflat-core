package com.tecacet.jflat.domain;

public class Employee {

    private String userId;

    //this should be "First Name, Last Name"
    private String employeeName;

    //this should be Worked Minutes/60
    private String workedHours;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(String workedHours) {
        this.workedHours = workedHours;
    }
}
