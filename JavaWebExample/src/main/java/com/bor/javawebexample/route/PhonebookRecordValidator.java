/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.ParseException;
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
public class PhonebookRecordValidator implements Processor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static JsonSchema schema = null;
    private static final Logger LOGGER = Logger.getLogger(PhonebookRecordValidator.class.getName());
    private static final String SEPARATOR = System.getProperty("line.separator");
    private static final String LOG_FORMAT = "Take file: {0}. Record count: {1}, valid records: {2}";

    public static boolean loadSchema(String url) {        
        File schemaFile = new File(url);
        try {
            String js = new String(Files.readAllBytes(Paths.get(schemaFile.getAbsolutePath())));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode fstabSchema = mapper.readTree(js);            
            final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            schema = factory.getJsonSchema(fstabSchema);
            return true;
        } catch (IOException | ProcessingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static ValidationResult validate(String json) {

        ValidationResult result = new ValidationResult();
        JsonNode actualObj;
        ProcessingReport report = null;
        try {
            actualObj = MAPPER.readTree(json);
            report = schema.validate(actualObj);
            if (!report.isSuccess()) {
                result.report = report.toString();
                return result;
            }

            if (!isPhonesValid(actualObj, result)) {
                return result;
            }
            
            if (!isDateValid(actualObj, result)) {
                return result;
            }

        } catch (IOException | ProcessingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private static boolean isPhonesValid(JsonNode node, ValidationResult result) {
        
        String workPhone = node.get("workPhone").textValue();
        String mobilePhone = node.get("mobilePhone").textValue();

        if (!workPhone.startsWith(mobilePhone.substring(0, 2))) {
            result.report = "--- BEGIN MESSAGES --- \n"
                    + "Error phone numbers: \n"
                    + "First three digit must be equal \n"
                    + workPhone + "\n" + mobilePhone + "\n"
                    + "---  END MESSAGES  ---";

            return result.isValid = false;
        }
        return result.isValid = true;
    }

    private static boolean isDateValid(JsonNode node, ValidationResult result) {
        
        String dateString = node.get("birthdate").textValue();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = format.parse(dateString);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            cal.setTime(new Date());
            int nowYaer = cal.get(Calendar.YEAR);
            
            if (year < 1900 || year > nowYaer) {
                result.report = "\n" + "--- BEGIN MESSAGES --- \n"
                        + "Error birthday year: \n"
                        + "Must be in range from 1900 to " + nowYaer                    
                        + "\n---  END MESSAGES  ---";
                return result.isValid = false;
            }
        } catch (ParseException ex) {            
            LOGGER.log(Level.SEVERE, null, ex);
            result.report = "Parsing date error";
            return result.isValid = false;
        }
        return result.isValid = true;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        
        if (exchange.isFailed()) {
            LOGGER.log(Level.SEVERE, null, "Exchange failed: " + exchange.getProperty(Exchange.EXCEPTION_CAUGHT));            
            return; 
        }
        
        String fileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        
        String body = exchange.getIn().getBody(String.class);
        String[] list = body.split(SEPARATOR); 
        
        List<String> outList = new ArrayList<>();
        for (String str : list) {
            ValidationResult res = validate(str);
            if (res.isValid)
                outList.add(str);
            else
                LOGGER.log(Level.INFO, null, res.report);
        }
        
        String log = MessageFormat.format(LOG_FORMAT, fileName, list.length, outList.size()); 
        LOGGER.log(Level.SEVERE, null, log);
        
        exchange.getOut().setBody(String.join(SEPARATOR, outList));
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());         
    }

}
