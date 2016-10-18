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

            <table class="table">
                <thead>
                    <th><b>Cognoms, Nom</b></th>
                    <th><b>Poblaci&oacute;</b></th>
                    <th><b>Club</b></th>
                </thead>
                <c:forEach items="${registrations}" var="r">
                    <tr>
                        <td>${r.surname1} ${r.surname2}, ${r.name}</td>
                        <td>${r.town}</td>
                        <td>${r.club}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>