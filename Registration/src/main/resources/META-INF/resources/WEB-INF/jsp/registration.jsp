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

            <form action="/registration/race/${race}/registration" method="POST">

                <div class="form-group">
                    <label for="route">Ruta prevista*</label>
                    <select id="route" name="route" class="form-control">
                        <c:forEach items="${routes}" var="r">
                            <option value="${r.id}">${r.name}</option>
                        </c:forEach>
                    </select>
                </div>


                <div class="form-group">
                    <label for="name">Nom*</label>
                    <input type="text" name="name" class="form-control" id="name" value="${registration.name}" placeholder="Nom">
                    <c:if test="${errors.hasFieldErrors('name')}"><span style="color: red;">Nom inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="surname1">Primer cognom*</label>
                    <input type="text" class="form-control" id="surname1" name="surname1" value="${registration.surname1}" placeholder="Primer cognom">
                    <c:if test="${errors.hasFieldErrors('surname1')}"><span style="color: red;">Cognom inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="surname2">Segon cognom</label>
                    <input type="text" class="form-control" id="surname2" name="surname2" value="${registration.surname2}" placeholder="Segon cognom">
                    <c:if test="${errors.hasFieldErrors('surname2')}"><span style="color: red;">Cognom inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label>Sexe*</label><br>
                    <label class="checkbox-inline"><input type="radio" name="gender" value="H" checked>Home</label>
                    <label class="checkbox-inline"><input type="radio" name="gender" value="D">Dona</label>
                </div>
                <div class="form-group">
                    <label for="bibname">Nom dorsal</label>
                    <input type="text" class="form-control" id="bibname" name="bibname" value="${registration.bibname}" placeholder="Nom dorsal">
                    <c:if test="${errors.hasFieldErrors('bibname')}"><span style="color: red;">Nom dorsal inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="birthday">Data naixement*</label>
                    <input type="text" class="form-control" id="birthday" name="birthday" value="${registration.birthday}" placeholder="dd/MM/AAAA">
                    <c:if test="${errors.hasFieldErrors('birthday')}"><span style="color: red;">Data naixement inv&agrave;lida</span></c:if>
                </div>
                <div class="form-group">
                    <label for="size">Talla samarreta</label>
                    <span style="font-size: x-small"> ( ** Ja tenim les samarretes comprades. Nom&eacute;s es poden encarregar talles que encara tenen disponibilitat)</span>
                    <select id="size" name="size" class="form-control">
                        <c:forEach items="${sizes}" var="t">
                            <option value="${t.id}">${t.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="email">Email*</label>
                    <input type="email" class="form-control" id="email" name="email" value="${registration.email}" placeholder="Email">
                    <c:if test="${errors.hasFieldErrors('email')}"><span style="color: red;">Email inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="dni">DNI/NIE*</label>
                    <input type="text" class="form-control" id="dni" name="dni" value="${registration.dni}" placeholder="DNI/NIE">
                    <c:if test="${errors.hasFieldErrors('dni')}"><span style="color: red;">DNI/NIE inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="telf">Telf*</label>
                    <span style="font-size: x-small"> ( ** Faciliteu-nos el m&ograve;bil que portareu el dia de la cursa aix&iacute; cas d'incid&egrave;ncia podrem contactar amb vosaltres)</span>
                    <input type="text" class="form-control" id="telf" name="telf" value="${registration.telf}" placeholder="Telf">
                    <c:if test="${errors.hasFieldErrors('telf')}"><span style="color: red;">Telf inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="town">Poblaci&oacute;*</label>
                    <input type="text" class="form-control" id="town" name="town" value="${registration.town}" placeholder="Poblaci&oacute;">
                    <c:if test="${errors.hasFieldErrors('town')}"><span style="color: red;">Poblaci&oacute; inv&agrave;lida</span></c:if>
                </div>
                <div class="form-group">
                    <label for="club">Club</label>
                    <input type="text" class="form-control" id="club" name="club" value="${registration.club}" placeholder="Club">
                    <c:if test="${errors.hasFieldErrors('club')}"><span style="color: red;">Club inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="feec">N&uacute;mero federat <b>FEEC</b></label>
                    <input type="text" class="form-control" id="feec" name="feec" value="${registration.feec}" placeholder="N&uacute;mero federat FEEC">
                    <c:if test="${errors.hasFieldErrors('feec')}"><span style="color: red;">N&uacute;mero federat inv&agrave;lid</span></c:if>
                </div>
                <div class="form-group">
                    <label for="telfemer">Nom i Telf. cas emer&egrave;ngcia*</label>
                    <input type="text" class="form-control" id="telfemer" name="telfemer" value="${registration.telfemer}" placeholder="Telf emerg&egrave;ncia">
                    <c:if test="${errors.hasFieldErrors('telfemer')}"><span style="color: red;">Nom i Telf. cas emer&egrave;ngcia inv&agrave;lid</span></c:if>
                </div>
                <button type="submit" class="btn btn-primary btn-lg btn-block">Inscriure</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>