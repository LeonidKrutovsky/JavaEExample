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
        <script src="res/phoneValidator.js" type="text/javascript"></script>
        <link href="./res/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <form id = "PhonebookForm" class="pure-form pure-form-aligned" action="phonebookEditor" method="post"> 
            <fieldset>
                <legend>Edit</legend>
                <div class="pure-control-group">
                    <label for="firstname">Firstname</label>
                    <input id="firstname" name="firstname" type="text" placeholder="Firstname" value="Leonid" autofocus required>                            
                </div>

                <div class="pure-control-group">
                    <label for="lastname">Lastname</label>
                    <input id="lastname" name="lastname" type="text" placeholder="Lastname" value="Krutovsky" required>                            
                </div>

                <div class="pure-control-group">
                    <label for="work_phone">Work phone</label>
                    <input id="work_phone" type="tel"  pattern="\d{3}-\d{3}-\d{2}-\d{2}" value="923-407-90-09"  placeholder="xxx-xxx-xx-xx" name="work_phone" required>                            
                </div>

                <div class="pure-control-group">
                    <label for="mobile_phone">Mobile phone</label>
                    <input id="mobile_phone" type="tel"  pattern="\d{3}-\d{3}-\d{2}-\d{2}" value="923-407-90-09" placeholder="xxx-xxx-xx-xx" name="mobile_phone" required>                            
                </div>

                <div class="pure-control-group">
                    <label for="email">E-mail</label>
                    <input id="email" type="email" name="email" placeholder="email" value="it.leon@docsis.ru" pattern ="\S{1,30}@\S{1,10}$" required>                            
                </div>

                <div class="pure-control-group">
                    <label for="birthdate">Birthdate</label>
                    <input id="birthdate" type="date" name="birthdate" value="2017-02-10" required>                            
                </div>

                <div class="pure-controls">                    
                    <input type="button" class="pure-button" name ="clear" value="clear"/>
                    <input type="submit" class="pure-button" name ="add" value="add"/>
                    <input type="button" class="pure-button" name ="send" value="send"/>                    
                </div>                         
            </fieldset>                    
        </form>
<br><br><br>
    <legend>Debug</legend>
        <p><textarea rows="30" cols="50" readonly="true" name="debug">${debugStr}</textarea></p>
    </body>
</html>
