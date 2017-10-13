/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample;

import com.bor.javawebexample.db.JobRecord;
import com.bor.javawebexample.db.ORMLiteUtils;
import com.bor.javawebexample.db.PhonebookRecord;
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
    private List<PhonebookRecord> phonebookList = new ArrayList<>();
    private boolean isCreate = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isCreate) {
            ORMLiteUtils.createTable(JobRecord.class);
            ORMLiteUtils.createTable(PhonebookRecord.class);
            isCreate = true;
        }
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("jobList", jobList);
        request.setAttribute("phonebookList", phonebookList);

        request.getRequestDispatcher("/JobsAndPhonebookTables.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (jobList.isEmpty()) {
            jobList = ORMLiteUtils.getAll(JobRecord.class);
        }
        if (phonebookList.isEmpty()) {
            phonebookList = ORMLiteUtils.getAll(PhonebookRecord.class);
        }

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        JobRecord record = createJobRecord(request);

        if (request.getParameter("add") != null) {
            ORMLiteUtils.create(JobRecord.class,
                     record);
            jobList.add(record);

        } else if (request.getParameter("search") != null) {
            if (isEmptySearchPost(record)) {
                jobList = ORMLiteUtils.getAll(JobRecord.class
                );

            } else {
                jobList = ORMLiteUtils.find(JobRecord.class,
                         record);
            }
        }

        processRequest(request, response);
    }

    private static JobRecord createJobRecord(HttpServletRequest request) {
        JobRecord job = new JobRecord();

        String temp = request.getParameter("firstname");
        if (temp != null && !temp.isEmpty()) {
            job.setFirstname(temp);
        }

        temp = request.getParameter("lastname");
        if (temp != null && !temp.isEmpty()) {
            job.setLastname(temp);
        }

        temp = request.getParameter("address");
        if (temp != null && !temp.isEmpty()) {
            job.setAddress(temp);
        }

        temp = request.getParameter("job");
        if (temp != null && !temp.isEmpty()) {
            job.setJob(temp);
        }

        return job;
    }

    private static boolean isEmptySearchPost(JobRecord record) {
        return record.getLastname() == null
                && record.getFirstname() == null
                && record.getJob() == null
                && record.getAddress() == null;
    }

}
