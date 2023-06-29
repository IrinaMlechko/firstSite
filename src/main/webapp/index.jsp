<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your service</title>
</head>
<body>
<br/>
<form action="controller">
    <input type="hidden" name="command" value="login"/>
    Login: <input type="text" name="login" value=""/>
    <br/>
    Password: <input type="password" name = "password" value = ""/>
    <input type="submit" name="submit" value="submit"/>
    <br/>
    ${failed}
</form>
</body>
</html>