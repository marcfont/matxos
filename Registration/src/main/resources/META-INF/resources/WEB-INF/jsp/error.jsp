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
        <div class="col-md-12" style="background-color: #FF5640; font-size: xx-large; color: azure; padding: 6%;">
            ${title}
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h2>Alguna cosa ha anat malament :(</h2>
            <br>
            <h1>ERROR realizant la Inscripci&oacute;</h1>
            <br>
            <>Torna-ho a intentar <a href="/registration/race/${race}/registration">aqu&iacute;</a> o contacteu amb nosaltres: <a href="mailto:inscripcions@matxos.cat">inscripcions@matxos.cat</a></span>
        </div>
    </div>
</div>
</body>
</html>