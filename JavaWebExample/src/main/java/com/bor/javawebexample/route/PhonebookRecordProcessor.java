/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.route;

import com.bor.javawebexample.db.JobRecord;
import com.bor.javawebexample.db.ORMLiteUtils;
import com.bor.javawebexample.db.PhonebookRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author leon
 */
public class PhonebookRecordProcessor implements Processor {

    private static final String SEPARATOR = System.getProperty("line.separator");
    private final ObjectMapper MAPPER = new ObjectMapper();
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(PhonebookRecordValidator.class.getName());

    public PhonebookRecordProcessor() {

        MAPPER.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        String[] list = body.split(SEPARATOR);

        PhonebookRecord record = null;
        List<String> outList = new ArrayList<>();
        for (String str : list) {
            try {
                record = MAPPER.readValue(str, PhonebookRecord.class);
                JobRecord matchObj = new JobRecord();
                matchObj.setLastname(record.getLastname());
                matchObj.setFirstname(record.getFirstname());
                List<JobRecord> jobs = ORMLiteUtils.find(JobRecord.class, matchObj);
                if (!jobs.isEmpty()) {
                    JobRecord jobRecord = jobs.get(0);
                    record.setJob(jobRecord.getJob());
                } else {
                    record.setJob("workless!");
                }
                ORMLiteUtils.create(PhonebookRecord.class, record);
                outList.add(MAPPER.writeValueAsString(record));
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }

        exchange.getOut().setBody(String.join(SEPARATOR, outList));
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }
}
