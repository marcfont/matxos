<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Error  - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px;">
        <div class="col-md-12" style="background-color: darkcyan; font-size: xx-large; color: azure; padding: 10%;">
            ${title}
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2>Algo ha anat malament :(</h2>
            <br>
            <h1>Aquesta p&agrave;gina no existeix</h1>
            <br>
            <span>Torna-ho a començar <a href="http://www.marxos.cat">aqu&iacute;</a></span>
        </div>
    </div>
</div>
</body>
</html>