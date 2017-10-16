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
    private static final Logger LOGGER = Logger.getLogger(JsonFileWriter.class.getName());
    private static final DateFormat df = new SimpleDateFormat("ddMMyy_HHmm");

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
            System.err.println("*****saveRecord*****    " + ex);
        }

        Date today = Calendar.getInstance().getTime();
        File file = new File(prepareDir.getAbsolutePath() + File.separator + "data_" + df.format(today) + ".json");

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(json);
        } catch (IOException ex) {
            System.err.println("*****saveRecord*****   " + ex);
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
                System.err.println("*****sendToWork*****   " + ex);
            }
        }
        
        if (stringList.isEmpty()) return;

        Date today = Calendar.getInstance().getTime();
        String destFilePath = workDir.getAbsolutePath() + File.separator + "json_" + df.format(today);
        File destinationFile = new File(destFilePath + ".txt");

        if (destinationFile.exists()) {
            destinationFile.delete();
        }

        try (FileWriter writer = new FileWriter(destinationFile, true)) {
            for (String line : stringList) {
                writer.write(line);
            }
            
            File fileFlag = new File(destFilePath + ".ready");
            fileFlag.createNewFile();
        } catch (IOException ex) {
            System.err.println("*****sendToWork*****   " + ex);
        } 

        for (File file : files) {
            file.delete();
        }
    }
    
    public void clear() {
        File[] files = prepareDir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }
}
