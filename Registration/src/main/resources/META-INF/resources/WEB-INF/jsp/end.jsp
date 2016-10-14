<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Inscripcions  - ${title} </title>
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
            <h2>Felicitats!!! ja est&aacute;s inscrit</h2>
            <br>
            <h1><b>INSCRIPCI&Oacute; FINALIZADA CORRECTAMENT</b></h1>
            <br>
            <span>Aqu&iacute; tens la <a href="/registration/race/${race}/registrations">llista d'inscrits</a> on ja surts!</span>
        </div>
    </div>
</div>
</body>
</html>