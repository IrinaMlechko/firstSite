<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your service - Registration</title>
</head>
<body>
<br/>
<form action="${pageContext.request.contextPath}/controller"  method="post">
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
    <span style="font-size: 12px; color: #888;">(Format: dd-MM-yyyy)</span> <!-- Текст о формате даты -->
    <br/>
    <input type="submit" name="submit" value="Register"/>
    <br/>
    ${failed}
</form>
</body>
</html>