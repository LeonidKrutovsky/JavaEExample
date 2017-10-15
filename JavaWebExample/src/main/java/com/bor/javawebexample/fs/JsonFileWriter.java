/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.fs;

import com.bor.javawebexample.db.PhonebookRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leon
 */
public class JsonFileWriter {

    private File jsondataFile;
    private ObjectMapper mapper = new ObjectMapper();
    private static File workDir;
    private static File prepareDir;
    private static Logger logger = Logger.getLogger(JsonFileWriter.class.getName());

    public static void prepareDirs(String mainDirPath) {
        workDir = new File(mainDirPath + File.separator + "work");
        prepareDir = new File(mainDirPath + File.separator + "prepare");
        if (!workDir.exists()) {
            workDir.mkdirs();
        }
        if (!prepareDir.exists()) {
            prepareDir.mkdirs();
        }
    }

    public JsonFileWriter(String jsondataFilePath) {
        jsondataFile = new File(jsondataFilePath);
        mapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
    }

    public void saveRecord(PhonebookRecord record) throws JsonProcessingException {
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(record);
        
        try (FileWriter writer = new FileWriter(jsondataFile, true)) {
            writer.write(json);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        DateFormat df = new SimpleDateFormat("ddMMyy_HHmm");
        Date today = Calendar.getInstance().getTime();
        File file = new File(prepareDir.getAbsolutePath() + File.separator + "data_" + df.format(today) + ".json" );

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(json);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
