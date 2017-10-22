package com.bor.javawebexample.route;


import com.bor.javawebexample.db.ORMLiteUtils;
import com.bor.javawebexample.db.PhonebookRecord;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author leon
 */
public class PhonebookDbTransmitter implements Processor {

    private static final String SEPARATOR = System.getProperty("line.separator");
    private final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PhonebookRecordEnricher.class);

    public PhonebookDbTransmitter() {

        MAPPER.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);
        String[] list = body.split(SEPARATOR);

        for (String str : list) {
            try {
                JsonNode node = MAPPER.readTree(str);
                PhonebookRecord record = MAPPER.treeToValue(node, PhonebookRecord.class);                
                ORMLiteUtils.create(PhonebookRecord.class, record);
                LOGGER.info("inset record to a PhonebookTable");
            } catch (IOException ex) {
                LOGGER.warn(ex.toString());
            }
        }
    }
}