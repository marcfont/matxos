<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Ranking - ${runner} - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px; background-color: #FF5640; font-size: xx-large; color: azure;">
        <div class="col-md-8 col-md-offset-2" style="padding: 6%;">
            ${title}<br>
            <b>${runner}</b><br>
            Dorsal: <b>${bib}</b><br>
            Ruta prevista: ${route}
        </div>
        <div class="pull-right" style="font-size: x-large; padding: 10px;"><a style="color: white" href="/ranking/race/${race}/ranking">Torna Ranking</a></div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <table class="table">
                <thead>
                    <th><b>Control</b></th>
                    <th><b>Temps</b></th>
                </thead>
                <c:forEach items="${rankings}" var="r">
                    <tr>
                        <td>${r.control}</td>
                        <c:choose>
                            <c:when test="${r.control.equals('Sortida')}">
                                <td>00h 00m 00s</td>
                            </c:when>
                            <c:otherwise>
                                <td>${r.time}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>