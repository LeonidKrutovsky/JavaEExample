/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.route;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author leon
 */
public class RouteManager {

    public RouteManager() {
        
        String schemaUrl = getClass().getClassLoader().getResource("/PhonebookRecordSchema.json").getPath();
        PhonebookRecordValidator.loadSchema(schemaUrl);
        try {
            start();
        } catch (Exception ex) {
            Logger.getLogger(RouteManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void start() throws Exception {
        
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("file:/home/leon/JEExample/work?doneFileName=${file:name.noext}.ready&delete=true")
                        .process(new PhonebookRecordValidator())
                        .to("file:/home/leon/JEExample/data")
                        .process(new PhonebookRecordProcessor())
                        .to("file:/home/leon/JEExample/data");
            }
        });
        context.start();
    }

}
