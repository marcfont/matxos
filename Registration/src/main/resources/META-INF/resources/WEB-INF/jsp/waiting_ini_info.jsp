<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>LLista d'espera - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px;">
        <div class="col-md-12" style="background-color: #FF5640; font-size: xx-large; color: azure; padding: 6%;">
            ${title}
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2>No hi han m&eacute;s places disponibles.</h2>
            <br>
            <h1><b>LLISTA D'ESPERA</b> Pots apuntar-te <a href="/registration/race/${race}/waiting">aqu&iacute;</a></h1>
            <br>
            <span>Si algun inscrit causa baixa. Avisarem al primer de la llista d'espera</span>
        </div>
    </div>
</div>
</body>
</html>