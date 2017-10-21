/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample;


import com.bor.javawebexample.db.PhonebookRecord;
import com.bor.javawebexample.fs.Configuration;
import com.bor.javawebexample.fs.JsonFileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
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

    private JsonFileWriter fileWriter;
    private static String status = "";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);        
        Configuration conf = (Configuration) getServletContext().getAttribute("config");
        fileWriter = new JsonFileWriter(conf.getJsondataPath());
        JsonFileWriter.prepareDirs(conf.getMainDirPath());        
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("statusStr", status);
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
            fileWriter.saveRecord(record);  
            status = "";
        } else if (request.getParameter("clear") != null) {
            fileWriter.clear();
            status = "cleared";
        } else if (request.getParameter("send") != null) {
            fileWriter.sendToWork();
            status = "sent to work";
        }
        processRequest(request, response);
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
