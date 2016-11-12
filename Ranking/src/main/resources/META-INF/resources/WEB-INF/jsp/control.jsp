<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Ranking - ${controltitle} - ${title} </title>
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="margin-bottom: 40px; background-color: darkcyan; font-size: xx-large; color: azure;">
        <div class="col-md-8 col-md-offset-2" style="padding: 6%;">
            ${controltitle} - ${title}
        </div>
        <div class="pull-right" style="font-size: x-large; padding: 10px;"><a style="color: white" href="/ranking/race/${race}/ranking">Torna Ranking</a></div>
    </div>

    <div class="row ">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default ">
                <div class="panel-body">
                    <form action="/ranking/race/${race}/control/${control}/ranking" class="form-inline">
                        <div class="form-group">
                            <div class="form-group">
                                <input type="text"
                                       name="bib"
                                       size="5"
                                       class="form-control"
                                       placeholder="Dorsal"
                                       value="${param.bib}">
                            </div>
                            <div class="form-group">
                                <input type="text"
                                       name="name"
                                       placeholder="Nom/Cognoms"
                                       class="form-control"
                                       value="${param.name}">
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="route" >
                                    <option value="">Totes</option>
                                    <c:forEach items="${routes}" var="r">
                                        <option value="${r.name}" ${param.route.equals(r.name) ? ' selected ' : ''} >${r.name}</option>
                                    </c:forEach>

                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="gender">
                                    <option value="">General</option>
                                    <option value="M" ${param.gender.equals('M') ? ' selected ' : ''}>Home</option>
                                    <option value="F" ${param.gender.equals('F') ? ' selected ' : ''}>Dona</option>
                                </select>
                            </div>
                            <input type="submit" class="btn btn-primary" value="Cerca"></p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <table class="table">
                <thead>
                    <th><b>Dorsal</b></th>
                    <th><b>Cognoms, Nom</b></th>
                    <th><b>Temps</b></th>
                    <th><b>Ruta prevista</b></th>
                </thead>
                <c:forEach items="${rankings}" var="r">
                    <tr>
                        <td>${r.bib}</td>
                        <td><a href="/ranking/race/${race}/bibs/${r.bib}/ranking">${r.name}</a></td>
                        <td>${r.time}</td>
                        <td>${r.route}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>