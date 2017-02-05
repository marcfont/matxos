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
            <table>
            <c:forEach items="${races}" var="r">
                <tr>
                    <td><h2><a href="/control/races/${r.id}">${r.name}</a></h2><br></td>
                </tr>
            </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>