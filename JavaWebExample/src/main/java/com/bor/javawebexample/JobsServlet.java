/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample;

import com.bor.javawebexample.db.JobRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leon
 */
@WebServlet(name = "JobsServlet", urlPatterns = {"/jobsAndPhonebook"})
public class JobsServlet extends HttpServlet {
    
    private List<JobRecord> jobList = new ArrayList<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("jobList", jobList);
        request.getRequestDispatcher("/JobsAndPhonebookTables.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("add") != null) {            
            JobRecord job = createJobRecord(request);
            jobList.add(job);
            
        }
        
        processRequest(request, response);
    }

    private static JobRecord createJobRecord(HttpServletRequest request) {
        JobRecord job = new JobRecord();
        
        String temp = request.getParameter("firstname");
        if (temp != null && !temp.isEmpty()) job.setFirstname(temp);
        
        temp = request.getParameter("lastname");
        if (temp != null && !temp.isEmpty()) job.setLastname(temp);
        
        temp = request.getParameter("address");
        if (temp != null && !temp.isEmpty()) job.setAddress(temp);
        
        temp = request.getParameter("job");
        if (temp != null && !temp.isEmpty()) job.setJob(temp);        

        return job;
    }

}
