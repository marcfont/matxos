<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Registration - ${title} </title>
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
            <span>Inscripcions obertes nom&eacute;s per persones VIP (socis)</span>
            <br>
            <h1>El teu DNI no esta habilitat per a la inscripci&oacute; VIP</h1>
            <br>
            <span>Si creus que hauries de poder, contacte amb el CET</span>
        </div>
    </div>
</div>
</body>
</html>