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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leon
 */
public class JsonFileWriter {

    private final File jsondataFile;
    private final ObjectMapper mapper = new ObjectMapper();
    private static File workDir;
    private static File prepareDir;
    private static File dataDir;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy_HHmm");
    private static final Logger LOGGER = Logger.getLogger(JsonFileWriter.class.getName());

    public static void prepareDirs(String mainDirPath) {
        workDir = new File(mainDirPath + File.separator + "work");
        prepareDir = new File(mainDirPath + File.separator + "prepare");
        dataDir = new File(mainDirPath + File.separator + "data");
        if (!workDir.exists()) {
            workDir.mkdirs();
        }
        if (!prepareDir.exists()) {
            prepareDir.mkdirs();
        }
        if (!dataDir.exists()) {
            dataDir.mkdirs();
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
            writer.write(json + System.getProperty("line.separator"));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        Date today = Calendar.getInstance().getTime();
        File file = new File(prepareDir.getAbsolutePath() + File.separator + "data_" + DATE_FORMAT.format(today) + ".json");

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(json + System.getProperty("line.separator"));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void sendToWork() {
        File[] files = prepareDir.listFiles();
        List<String> stringList = new ArrayList<>();

        for (File file : files) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
                stringList.addAll(lines);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }

        if (stringList.isEmpty()) {
            LOGGER.log(Level.INFO, null, "No file to send!");
            return;
        }

        Date today = Calendar.getInstance().getTime();
        String destFilePath = workDir.getAbsolutePath() + File.separator + "json_" + DATE_FORMAT.format(today);
        File destinationFile = new File(destFilePath + ".txt");

        if (destinationFile.exists()) {
            destinationFile.delete();
        }

        try (FileWriter writer = new FileWriter(destinationFile, true)) {
            for (String line : stringList) {
                writer.write(line + System.getProperty("line.separator"));
            }

            File fileFlag = new File(destFilePath + ".ready");
            fileFlag.createNewFile();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        for (File file : files) {
            if (!file.delete()) {
                LOGGER.log(Level.SEVERE, null, "Can`n delete file " + file.getName());
            }
        }
    }

    public void clear() {
        File[] files = prepareDir.listFiles();
        for (File file : files) {
            if (!file.delete()) {
                LOGGER.log(Level.SEVERE, null, "Can`n delete file " + file.getName());
            }
        }
    }
}
