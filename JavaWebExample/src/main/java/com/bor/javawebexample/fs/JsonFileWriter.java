/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.fs;

import com.bor.javawebexample.db.PhonebookRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leon
 */
public class JsonFileWriter {
    private File jsondataFile = new File(System.getProperty("user.home") + "/jsondata.txt");
    ObjectMapper mapper = new ObjectMapper();
    
    public void saveRecord(PhonebookRecord record) {        
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try (FileWriter writer = new FileWriter(jsondataFile, true)) {
            mapper.writeValue(writer, record);
        } catch (IOException ex) {
            Logger.getLogger(JsonFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
