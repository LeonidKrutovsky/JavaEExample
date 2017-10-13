<%-- 
    Document   : JobsAndPhonebookTables
    Created on : Oct 13, 2017, 5:36:39 PM
    Author     : leon
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page import="com.bor.javawebexample.db.JobRecord" %>
<%@page import="com.bor.javawebexample.db.PhonebookRecord"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs and phonebook</title>
        <link href="./res/main.css" rel="stylesheet" type="text/css">
    </head>
    <body>


        <table width="70%"> <tr><td>

                    <form class="pure-form pure-form-aligned" action="jobEditor" method="post"> 
                        <fieldset>
                            <legend>Edit</legend>
                            <div class="pure-control-group">
                                <label for="firstname">Firstname</label>
                                <input id="firstname" name="firstname" type="text" placeholder="Firstname" autofocus required>                            
                            </div>
                            <div class="pure-control-group">
                                <label for="lastname">Lastname</label>
                                <input id="lastname" name="lastname" type="text" placeholder="Lastname" required>                            
                            </div>
                            <div class="pure-control-group">
                                <label for="job">Job</label>
                                <input id="job" name="job" type="text" placeholder="Job" required>                            
                            </div>
                            <div class="pure-control-group">
                                <label for="address">Address</label>
                                <input id="address" name="address" type="text" placeholder="Address" required>                            
                            </div>
                            <div class="pure-controls">
                                <input type="submit" class="pure-button pure-button-primary" name ="add" value="add"/>
                            </div>                         
                        </fieldset>                    
                    </form>
                </td>
                <td>

                    <form class="pure-form pure-form-aligned" action="jobEditor" method="post"> 
                        <fieldset>
                            <legend>Search</legend>
                            <div class="pure-control-group">
                                <label for="firstname">Firstname</label>
                                <input id="firstname" name="firstname" type="text" placeholder="Firstname">                            
                            </div>
                            <div class="pure-control-group">
                                <label for="lastname">Lastname</label>
                                <input id="lastname" name="lastname" type="text" placeholder="Lastname">                            
                            </div>
                            <div class="pure-control-group">
                                <label for="job">Job</label>
                                <input id="job" name="job" type="text" placeholder="Job">                            
                            </div>
                            <div class="pure-control-group">
                                <label for="address">Address</label>
                                <input id="address" name="address" type="text" placeholder="Address">                            
                            </div>
                            <div class="pure-controls">
                                <input type="submit" class="pure-button pure-button-primary" name ="search" value="search"/>
                            </div>                         
                        </fieldset>                    
                    </form>

                </td></tr>
        </table>

        <table class="pure-table" width="70%" >
            <thead>
                <tr><th>Id</th><th>Lastname</th><th>Firstname</th><th>Job</th>  
                    <th>Address</th></tr>
            </thead>
            <tbody>
                <c:forEach items="${jobList}" var="job">
                    <tr>
                        <td>${job.getId()}</td>
                        <td>${job.getLastname()}</td>
                        <td>${job.getFirstname()}</td>                
                        <td>${job.getJob()}</td>
                        <td>${job.getAddress()}</td>
                    </tr>
                </c:forEach> 
            </tbody>
        </table>

        <br>
        <hr align="left" width="100%" ize="2" />
        <br>

        <table class="pure-table" width="70%">
            <thead>
                <tr><th>Id</th><th>Lastname</th><th>Firstname</th><th>Work phone</th>  
                    <th>Mobile phone</th><th>e-mail</th><th>Birthdate</th><th>Job</th></tr>
            </thead>
            <tbody>
                <c:forEach items="${phonebookList}" var="phone">
                    <tr>
                        <td>${phone.getId()}</td>
                        <td>${phone.getLastname()}</td>
                        <td>${phone.getFirstname()}</td>   
                        <td>${phone.getWorkPhone()}</td>
                        <td>${phone.getMobilePhone()}</td>
                        <td>${phone.getEmail()}</td>
                        <td>${phone.getStringBirthdate()}</td>
                        <td>${phone.getJob()}</td>
                    </tr>
                </c:forEach> 
            </tbody>
        </table>
        
    </body>
</html>
