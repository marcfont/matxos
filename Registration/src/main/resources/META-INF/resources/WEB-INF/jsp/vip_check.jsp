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

            <form action="/registration/race/${race}/check" method="POST">
                <span>Inscripcions obertes nom&egrave;s per persones VIP (socis)</span>
                <br>
                <div class="form-group">
                    <label for="dni">DNI/NIE</label>
                    <input type="text" class="form-control" id="dni" name="dni" placeholder="DNI/NIE">
                </div>

                <button type="submit" class="btn btn-primary btn-lg btn-block">Valida DNI/NIE</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>