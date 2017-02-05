<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Control curs</title>
</head>
<body>
<form action="/login" method="POST">
    <div><label> User: <input type="text" name="user" value="cet"/></label></div>
    <div><label> Password: <input type="password" name="password" value="hola"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>