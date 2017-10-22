/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author leon
 */
public class RouteManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteManager.class);
    private final String mainDir;

    public RouteManager(String mainDir, String logFilePath) {

        this.mainDir = mainDir;
        String schemaUrl = getClass().getClassLoader().getResource("/PhonebookRecordSchema.json").getPath();
        PhonebookRecordValidator.loadSchema(schemaUrl);
        try {
            start();
        } catch (Exception ex) {
            LOGGER.warn(ex.toString());
        }
        
        System.setProperty("log.name", logFilePath);
    }

    private void start() throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("file:" + mainDir + "/work?doneFileName=${file:name.noext}.ready&delete=true")
                        .process(new PhonebookRecordValidator())
                        //.to("file:/home/leon/JEExample/data")
                        .process(new PhonebookRecordEnricher())
                        .to("file:" + mainDir + "/data")
                        .process(new PhonebookDbTransmitter());
            }
        });
        context.start();
    }

}
