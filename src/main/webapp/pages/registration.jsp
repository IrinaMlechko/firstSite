<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your service - Registration</title>
</head>
<body>
<br/>
<form action="controller">
    <input type="hidden" name="command" value="signup"/>
    Login: <input type="text" name="login" value=""/>
    <br/>
    Password: <input type="password" name="password" value=""/>
    <br/>
    First Name: <input type="text" name="firstName" value=""/>
    <br/>
    Last Name: <input type="text" name="lastName" value=""/>
    <br/>
    Date of Birth: <input type="text" name="dateOfBirth" value=""/>
    <br/>
    <input type="submit" name="submit" value="Register"/>
</form>
</body>
</html>