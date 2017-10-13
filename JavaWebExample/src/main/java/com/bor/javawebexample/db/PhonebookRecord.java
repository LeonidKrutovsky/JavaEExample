/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leon
 */
@DatabaseTable(tableName = "Phonebook")
public class PhonebookRecord {    

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(width = 20)
    private String lastname;    
    
    @DatabaseField(width = 10)
    private String firstname;
    
    @DatabaseField(width = 13)
    private String workPhone;
    
    @DatabaseField(width = 13)
    private String mobilePhone;

    @DatabaseField(width = 40)
    private String email;    

    @DatabaseField(dataType = DataType.DATE)
    private Date birthdate;    
    

    @DatabaseField
    private String job;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStringBirthdate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String retVal = format.format(birthdate);
        return retVal == null ? "NULL" : retVal;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
   
}
