<%-- 
    Document   : PhonebookEditor
    Created on : Oct 13, 2017, 5:41:31 PM
    Author     : leon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Phonebook Editor</title>        
        <script type="text/javascript">
            function initForm() {
                var form = document.querySelector('#PhonebookForm');
                var tel1 = form.querySelector('input[name="work_phone"]');
                var tel2 = form.querySelector('input[name="mobile_phone"]');

                function validatePhone() {
                    if (tel1.value.slice(0, 3) !== tel2.value.slice(0, 3)) {
                        tel2.setCustomValidity("Error: first three digits of mobile and work phone must bi equal");
                    } else {
                        tel2.setCustomValidity("");
                    }
                }
                form.addEventListener('submit', validatePhone);
                tel1.addEventListener('change', validatePhone);
                tel2.addEventListener('change', validatePhone);
            }
            document.addEventListener('DOMContentLoaded', initForm);
        </script>
    </head>
    <body>
        <h1>Phonebook Editor</h1>  
        <form id = "PhonebookForm" action="phonebookEditor" method="post">  
            <table>                
                <tr><td>Firstname</td><td><input placeholder="Firstname" name="firstname" maxlength="10" autofocus required/></td></tr>
                <tr><td>Lastname</td><td><input placeholder="Lastname" name="lastname" maxlength="20" required/></td></tr>
                <tr><td>Work phone</td><td><input type="tel"  pattern="\d{3}-\d{3}-\d{2}-\d{2}"  placeholder="xxx-xxx-xx-xx" name="work_phone" required /></td></tr>
                <tr><td>Mobile phone</td><td><input type="tel" pattern="\d{3}-\d{3}-\d{2}-\d{2}" placeholder="xxx-xxx-xx-xx" name="mobile_phone" required /></td></tr>
                <tr><td>E-mail</td><td><input type="email" name="email" pattern ="\S{1,30}@\S{1,10}$" required/></td></tr>
                <tr><td>Birthdate</td><td><input type="date" name="birthdate" required/></td></tr>
                <tr><td>Job</td><td><input type="text" placeholder="BoR" name="job"required/></td></tr> 
            </table>  
            <table>
                <tr><td><input type="submit" name ="clear" value="clear"/></td>
                    <td><input type="submit" name ="add" value="add"/></td>
                    <td><input type="submit" name ="send" value="send"/></td>
                </tr> 
            </table>            
        </form>        
    </body>
</html>
