/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample;

import com.bor.javawebexample.db.ORMLiteUtils;
import com.bor.javawebexample.db.PhonebookRecord;
import com.bor.javawebexample.fs.JsonFileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leon
 */
@WebServlet(name = "PhonebookServlet", urlPatterns = {"/phonebookEditor"})
public class PhonebookServlet extends HttpServlet {

    private String debugStr = null;
    private JsonFileWriter fileWriter = new JsonFileWriter();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/PhonebookEditor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("add") != null) {
            PhonebookRecord record = createRecord(request);
            ORMLiteUtils.create(PhonebookRecord.class, record); //for debug

            fileWriter.saveRecord(record);
            request.setAttribute("debugStr", debugStr);

            processRequest(request, response);
        } else if (request.getParameter("clear") != null) {

        }

    }

    private static PhonebookRecord createRecord(HttpServletRequest request) {
        PhonebookRecord record = new PhonebookRecord();

        String temp = request.getParameter("firstname");
        if (temp != null && !temp.isEmpty()) {
            record.setFirstname(temp);
        }

        temp = request.getParameter("lastname");
        if (temp != null && !temp.isEmpty()) {
            record.setLastname(temp);
        }

        temp = request.getParameter("work_phone");
        if (temp != null && !temp.isEmpty()) {
            record.setWorkPhone(temp);
        }

        temp = request.getParameter("mobile_phone");
        if (temp != null && !temp.isEmpty()) {
            record.setMobilePhone(temp);
        }

        temp = request.getParameter("email");
        if (temp != null && !temp.isEmpty()) {
            record.setEmail(temp);
        }

        temp = request.getParameter("birthdate");
        if (temp != null && !temp.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                record.setBirthdate(format.parse(temp));
            } catch (ParseException ex) {
                Logger.getLogger(PhonebookRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return record;
    }

}
