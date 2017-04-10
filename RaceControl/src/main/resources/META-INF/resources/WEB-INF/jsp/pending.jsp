<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>${title} </title>
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
            <a href="/control/races/${race}">Tornar</a>
            <br><br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            Falten: ${howmany}
            <br><br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <table border="1">
                <thead>
                    <tr>
                        <td><b>Dorsal</b></td>
                        <td><b>Corredor</b></td>
                        <td><b>Emerg&egrave;ncia</b></td>
                    </tr>
                </thead>
                <c:forEach items="${reads}" var="r">
                    <tr>
                        <td>${r.bib}</td>
                        <td>${r.surname1} ${r.surname2}, ${r.name}</td>
                        <td>${r.telfemer}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>