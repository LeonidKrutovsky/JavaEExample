/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.route;

import com.bor.javawebexample.db.JobRecord;
import com.bor.javawebexample.db.ORMLiteUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author leon
 */
public class PhonebookRecordEnricher implements Processor {

    private static final String SEPARATOR = System.getProperty("line.separator");
    private final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PhonebookRecordEnricher.class);

    public PhonebookRecordEnricher() {

        MAPPER.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        
        String body = exchange.getIn().getBody(String.class);
        String[] list = body.split(SEPARATOR);

        ObjectNode objectNode = null;
        List<String> outList = new ArrayList<>();
        for (String str : list) {
            try {
                objectNode = (ObjectNode)MAPPER.readTree(str);
                JobRecord matchObj = new JobRecord();
                matchObj.setLastname(objectNode.get("lastname").textValue());
                matchObj.setFirstname(objectNode.get("firstname").textValue());
                List<JobRecord> jobs = ORMLiteUtils.find(JobRecord.class, matchObj);
                
                if (!jobs.isEmpty()) {
                    JobRecord jobRecord = jobs.get(0);
                    objectNode.put("job", jobRecord.getJob());
                } else {
                    objectNode.put("job", "workless!");
                }
                String textValue = MAPPER.writeValueAsString(objectNode);
                outList.add(textValue);
                LOGGER.info("Enrich file by a job value " + objectNode.get("job").textValue());
            } catch (IOException ex) {                
                LOGGER.warn(ex.toString());
            }
        }

        exchange.getOut().setBody(String.join(SEPARATOR, outList));
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }
        
}
