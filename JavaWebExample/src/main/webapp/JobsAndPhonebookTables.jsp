<%-- 
    Document   : JobsAndPhonebookTables
    Created on : Oct 13, 2017, 5:36:39 PM
    Author     : leon
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Job Table</h2>

        <h3>Edit</h3>  
        <form action="jobsAndPhonebook" method="post">  
            <table>                
                <tr><td><input type="text" placeholder="Firstname" name="firstname" autofocus required/></td></tr>
                <tr><td><input type="text" placeholder="Lastname" name="lastname" required/></td></tr>
                <tr><td><input type="text" placeholder="Job" name="job"required/></td></tr>
                <tr><td><input type="text" placeholder="Address" name="address"required/></td></tr>

                <tr><td><input type="submit" name ="add" value="add"/></td></tr>  
            </table>  
        </form>
        
        
        <table border="1">
        <tr><th>Id</th><th>Lastname</th><th>Firstname</th><th>Job</th>  
            <th>Address</th></tr>

        <c:forEach items="${jobList}" var="job">
            <tr>
                <td>${job.getId()}</td>
                <td>${job.getLastname()}</td>
                <td>${job.getFirstname()}</td>                
                <td>${job.getJob()}</td>
                <td>${job.getAddress()}</td>
            </tr>
        </c:forEach> 
    </table>
    </body>
</html>
