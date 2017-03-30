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
NO SORTIDA
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <table border="1">
                <thead>
                    <tr>
                        <td><b>Dorsal</b></td>
                    </tr>
                </thead>
                <c:forEach items="${nosor}" var="r">
                    <tr>
                        <td>${r.readKey.bib}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
READ AFTER OUT
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <table border="1">
                <thead>
                <tr>
                    <td><b>Dorsal</b></td>
                </tr>
                </thead>
                <c:forEach items="${out}" var="r">
                    <tr>
                        <td>${r.readKey.bib}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>