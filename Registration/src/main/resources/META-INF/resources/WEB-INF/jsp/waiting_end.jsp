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
        <div class="col-md-12" style="background-color: #FF5640; font-size: xx-large; color: azure; padding: 6%;">
            ${title}
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2>Ja est&aacute;s inscrit a la llista d'espera</h2>
            <br>
            <h1><b>INSCRIPCI&Oacute; A LA LLISTA D'ESPERA FINALIZADA CORRECTAMENT</b></h1>
            <br>
            <span>Aqu&iacute; tens la <a href="/registration/race/${race}/waitings">llista d'espera</a></span><br>
            <span>Si algun inscrit causa baixa. Avisarem al primer de la llista.</a></span>
        </div>
    </div>
</div>
</body>
</html>