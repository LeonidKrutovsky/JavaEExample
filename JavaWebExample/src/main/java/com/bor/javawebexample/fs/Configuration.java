/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.fs;

import com.bor.javawebexample.db.DbConfig;
/**
 *
 * @author leon
 */
public class Configuration {
    private String mainDirPath;
    private String jsondataPath;    
    private String logFilePath;
    private DbConfig dbConfig;

    public Configuration() {
    }
    
    public Configuration(String jsondataPath, String mainDirPath, String logFilePath, DbConfig dbConfig) {
        this.jsondataPath = jsondataPath;
        this.mainDirPath = mainDirPath;
        this.logFilePath = logFilePath;
        this.dbConfig = dbConfig;
    }

    public String getMainDirPath() {
        return mainDirPath;
    }

    public void setMainDirPath(String mainDirPath) {
        this.mainDirPath = mainDirPath;
    }

    public String getJsondataPath() {
        return jsondataPath;
    }

    public void setJsondataPath(String jsondataPath) {
        this.jsondataPath = jsondataPath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }    
}
