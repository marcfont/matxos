<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Inscripcions - ${title} </title>
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
        <div class="col-md-8 col-md-offset-2" style="">
            <b>Places disponibles: ${available} </b>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <table class="table">
                <thead>
                    <th><b>Cognoms, Nom</b></th>
                    <th><b>Poblaci&oacute;</b></th>
                    <th><b>Club</b></th>
                    <th><b>&Eacute;s solidari</b></th>
                </thead>
                <c:forEach items="${registrations}" var="r">
                    <tr>
                        <td>${r.surname1} ${r.surname2}, ${r.name}</td>
                        <td>${r.town}</td>
                        <td>${r.club}</td>
                        <td>
                             <c:choose>
                                  <c:when test="${r.solidari}">
                                         <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                                  </c:when>
                                   <c:otherwise>
                                          <span></span>
                                  </c:otherwise>
                             </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>