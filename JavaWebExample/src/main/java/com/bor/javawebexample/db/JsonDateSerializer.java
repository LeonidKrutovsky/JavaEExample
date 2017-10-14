/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.db;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author leon
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
    
    @Override
    public void serialize(Date t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(t);
        
        jg.writeStartObject();
        jg.writeObjectField("day", cal.get(Calendar.DAY_OF_MONTH));
        jg.writeObjectField("month", cal.get(Calendar.MONTH));
        jg.writeObjectField("year", cal.get(Calendar.YEAR));
        jg.writeEndObject();
    }
}
