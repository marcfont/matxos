<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Control - ${title} </title>
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
            <a href="/control/home">Tornar</a>
            <br><br>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h1>Nou abandonament</h1><br>
            <form action="/control/races/${race}/out" method="POST" accept-charset="utf-8">
                <div class="form-group">
                    <label for="control">Control*</label>
                    <select id="control" name="control" class="form-control">
                        <c:forEach items="${controls}" var="c">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="bib">Dorsal*</label>
                    <input type="text" name="bib" class="form-control" id="bib" value="" placeholder="Dorsal">
                    <c:if test="${errors.hasFieldErrors('bib')}"><span style="color: red;">Dorsal inv&agrave;lid</span></c:if>
                </div>
                <button type="submit" class="btn btn-primary btn-lg btn-block">Nou abandonament</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <h1>Modifica/afegeix temps</h1><br>
            <form action="/control/races/${race}/read" method="POST" accept-charset="utf-8">
                <div class="form-group">
                    <label for="control2">Control*</label>
                    <select id="control2" name="control" class="form-control">
                        <c:forEach items="${controls}" var="c">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="bib">Dorsal*</label>
                    <input type="text" name="bib" class="form-control" id="bib" value="" placeholder="Dorsal">
                    <c:if test="${errors.hasFieldErrors('bib')}"><span style="color: red;">Dorsal inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="time">Temps* (hora que ha passat)</label>
                    <input type="text" name="time" class="form-control" id="time" value="" placeholder="HH:mm:ss">
                    <c:if test="${errors.hasFieldErrors('time')}"><span style="color: red;">Temps inv&agrave;lid</span></c:if>
                </div>
                <button type="submit" class="btn btn-primary btn-lg btn-block">Actualiza</button>
            </form>
        </div>
        </div>
    </div>

    <div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h1>Comprova pendents</h1><br>
        <form action="/control/races/${race}/pending" method="GET" accept-charset="utf-8">
            <div class="form-group">
                <label for="control3">Control*</label>
                <select id="control3" name="control" class="form-control">
                    <c:forEach items="${controls}" var="c">
                        <option value="${c.id}">${c.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary btn-lg btn-block">Comprovar</button>
        </form>
    </div>
</div>
</div>
</div>
</body>
</html>