/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.db;

/**
 *
 * @author leon
 */
public class DbConfig {

    private String databaseUrl;
    private String user;
    private String password;
    
    public DbConfig() {}
    
    public DbConfig(String databaseUrl, String user, String passord) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = passord;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
